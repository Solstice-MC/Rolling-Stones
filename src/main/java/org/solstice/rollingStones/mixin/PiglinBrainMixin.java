package org.solstice.rollingStones.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

	@Shadow protected static boolean isWillingToTrade(PiglinEntity piglin, ItemStack nearbyItems) {
		return false;
	}

	@WrapOperation(
		method = "loot",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/mob/PiglinBrain;isGoldenItem(Lnet/minecraft/item/ItemStack;)Z"
		)
	)
	private static boolean canBarter(ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PiglinEntity piglin) {
		boolean result = isWillingToTrade(piglin, stack);
		return result && original.call(stack);
	}

	@WrapMethod(method = "isWillingToTrade")
	private static boolean isNotTrapped(PiglinEntity piglin, ItemStack nearbyItems, Operation<Boolean> original) {
		BlockPos pos = piglin.getBlockPos().west(0);
		var test = piglin.getPathfindingFavor(pos);

		System.out.println(test);
		boolean result = false;
		return result && original.call();
	}

}
