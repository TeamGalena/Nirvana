package galena.nirvana.forge.services;

import galena.nirvana.platform.services.IBrewingRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

public class ForgeBrewingRegistry implements IBrewingRegistry {

    @Override
    public void addRecipe(PotionBrewing.Builder builder, Ingredient input, Ingredient ingredient, ItemStack output) {
        builder.addRecipe(input, ingredient, output);
    }

}
