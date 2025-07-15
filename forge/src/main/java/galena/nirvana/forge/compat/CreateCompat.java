package galena.nirvana.forge.compat;

import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaItems;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class CreateCompat {

    private static List<FillingRecipe> getBongFillingRecipes(IIngredientManager ingredientManager) {
        var items = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);
        var bongs = items.stream().filter(NirvanaItems.POTION_BONG::isIn);
        return bongs.map(stack -> {
            var potion = PotionFluidHandler.getFluidFromPotionItem(stack);
            return new StandardProcessingRecipe.Builder<>(FillingRecipe::new, NirvanaConstants.createId("bong"))
                    .withItemIngredients(Ingredient.of(NirvanaItems.BONG))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(potion))
                    .withSingleItemOutput(stack)
                    .build();
        }).toList();
    }

    @SuppressWarnings("unchecked")
    public static void addJeiRecipes(IRecipeRegistration registration) {
        registration.getJeiHelpers().getRecipeType(ResourceLocation.fromNamespaceAndPath("create", "spout_filling"))
                .map(it -> (RecipeType<FillingRecipe>) it)
                .ifPresent(type -> registration.addRecipes(type, getBongFillingRecipes(registration.getIngredientManager())));
    }

}
