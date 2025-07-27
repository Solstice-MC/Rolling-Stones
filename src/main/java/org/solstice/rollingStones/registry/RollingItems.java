package org.solstice.rollingStones.registry;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.solstice.rollingStones.RollingStones;
import org.solstice.rollingStones.content.item.CursedSmithingStoneItem;
import org.solstice.rollingStones.content.item.SmithingStoneItem;
import org.solstice.rollingStones.content.item.TransformingSmithingStone;

import java.util.Map;
import java.util.function.Function;

public class RollingItems {

	public static void init() {}

	public static final AttributeModifiersComponent BAMBOO_SWORD_ATTRIBUTES = AttributeModifiersComponent.builder()
		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
			new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), AttributeModifierSlot.MAINHAND
//			new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, -2, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND
//			new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, -0.999999999D, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND
		)
		.add(EntityAttributes.GENERIC_ATTACK_SPEED,
			 new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, -2.4D, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND
		)
		.build();

	public static final Item BAMBOO_SWORD = register("bamboo_sword",
		settings -> new SwordItem(ToolMaterials.WOOD, settings),
		new Item.Settings()
			.attributeModifiers(BAMBOO_SWORD_ATTRIBUTES)
	);

    public static final Item SIMPLE_SMITHING_STONE = register("simple_smithing_stone",
		SmithingStoneItem::new,
		new Item.Settings()
    );
	public static final Item MALEDICTIVE_SMITHING_STONE = register("maledictive_smithing_stone",
		settings -> new CursedSmithingStoneItem(settings, 3),
		new Item.Settings()
			.rarity(Rarity.UNCOMMON)
	);
    public static final Item HONED_SMITHING_STONE = register("honed_smithing_stone",
		SmithingStoneItem::new,
		new Item.Settings()
			.rarity(Rarity.UNCOMMON)
    );
    public static final Item GILDED_SMITHING_STONE = register("gilded_smithing_stone",
		SmithingStoneItem::new,
		new Item.Settings()
			.rarity(Rarity.RARE)
    );
	public static final Item MIDAS_SMITHING_STONE = register("midas_smithing_stone",
		settings -> new TransformingSmithingStone(settings, RollingTags.MIDAS_TRANSFORMATIONS, Items.GOLD_INGOT),
		new Item.Settings()
			.rarity(Rarity.RARE)
	);

	public static final Int2ObjectOpenHashMap<ItemStack> SMITHING_STONE_TIERS = new Int2ObjectOpenHashMap<>(Map.of(
		1, SIMPLE_SMITHING_STONE.getDefaultStack(),
		2, HONED_SMITHING_STONE.getDefaultStack(),
		3, GILDED_SMITHING_STONE.getDefaultStack()
	));

	public static Item register(String name) {
		return register(name, Item::new);
	}

	public static Item register(String name, Function<Item.Settings, Item> function) {
		return register(name, function, new Item.Settings());
	}

	public static Item register(String name, Item.Settings settings) {
		return register(name, Item::new, settings);
	}

	public static Item register(String name, Function<Item.Settings, Item> function, Item.Settings settings) {
		Identifier id = RollingStones.of(name);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		Item item = function.apply(settings);
		return Registry.register(Registries.ITEM, key, item);
	}

}
