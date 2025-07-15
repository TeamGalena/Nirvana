package galena.nirvana.index;

import galena.nirvana.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
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

   // private static void registerMix(PotionBrewing.Builder brewing, ItemStack ingredient, ItemStack from) {
   //     var input = isWater(from) ? Ingredient.of(NirvanaItems.BONG) : Services.PLATFORM.createNBTIngredient(from);
   //     var output = brewing.mix(ingredient, from);
   //     if (output == from) return;
   //     Services.BREWING.addRecipe(, input, Ingredient.of(ingredient), output);
   // }

    private static void registerBongRecipes(PotionBrewing.Builder builder) {
  //      var vanilla = builder.build();
        var waterBottle = withPotion(Items.POTION, Potions.WATER);

        Services.BREWING.addRecipe(
                builder,
                Services.PLATFORM.createNBTIngredient(waterBottle),
                Ingredient.of(NirvanaItems.WEED),
                NirvanaItems.BONG.asStack()
        );

        builder.addContainer(NirvanaItems.POTION_BONG.asItem());

//        var catalysts = BuiltInRegistries.ITEM.stream()
//                .map(ItemStack::new)
//                .filter(vanilla::isIngredient)
//                .toList();
//

        //BuiltInRegistries.POTION.holders().forEach(potion -> {
        //    var from = withPotion(NirvanaItems.POTION_BONG, potion);
        //    var potionStack = withPotion(Items.POTION, potion);
        //    catalysts.stream()
        //            .filter(it -> vanilla.hasMix(potionStack, it))
        //            .forEach(catalyst -> registerMix(brewing, catalyst, from));
        //});
    }

    public static void register(PotionBrewing.Builder builder) {
        registerBongRecipes(builder);
    }

}
