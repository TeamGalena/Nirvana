package galena.nirvana.platform.services;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

public interface IBrewingRegistry {

    void addRecipe(PotionBrewing.Builder builder, Ingredient input, Ingredient ingredient, ItemStack output);

}
