package galena.nirvana.forge.services;

import galena.nirvana.platform.services.IBrewingRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class ForgeBrewingRegistry implements IBrewingRegistry {

    @Override
    public void addRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        NeoForge.EVENT_BUS.addListener((RegisterBrewingRecipesEvent event) -> {
            event.getBuilder().addRecipe(input, ingredient, output);
        });
    }

}
