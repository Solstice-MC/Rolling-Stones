package org.solstice.rollingStones.mixin.test;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@WrapOperation(
		method = "tryAttack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private boolean ignoreDamageWithTrainingWeapon(
		Entity instance,
		DamageSource source,
		float amount,
		Operation<Boolean> original,
		@Local(name = "f") float dealtDamage
	) {
		if (dealtDamage == 0F) return true;
		return original.call(instance, source, amount);
	}

	@WrapOperation(
		method = "tryAttack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/mob/MobEntity;getKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F"
		)
	)
	private float increaseKnockbackWithTrainingWeapon(
		MobEntity instance,
		Entity entity,
		DamageSource damageSource,
		Operation<Float> original,
		@Local(name = "f") float dealtDamage
	) {
		if (dealtDamage == 0F) return 1;
		return original.call(instance, entity, damageSource);
	}

}
