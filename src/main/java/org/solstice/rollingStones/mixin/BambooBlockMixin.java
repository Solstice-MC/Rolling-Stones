package org.solstice.rollingStones.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BambooBlock.class)
public abstract class BambooBlockMixin extends Block {

	public BambooBlockMixin(Settings settings) {
		super(settings);
	}

	@WrapMethod(method = "calcBlockBreakingDelta")
	float fixBreakingRequirement(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, Operation<Float> original) {
		return player.getMainHandStack().isIn(ItemTags.SWORDS) ? 1 : super.calcBlockBreakingDelta(state, player, world, pos);
	}

}
