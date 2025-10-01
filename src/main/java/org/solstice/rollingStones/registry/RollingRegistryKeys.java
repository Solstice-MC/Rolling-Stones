package org.solstice.rollingStones.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import org.solstice.rollingStones.RollingStones;
import org.solstice.rollingStones.content.itemAbility.ItemAbility;
import org.solstice.rollingStones.content.upgrade.Upgrade;

public class RollingRegistryKeys {

	public static void init() {}

	public static final RegistryKey<Registry<Upgrade>> UPGRADE = of("upgrade");
	public static final RegistryKey<Registry<ItemAbility.Type>> ITEM_ABILITY = of("item_ability");

	public static <T> RegistryKey<Registry<T>> of(String name) {
        return RegistryKey.ofRegistry(RollingStones.of(name));
    }

}
