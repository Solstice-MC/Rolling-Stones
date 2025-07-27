package org.solstice.rollingStones.mixin.test;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	protected abstract <T extends TooltipAppender> void appendTooltip(ComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);

	@WrapMethod(method = "appendAttributeModifierTooltip")
	void shouldDisplayTooltip(Consumer<Text> textConsumer, PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, Operation<Void> original) {
//		double totalValue = player.getAttributeValue(attribute) + modifier.value();
//		System.out.println(totalValue);
//		if (totalValue <= -0.001D || totalValue >= 0.001D)
		original.call(textConsumer, player, attribute, modifier);
	}

}
