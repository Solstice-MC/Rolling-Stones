package org.solstice.rollingStones.registry;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import org.solstice.euclidsElements.content.api.item.ToolMaterialImpl;

public class RollingToolMaterials {

	public static final ToolMaterial ADAMANTITE = new ToolMaterialImpl(
		1024, 8.0F, 3.0F,
		BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
		10,
		() -> Ingredient.ofItems(RollingItems.ADAMANTITE_INGOT)
	);

}
