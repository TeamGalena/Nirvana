package galena.nirvana.index;

import galena.nirvana.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class NirvanaBrewing {

    private static ItemStack withPotion(ItemLike item, Holder<Potion> potion) {
        var stack = new ItemStack(item);
        var contents = new PotionContents(potion);
        stack.set(DataComponents.POTION_CONTENTS, contents);
        return stack;
    }

    private static boolean isWater(ItemStack stack) {
        return stack.get(DataComponents.POTION_CONTENTS).is(Potions.WATER);
    }

    private static void registerMix(PotionBrewing.Builder brewing, ItemStack ingredient, ItemStack from) {
        var vanilla = brewing.build();
        var input = isWater(from) ? Ingredient.of(NirvanaItems.BONG) : Services.PLATFORM.createNBTIngredient(from);
        var output = vanilla.mix(ingredient, from);
        if (output == from) return;
        Services.BREWING.addRecipe(brewing, input, Ingredient.of(ingredient), output);
    }

    private static void registerBongRecipes(PotionBrewing.Builder builder) {
        var vanilla = builder.build();
        var waterBottle = withPotion(Items.POTION, Potions.WATER);

        Services.BREWING.addRecipe(
            builder,
            Services.PLATFORM.createNBTIngredient(waterBottle),
            Ingredient.of(NirvanaItems.WEED),
            NirvanaItems.BONG.asStack()
        );

        var catalysts = BuiltInRegistries.ITEM.stream()
            .map(ItemStack::new)
            .filter(stack -> {
                try {
                    return vanilla.isIngredient(stack);
                } catch (IllegalStateException e) {
                    return false;
                }
            }).toList();

        BuiltInRegistries.POTION.holders()
            .filter(it -> !it.is(NirvanaTags.NO_BONG))
            .forEach(potion -> {
                var from = withPotion(NirvanaItems.POTION_BONG, potion);
                var potionStack = withPotion(Items.POTION, potion);
                catalysts.stream().filter(it -> {
                    try {
                        return vanilla.hasMix(potionStack, it);
                    } catch (IllegalStateException e) {
                        return false;
                    }
                }).forEach(catalyst -> registerMix(builder, catalyst, from));
            });
    }

    public static void register(PotionBrewing.Builder builder) {
        registerBongRecipes(builder);
    }

}
