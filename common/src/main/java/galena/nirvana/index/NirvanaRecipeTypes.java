package galena.nirvana.index;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.recipe.SuspicousCraftingRecipe;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.FlowerBlock;

public class NirvanaRecipeTypes {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<? extends RecipeSerializer<?>> SUSPICIOUS_RECIPE_SERIALIZER = REGISTRATE
            .generic("suspicious_crafting", Registries.RECIPE_SERIALIZER, SuspicousCraftingRecipe.Serializer::new)
            .register();

    public static Stream<Pair<ItemLike, ItemStack>> getSuspiciousVariants(ItemLike output, int factor) {
        return BuiltInRegistries.ITEM.getTag(ItemTags.SMALL_FLOWERS)
                .stream()
                .flatMap(HolderSet.ListBacked::stream)
                .map(Holder::value)
                .filter(BlockItem.class::isInstance)
                .map(item -> ((BlockItem) item).getBlock())
                .filter(FlowerBlock.class::isInstance)
                .map(FlowerBlock.class::cast)
                .map(flower -> {
                    var outputStack = new ItemStack(output);
                    var effects = flower.getSuspiciousEffects().effects();
                    var modifiedEffects = new SuspiciousStewEffects(effects
                            .stream()
                            .map(it -> new SuspiciousStewEffects.Entry(it.effect(), it.duration() * factor))
                            .toList()
                    );
                    outputStack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, modifiedEffects);

                    return new Pair<>(flower, outputStack);
                });
    }

    private static Stream<RecipeHolder<CraftingRecipe>> createSuspiciousRecipes(Ingredient base, ItemLike result, int flowerCount, int weedCount, int factor) {
        var group = BuiltInRegistries.ITEM.getKey(result.asItem());
        var weed = Ingredient.of(NirvanaItems.WEED);

        return getSuspiciousVariants(result, factor).map(pair -> {
            var flowerBlock = pair.getFirst().asItem();
            var output = pair.getSecond();
            var type = BuiltInRegistries.ITEM.getKey(flowerBlock);

            Ingredient flower = Ingredient.of(flowerBlock);
            NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(flowerCount + weedCount + 1);
            for (int i = 0; i < flowerCount; i++) inputs.add(flower);
            for (int i = 0; i < weedCount; i++) inputs.add(weed);
            inputs.add(base);

            ResourceLocation id = group.withSuffix("/" + type.getNamespace() + "/" + type.getPath());
            var recipe =  new ShapelessRecipe(group.toString(), CraftingBookCategory.MISC, output, inputs);
            return new RecipeHolder<>(id, recipe);
        });
    }

    public static List<RecipeHolder<CraftingRecipe>> createSuspiciousRecipes() {
        return Stream.of(
                createSuspiciousRecipes(Ingredient.of(Items.BOWL), NirvanaItems.HERBAL_SALVE, 3, 3, Services.CONFIG.common().herbalSalveFactor()),
                createSuspiciousRecipes(Ingredient.of(NirvanaItems.EMPTY_PIPE), NirvanaItems.SUSPICIOUS_PIPE, 6, 1, Services.CONFIG.common().suspiciousPipeFactor())
        ).flatMap(Function.identity()).toList();
    }

    public static void register() {
        // loads this class
    }

}
