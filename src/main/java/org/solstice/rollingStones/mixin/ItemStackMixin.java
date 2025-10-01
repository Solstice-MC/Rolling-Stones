package org.solstice.rollingStones.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;
import org.solstice.euclidsElements.effectHolder.api.EffectHolderHelper;
import org.solstice.rollingStones.registry.RollingEnchantmentEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow public abstract Item getItem();

	@Shadow
	public abstract int getDamage();

	@WrapMethod(method = "getMaxDamage")
	private int getMaxDamage(Operation<Integer> original) {
		MutableFloat result = new MutableFloat(original.call());
		EffectHolderHelper.forEachEffectHolder((ItemStack)(Object)this, (effectHolder, level) ->
			// TODO WTF WHY
			effectHolder.value().method_60506(RollingEnchantmentEffects.MAX_DURABILITY, Random.create(), level, result)
		);
		return result.intValue();
	}

	@ModifyExpressionValue(
		method = "appendAttributeModifierTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;value()D"
		)
	)
	private double wip(
		double original,
		Consumer<Text> c,
		@Nullable PlayerEntity player,
		RegistryEntry<EntityAttribute> attribute,
		EntityAttributeModifier modifier
	) {
		AtomicReference<Double> result = new AtomicReference<>(original);

//		EffectHolderHelper.forEachEffectHolder((ItemStack)(Object)this, (entry, level) -> {
//			List<AttributeEnchantmentEffect> attributeEffects = entry.value().getEffects()
//				.getOrDefault(EnchantmentEffectComponentTypes.ATTRIBUTES, new ArrayList<>());
//			attributeEffects.forEach(attributeEffect -> {
//				System.out.println(attributeEffect.attribute());
//				System.out.println(attribute);
//				if (attributeEffect.attribute().equals(attribute)) {
//					result.updateAndGet(value -> value + attributeEffect.amount().getValue(level));
//				}
//			});
//		});

		return result.get();
	}

	@WrapMethod(method = "appendAttributeModifierTooltip")
	void shouldDisplayAttribute(Consumer<Text> textConsumer, PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, Operation<Void> original) {
		if (player == null) return;

		double totalValue = player.getAttributeValue(attribute) + modifier.value();
		if (totalValue <= -0.001D || totalValue >= 0.001D)
			original.call(textConsumer, player, attribute, modifier);
	}

	@WrapMethod(method = "setDamage")
	public void preventCursedDamageIncrease(int damage, Operation<Void> original) {
		boolean isCursed = EnchantmentHelper.hasAnyEnchantmentsWith(
			(ItemStack)(Object)this,
			RollingEnchantmentEffects.PREVENT_REPAIRING
		);
		int currentDamage = this.getDamage();

		if (isCursed && damage > currentDamage) return;

		original.call(damage);
	}

}
