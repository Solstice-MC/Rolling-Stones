package org.solstice.rollingStones.content.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;

public class RollingStoneEntity extends PistonBlockEntity {

	public RollingStoneEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

//	public RollingStoneEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
//		super(type, pos, state);
//	}

	@Override
	public BlockEntityType<?> getType() {
		return super.getType();
	}

}
