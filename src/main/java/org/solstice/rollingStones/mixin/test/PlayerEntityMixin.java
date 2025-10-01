package org.solstice.rollingStones.mixin.test;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@WrapOperation(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;getDamageAgainst(Lnet/minecraft/entity/Entity;FLnet/minecraft/entity/damage/DamageSource;)F",
			ordinal = 0
		)
	)
	float storeDealtDamage(
		PlayerEntity instance,
		Entity target,
		float baseDamage,
		DamageSource damageSource,
		Operation<Float> original,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage
	) {
		dealtDamage.set(original.call(instance, target, baseDamage, damageSource));
		return dealtDamage.get();
	}

	@ModifyVariable(
		method = "attack",
		at = @At("STORE"),
		name = "g"
	)
	private float increaseDealtDamage(
		float damage,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage
	) {
		if (dealtDamage.get() == 0) return Float.MIN_NORMAL;
		return damage;
	}

	@ModifyVariable(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;getBonusAttackDamage(Lnet/minecraft/entity/Entity;FLnet/minecraft/entity/damage/DamageSource;)F"
		),
		name = "g"
	)
	private float resetDealtDamage(
		float damage,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage
	) {
		if (dealtDamage.get() == 0) return 0;
		return damage;
	}

	@WrapOperation(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private boolean ignoreAttackWithTrainingWeapon(
		Entity instance,
		DamageSource source,
		float amount,
		Operation<Boolean> original,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage
	) {
		if (dealtDamage.get() == 0) return true;
		return original.call(instance, source, amount);
	}

	@ModifyVariable(method = "attack", at = @At("STORE"), name = "k")
	private float increaseKnockbackWithTrainingWeapon(
		float strength,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage,
		@Local(name = "h") float attackCooldown
	) {
		if (dealtDamage.get() == 0) strength += attackCooldown;
		return strength;
	}

	@WrapOperation(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private boolean ignoreSweepAttackWithTrainingWeapon(
		LivingEntity instance,
		DamageSource source,
		float amount,
		Operation<Boolean> original,
		@Share(value = "dealtDamage") LocalFloatRef dealtDamage
	) {
		if (dealtDamage.get() == 0) return true;
		return original.call(instance, source, amount);
	}

}
