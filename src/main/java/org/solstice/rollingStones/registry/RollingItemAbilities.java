package org.solstice.rollingStones.registry;

import net.minecraft.registry.Registry;
import org.solstice.rollingStones.RollingStones;
import org.solstice.rollingStones.content.itemAbility.ItemAbility;
import org.solstice.rollingStones.content.itemAbility.TremoringAbility;

public class RollingItemAbilities {

	public static void init() {}

	public static final ItemAbility.Type TREMORING = register("tremoring", TremoringAbility.TYPE);

	public static ItemAbility.Type register(String name, ItemAbility.Type type) {
		return Registry.register(RollingRegistries.ITEM_ABILITY, RollingStones.of(name), type);
	}

}
