package org.solstice.rollingStones;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.village.VillageGossipType;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;
import java.util.function.Predicate;

public class Test {

	public static void init() {
		PlayerBlockBreakEvents.AFTER.register(Test::onBlockBreak);
		UseBlockCallback.EVENT.register(Test::onUseBlock);
	}

	private static boolean notInVillage(ServerWorld world, BlockPos pos) {
		Registry<Structure> registry = world.getRegistryManager().get(RegistryKeys.STRUCTURE);

		List<StructureStart> starts = world.getStructureAccessor().getStructureStarts(
			new ChunkPos(pos),
			structure -> registry.getEntry(structure).isIn(StructureTags.VILLAGE)
		);
		return starts.stream()
			.noneMatch(start -> start.getBoundingBox().contains(pos));
	}

	private static void onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (notInVillage((ServerWorld) world, pos)) return;

		List<VillagerEntity> entities = viewingVillagers(world, player,
			BlockPos.ofFloored(player.getBoundingBox().getCenter()));

		if (entities.isEmpty()) return;

		entities.forEach(villager -> villager.getGossip().startGossip(
			player.getUuid(),
			VillageGossipType.MINOR_NEGATIVE,
			4
		));
	}


	public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
		if (player.isSpectator()) return ActionResult.PASS;
		if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;
		if (hitResult.getType() != HitResult.Type.BLOCK) return ActionResult.PASS;

		if (notInVillage(serverWorld, hitResult.getBlockPos())) return ActionResult.PASS;

		List<VillagerEntity> entities = viewingVillagers(world, player,
			BlockPos.ofFloored(player.getBoundingBox().getCenter()));

		if (entities.isEmpty()) return ActionResult.PASS;

//		player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL, 2.0f, 1.0f);

		entities.forEach(villager -> villager.getGossip().startGossip(
			player.getUuid(),
			VillageGossipType.MAJOR_NEGATIVE,
			12
		));

		return ActionResult.PASS;
	}

	private static boolean canView(VillagerEntity villager, PlayerEntity player, int range) {
		if (villager.isSleeping()) return false;

		EntityHitResult result = ProjectileUtil.raycast(
			villager,
			villager.getPos(),
			player.getPos(),
			player.getBoundingBox(),
			entity -> entity == player,
			range * range
		);

		return result != null && result.getType() != HitResult.Type.MISS;
	}

	private static List<VillagerEntity> viewingVillagers(World world, PlayerEntity player, BlockPos pos) {
		int range = player.isSneaking() ? 16 : 32;
		Box box = new Box(pos).expand(range);
		Predicate<VillagerEntity> canView = villager -> canView(villager, player, range);
		return world.getEntitiesByClass(VillagerEntity.class, box, canView);
	}

}
