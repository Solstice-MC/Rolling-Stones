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
import org.solstice.rollingStones.registry.RollingTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

//	@WrapOperation(
//		method = "tryAttack",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
//		)
//	)
//	private boolean ignoreAttackWithTrainingWeapon(
//		Entity instance,
//		DamageSource source,
//		float amount,
//		Operation<Boolean> original
//	) {
//		if (this.getMainHandStack().isIn(RollingTags.TRAINING_WEAPON)) return true;
//		return original.call(instance, source, amount);
//	}

//	@ModifyVariable(method = "tryAttack", at = @At("STORE"), name = "g")
//	private float increaseKnockbackWithTrainingWeapon(float strength) {
//		if (this.getMainHandStack().isIn(RollingTags.TRAINING_WEAPON)) return ++strength;
//		return strength;
//	}

}
