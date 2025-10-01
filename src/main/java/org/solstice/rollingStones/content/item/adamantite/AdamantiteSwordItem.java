package org.solstice.rollingStones.content.item.adamantite;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.UseAction;
import org.solstice.rollingStones.content.item.AbilityHoldingItem;

public class AdamantiteSwordItem extends SwordItem implements AbilityHoldingItem {

	public AdamantiteSwordItem(ToolMaterial toolMaterial, Settings settings) {
		super(toolMaterial, settings);
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
