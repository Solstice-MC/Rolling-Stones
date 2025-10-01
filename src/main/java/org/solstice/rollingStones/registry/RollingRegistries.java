package org.solstice.rollingStones.registry;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import org.solstice.rollingStones.content.itemAbility.ItemAbility;
import org.solstice.rollingStones.content.upgrade.Upgrade;

public class RollingRegistries {


	public static void init() {
		DynamicRegistries.registerSynced(RollingRegistryKeys.UPGRADE, Upgrade.CODEC, Upgrade.CODEC);
	}

	public static final Registry<ItemAbility.Type> ITEM_ABILITY = new SimpleRegistry<>(RollingRegistryKeys.ITEM_ABILITY, Lifecycle.stable());

}
