package galena.nirvana.fabric.datagen;

import com.possible_triangle.multikulti.datagen.conditions.Conditional;
import com.possible_triangle.multikulti.datagen.conditions.Inverted;
import com.possible_triangle.multikulti.datagen.conditions.ModLoaded;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.fabric.datagen.CompatRegistrate.Mods;
import galena.nirvana.index.NirvanaItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class AdditionalNirvanaRecipes extends FabricRecipeProvider {

    public AdditionalNirvanaRecipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD)
                .pattern("sh ")
                .pattern("hs ")
                .pattern("  s")
                .define('s', Items.STRING)
                .define('h', NirvanaItems.HEMP.get())
                .unlockedBy("has_hemp", has(NirvanaItems.HEMP))
                .save(output, NirvanaConstants.createId("lead_from_hemp"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEATHER)
                .pattern("hh")
                .pattern("hh")
                .define('h', NirvanaItems.HEMP.get())
                .unlockedBy("has_hemp", has(NirvanaItems.HEMP))
                .save(output, NirvanaConstants.createId("leather_from_hemp"));

        Conditional.with(
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CompatRegistrate.FARMERS_DELIGHT_ROPE, 2)
                        .pattern("h")
                        .pattern("h")
                        .define('h', NirvanaItems.HEMP.get())
                        .unlockedBy("has_hemp", has(NirvanaItems.HEMP)),
                new ModLoaded(Mods.FARMERS_DELIGHT), new Inverted(new ModLoaded(Mods.SUPPLEMENTARIES))
        ).save(exporter, NirvanaConstants.createId("fd_rope_from_hemp"));

        Conditional.with(
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CompatRegistrate.SUPPLEMENTARIES_ROPE, 2)
                        .pattern("h")
                        .pattern("h")
                        .define('h', NirvanaItems.HEMP.get())
                        .unlockedBy("has_hemp", has(NirvanaItems.HEMP)),
                new ModLoaded(Mods.SUPPLEMENTARIES)
        ).save(exporter, NirvanaConstants.createId("supplementaries_rope_from_hemp"));
    }

}
