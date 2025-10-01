package org.solstice.rollingStones.content.itemAbility;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.solstice.rollingStones.registry.RollingRegistries;
import org.solstice.rollingStones.registry.RollingRegistryKeys;

public interface ItemAbility {

	Codec<ItemAbility> CODEC = RollingRegistries.ITEM_ABILITY.getCodec()
		.dispatch(ItemAbility::getType, Type::codec);

//	Codec<RegistryEntry<ItemAbility>> ENTRY_CODEC = RegistryFixedCodec.of(RollingRegistryKeys.ITEM_ABILITY)
//		.dispatch(ItemAbility::getType, Type::codec);

	Type getType();
	MapCodec<? extends ItemAbility> getCodec();
	Definition getDefinition();

	void afterDamageDealt(ItemStack stack, LivingEntity target, LivingEntity attacker);
	void afterBlockBreak(ItemStack stack, World world, BlockPos pos);
	void afterDamageTaken(ItemStack stack, LivingEntity attacker, LivingEntity target);

	record Type(MapCodec<? extends ItemAbility> codec) {}

	record Definition (
		int maxCharges
	) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.optionalFieldOf("max_charges", 1).forGetter(Definition::maxCharges)
		).apply(instance, Definition::new));

	}

}
