package org.solstice.rollingStones.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.rollingStones.content.item.SmithingStoneItem;
import org.solstice.rollingStones.content.upgrade.Upgrade;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RollingItemGroups {

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries ->
			entries.addBefore(Items.WOODEN_SWORD, RollingItems.BAMBOO_SWORD)
		);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries ->
			entries.addAfter(Items.BARREL, RollingBlocks.STRONGBOX)
		);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries ->
			entries.addAfter(Items.BARREL, RollingBlocks.STRONGBOX)
		);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(RollingItemGroups::addSmithingStones);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Blocks.ANCIENT_DEBRIS, RollingBlocks.ANCIENT_CONSTRUCTION);
			entries.addAfter(Items.NETHERITE_INGOT, RollingItems.ADAMANTITE_CHUNK, RollingItems.ADAMANTITE_INGOT);
		});
	}

	public static void addSmithingStones(FabricItemGroupEntries entries) {
		entries.addAll(List.of(
			RollingItems.SIMPLE_SMITHING_STONE.getDefaultStack().copy(),
			RollingItems.MALEDICTIVE_SMITHING_STONE.getDefaultStack().copy(),
			RollingItems.HONED_SMITHING_STONE.getDefaultStack().copy(),
			RollingItems.GILDED_SMITHING_STONE.getDefaultStack().copy(),
			RollingItems.MIDAS_SMITHING_STONE.getDefaultStack().copy()
		));
		entries.getContext().lookup().getOptionalWrapper(RollingRegistryKeys.UPGRADE).ifPresent(registry -> {
			addMaxTierSmithingStones(entries, registry);
			addAllTierSmithingStones(entries, registry);
		});
	}

	private static void addMaxTierSmithingStones(ItemGroup.Entries entries, RegistryWrapper<Upgrade> registryWrapper) {
		registryWrapper.streamEntries()
			.map(RollingItemGroups::maxTier)
			.forEach(stack -> entries.add(stack, ItemGroup.StackVisibility.PARENT_TAB_ONLY));
	}

	private static ItemStack maxTier(RegistryEntry<Upgrade> entry) {
		return SmithingStoneItem.forUpgrade(entry, entry.value().getDefinition().getMaxLevel());
	}

	private static void addAllTierSmithingStones(ItemGroup.Entries entries, RegistryWrapper<Upgrade> registryWrapper) {
		registryWrapper.streamEntries()
			.flatMap(RollingItemGroups::allTiers)
			.forEach(stack -> entries.add(stack, ItemGroup.StackVisibility.SEARCH_TAB_ONLY));
	}

	private static Stream<ItemStack> allTiers(RegistryEntry<Upgrade> entry) {
		return IntStream.rangeClosed(1, entry.value().getDefinition().getMaxLevel()).mapToObj(tier -> SmithingStoneItem.forUpgrade(entry, tier));
	}

}
