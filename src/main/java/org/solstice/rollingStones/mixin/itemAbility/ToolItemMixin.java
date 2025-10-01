package org.solstice.rollingStones.mixin.itemAbility;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.UseAction;
import org.solstice.rollingStones.content.item.AbilityHoldingItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
public abstract class ToolItemMixin extends Item implements AbilityHoldingItem {

	public ToolItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		if (this.canUseAbility(stack)) return UseAction.SPEAR;
		return super.getUseAction(stack);
	}

	@Override
	public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postDamageEntity(stack, target, attacker);
		this.increaseAbility(stack);
	}



}
