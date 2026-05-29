package galena.nirvana.forge.compat;

import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaItems;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class CreateCompat {

    private static List<RecipeHolder<FillingRecipe>> getBongFillingRecipes(IIngredientManager ingredientManager) {
        var items = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);
        var bongs = items.stream().filter(NirvanaItems.POTION_BONG::isIn);
        return bongs.map(stack -> {
            var potionFluid = PotionFluidHandler.getFluidFromPotionItem(stack);
            var potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                    .potion()
                    .flatMap(Holder::unwrapKey)
                    .orElseThrow()
                    .location();

            stack.setDamageValue(0);

            var id = NirvanaConstants.createId("fill/bong/" + potion.getNamespace() + "/" + potion.getPath());
            var recipe = new StandardProcessingRecipe.Builder<>(FillingRecipe::new, id)
                    .withItemIngredients(Ingredient.of(NirvanaItems.BONG))
                    .withFluidIngredients(SizedFluidIngredient.of(potionFluid))
                    .withSingleItemOutput(stack)
                    .build();

            return new RecipeHolder<>(id, recipe);
        }).toList();
    }

    @SuppressWarnings("unchecked")
    public static void addJeiRecipes(IRecipeRegistration registration) {
        registration.getJeiHelpers().getRecipeType(ResourceLocation.fromNamespaceAndPath("create", "spout_filling"))
                .map(it -> (RecipeType<RecipeHolder<FillingRecipe>>) it)
                .ifPresent(type -> registration.addRecipes(type, getBongFillingRecipes(registration.getIngredientManager())));
    }

}
