package org.solstice.rollingStones.content.itemAbility;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record TremoringAbility (
	Definition definition
) implements ItemAbility {

	public static final TremoringAbility DEFAULT = new TremoringAbility(new Definition(0));

	public static final MapCodec<TremoringAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Definition.CODEC.forGetter(TremoringAbility::definition)
	).apply(instance, TremoringAbility::new));

	public static final Type TYPE = new Type(CODEC);

	@Override
	public MapCodec<? extends ItemAbility> getCodec() {
		return CODEC;
	}

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public Definition getDefinition() {
		return this.definition;
	}

	@Override
	public void afterDamageDealt(ItemStack stack, LivingEntity target, LivingEntity attacker) {

	}

	@Override
	public void afterBlockBreak(ItemStack stack, World world, BlockPos pos) {

	}

	@Override
	public void afterDamageTaken(ItemStack stack, LivingEntity attacker, LivingEntity target) {

	}

	public void rocketJump(World world, LivingEntity user, ItemStack stack, BlockPos pos) {
		world.createExplosion(
			user,
			Explosion.createDamageSource(world, user),
			new EntityExplosionBehavior(user),
			pos.toCenterPos(),
			1,
			false,
			World.ExplosionSourceType.NONE
		);
	}

	public void veinMine(World world, LivingEntity user, ItemStack stack, BlockPos originalPos) {
		BlockPos.Mutable pos = originalPos.mutableCopy();

		ToolComponent component = stack.getOrDefault(DataComponentTypes.TOOL, null);
		if (component == null) return;

		List<Direction> directions = Arrays.asList(Direction.values());
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(directions);
			for (Direction direction : directions) {
				BlockState state = world.getBlockState(pos.offset(direction));
				if (component.isCorrectForDrops(state)) {
					pos.move(direction);
					Block.dropStacks(state, world, pos, null, user, stack);
					boolean broken = world.setBlockState(pos, world.getFluidState(pos).getBlockState(), 3, 512);
					if (broken)
						world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(null, state));
					break;
				}
			}
			pos.move(directions.getFirst());
		}
	}

}
