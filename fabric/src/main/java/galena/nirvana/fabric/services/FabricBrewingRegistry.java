package galena.nirvana.fabric.services;

import galena.nirvana.platform.services.IBrewingRegistry;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

public class FabricBrewingRegistry implements IBrewingRegistry {

    public record BrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {}

    private static final ArrayList<BrewingRecipe> CUSTOM_RECIPES = new ArrayList<>();

    public static boolean isCustomInput(ItemStack stack) {
        return CUSTOM_RECIPES.stream().anyMatch(it -> it.input.test(stack));
    }

    public static boolean isCustomIngredient(ItemStack stack) {
        return CUSTOM_RECIPES.stream().anyMatch(it -> it.ingredient.test(stack));
    }

    public static Optional<BrewingRecipe> getCustomRecipe(ItemStack input, ItemStack ingredient) {
        return CUSTOM_RECIPES.stream().filter(it -> it.ingredient.test(ingredient) && it.input.test(input)).findFirst();
    }

    public static boolean hasCustomRecipe(ItemStack input, ItemStack ingredient) {
        return getCustomRecipe(input, ingredient).isPresent();
    }

    @Override
    public void addRecipe(PotionBrewing.Builder builder, Ingredient input, Ingredient ingredient, ItemStack output) {
        CUSTOM_RECIPES.add(new BrewingRecipe(input, ingredient, output));
        // TODO?
        // PotionBrewing.ALLOWED_CONTAINERS.add(input);
    }

}
