package org.solstice.rollingStones.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import org.solstice.rollingStones.content.itemAbility.ItemAbility;

import java.util.function.UnaryOperator;

public record ItemAbilityComponent (
	ItemAbility ability,
	int charges
) {

	public static final Codec<ItemAbilityComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemAbility.CODEC.fieldOf("ability").forGetter(ItemAbilityComponent::ability),
		Codec.INT.optionalFieldOf("charges", 0).forGetter(ItemAbilityComponent::charges)
	).apply(instance, ItemAbilityComponent::new));

	public boolean canUseAbility() {
		return this.charges >= this.ability.getDefinition().maxCharges();
	}

	public static class Builder {

		public ItemAbility ability;
		public int charges;

		public Builder(ItemAbilityComponent component) {
			this(component.ability, component.charges);
		}

		public Builder(ItemAbility ability, int charges) {
			this.ability = ability;
			this.charges = charges;
		}

		public void modifyCharges(UnaryOperator<Integer> function) {
			int result = function.apply(this.charges);
			this.charges = Math.max(result, this.getMaxCharges());
			this.charges = function.apply(this.charges);
		}

		public void incrementCharges() {
			this.incrementCharges(1);
		}

		public void incrementCharges(int value) {
			this.modifyCharges(charges -> charges + value);
		}

		public void resetCharges() {
			this.charges = 0;
		}

		public int getMaxCharges() {
			return this.ability.getDefinition().maxCharges();
		}

		public ItemAbilityComponent build() {
			return new ItemAbilityComponent(this.ability, this.charges);
		}

	}

}
