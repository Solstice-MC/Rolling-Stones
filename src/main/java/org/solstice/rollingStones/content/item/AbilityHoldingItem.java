package org.solstice.rollingStones.content.item;

import net.minecraft.item.ItemStack;
import org.solstice.rollingStones.content.component.ItemAbilityComponent;
import org.solstice.rollingStones.registry.RollingComponentTypes;

public interface AbilityHoldingItem {

	default boolean canUseAbility(ItemStack stack) {
		ItemAbilityComponent component = stack.getOrDefault(RollingComponentTypes.ABILITY, null);
		if (component != null) return component.canUseAbility();
		return false;
	}

	default void increaseAbility(ItemStack stack) {

	}

}
