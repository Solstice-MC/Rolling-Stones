package org.solstice.rollingStones.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RollingStoneBlock extends PistonExtensionBlock {

//	public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;

	protected static final VoxelShape X_SHAPE = Block.createCuboidShape(2, 0, 4, 14, 12, 12);
	protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(4, 0, 2, 12, 12, 14);

	public RollingStoneBlock(Settings settings) {
		super(settings);
//		this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.NORTH.getAxis()));
	}

//	@Override
//	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//		builder.add(AXIS);
//	}

	public static BlockEntity createBlockEntityPiston(BlockPos pos, BlockState state, BlockState pushedBlock, Direction facing, boolean extending, boolean source) {
		return new PistonBlockEntity(pos, state, pushedBlock, facing, extending, source);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		Direction direction = ctx.getPlayerLookDirection();
		return this.getDefaultState().with(PistonExtensionBlock.FACING, direction);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) return ActionResult.PASS;

		Direction direction = hit.getSide().getOpposite();
		world.removeBlock(pos, true);

		BlockState destination = world.getBlockState(pos.offset(direction));
		if (!destination.canPlaceAt(world, pos)) return ActionResult.FAIL;

		world.setBlockState(pos.offset(direction), state.with(PistonExtensionBlock.FACING, direction));
		return ActionResult.SUCCESS;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(PistonExtensionBlock.FACING).getAxis() == Direction.Axis.X) return X_SHAPE;
		return Z_SHAPE;
	}

//	@Override
//	protected BlockState rotate(BlockState state, BlockRotation rotation) {
//		return switch (rotation) {
//			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(PistonExtensionBlock.FACING).getAxis()) {
//				case Z -> state.with(PistonExtensionBlock.FACING, Direction.Axis.X);
//				case X -> state.with(PistonExtensionBlock.FACING, Direction.Axis.Z);
//				default -> state;
//			};
//			default -> state;
//		};
//	}

}
