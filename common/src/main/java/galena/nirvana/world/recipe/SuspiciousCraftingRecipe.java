package galena.nirvana.world.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.Nullable;

public class SuspiciousCraftingRecipe extends CustomRecipe {

    private final ItemStack result;
    public final Ingredient base;
    public final int requiredFlowers;
    public final int requiredWeed;
    public final int durationFactor;

    public SuspiciousCraftingRecipe(CraftingBookCategory category, ItemStack result, Ingredient base, int requiredFlowers, int requiredWeed, int durationFactor) {
        super(category);
        this.result = result;
        this.base = base;
        this.requiredFlowers = requiredFlowers;
        this.requiredWeed = requiredWeed;
        this.durationFactor = durationFactor;
    }

    public ItemStack getContainer() {
        return result.copy();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        @Nullable Item flowerType = null;
        var flowerCount = 0;
        var weedCount = 0;
        var hasBase = false;

        for (int i = 0; i < input.size(); ++i) {
            var stack = input.getItem(i);

            if (NirvanaItems.WEED.isIn(stack)) {
                weedCount++;
            } else if (stack.is(ItemTags.SMALL_FLOWERS)) {
                if (flowerType == null) flowerType = stack.getItem();

                if (stack.is(flowerType)) flowerCount++;
                else return false;
            } else if (base.test(stack) && !hasBase) {
                hasBase = true;
            } else if (!stack.isEmpty()) {
                return false;
            }
        }

        return hasBase && weedCount == requiredWeed && flowerCount == requiredFlowers;
    }

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider lookup) {
        var result = getContainer();

        for (int i = 0; i < container.size(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                var holder = SuspiciousEffectHolder.tryGet(stack.getItem());
                if (holder != null) {
                    var effects = new SuspiciousStewEffects(
                            holder.getSuspiciousEffects().effects()
                                    .stream()
                                    .map(it -> new SuspiciousStewEffects.Entry(it.effect(), it.duration() * durationFactor))
                                    .toList()
                    );
                    result.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, effects);
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i*j >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NirvanaRecipeTypes.SUSPICIOUS_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<SuspiciousCraftingRecipe> {

        private static final MapCodec<SuspiciousCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
                builder.group(
                        CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(SuspiciousCraftingRecipe::category),
                        ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(it -> it.result),
                        Ingredient.CODEC.fieldOf("base").forGetter(it -> it.base),
                        Codec.INT.optionalFieldOf("flowers", 1).forGetter(it -> it.requiredFlowers),
                        Codec.INT.optionalFieldOf("weed", 1).forGetter(it -> it.requiredWeed),
                        Codec.INT.optionalFieldOf("durationFactor", 1).forGetter(it -> it.durationFactor)
                ).apply(builder, SuspiciousCraftingRecipe::new)
        );

        private static final StreamCodec<RegistryFriendlyByteBuf, SuspiciousCraftingRecipe> STREAM_CODEC = StreamCodec.composite(
            CraftingBookCategory.STREAM_CODEC,
                SuspiciousCraftingRecipe::category,
                ItemStack.STREAM_CODEC,
                it -> it.result,
                Ingredient.CONTENTS_STREAM_CODEC,
                it -> it.base,
                ByteBufCodecs.INT,
                it -> it.requiredFlowers,
                ByteBufCodecs.INT,
                it -> it.requiredWeed,
                ByteBufCodecs.INT,
                it -> it.durationFactor,
                SuspiciousCraftingRecipe::new
        );

        @Override
        public MapCodec<SuspiciousCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SuspiciousCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

    }

}
