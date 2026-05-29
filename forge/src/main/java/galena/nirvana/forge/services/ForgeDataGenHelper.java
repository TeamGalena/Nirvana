package galena.nirvana.forge.services;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.providers.loot.RegistrateEntityLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaBlocks;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.platform.services.IDataGenHelper;
import galena.nirvana.world.block.HempCropBlock;
import galena.nirvana.world.item.DeerStalkerItem;
import java.util.function.Function;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.Nullable;

public class ForgeDataGenHelper implements IDataGenHelper {

    private LootItemCondition.Builder hasAge(Block block, int age) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
            .setProperties(StatePropertiesPredicate.Builder.properties()
                .hasProperty(HempCropBlock.AGE, age)
            );
    }

    private LootItemCondition.Builder growthMissing(CropBlock block, int away) {
        return hasAge(block, block.getMaxAge() - away);
    }

    private LootItemCondition.Builder maxAge(CropBlock block) {
        return growthMissing(block, 0);
    }

    @Override
    public void hempCrop(DataGenContext<Block, ? extends CropBlock> context, RegistrateBlockstateProvider provider) {
        var block = context.getEntry();
        provider.getVariantBuilder(block).forAllStates(state -> {
            var age = block.getAge(state);

            var base = provider.blockTexture(block);
            var name = base.withSuffix("_" + age);

            var model = age < 3
                ? provider.models().singleTexture(name.getPath(), NirvanaConstants.createId("block/crop_cross"), "cross", name)
                : provider.models().getExistingFile(name);

            return ConfiguredModel.builder()
                .modelFile(model)
                .build();
        });
    }

    @Override
    public void hempCrop(RegistrateBlockLootTables provider, CropBlock block) {
        var fortune = provider.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        provider.add(block, provider.applyExplosionDecay(block,
            LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(AlternativesEntry.alternatives(
                            LootItem.lootTableItem(NirvanaItems.HEMP)
                                .when(maxAge(block))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.57F, 2)),
                            LootItem.lootTableItem(NirvanaItems.HEMP)
                                .when(growthMissing(block, -1))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.62F, 1)),
                            LootItem.lootTableItem(NirvanaItems.HEMP_SEEDS)
                        )
                    ))
                .withPool(LootPool.lootPool()
                    .add(AlternativesEntry.alternatives(
                            LootItem.lootTableItem(NirvanaItems.HEMP_SEEDS)
                                .when(maxAge(block))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.57F, 2)),
                            LootItem.lootTableItem(NirvanaItems.HEMP_SEEDS)
                                .when(growthMissing(block, -1))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.57F, 1))
                        )
                    )
                )
        ));
    }

    @Override
    public void crate(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var block = context.getEntry();
        var name = provider.blockTexture(block);
        var model = provider.models().cube(
            name.getPath(),
            name.withSuffix("_bottom"),
            name.withSuffix("_top"),
            name.withSuffix("_front"),
            name.withSuffix("_back"),
            name.withSuffix("_side"),
            name.withSuffix("_side")
        ).texture("particle", name.withSuffix("_top"));

        provider.getVariantBuilder(block).forAllStates(state -> {
            var facing = state.getValue(HorizontalDirectionalBlock.FACING);

            return ConfiguredModel.builder()
                .modelFile(model)
                .rotationY((int) facing.toYRot())
                .build();
        });
    }

    @Override
    public void flatItem(DataGenContext<Item, ? extends Item> context, RegistrateItemModelProvider provider) {
        provider.withExistingParent(context.getName() + "_flat", "item/generated").texture("layer0", provider.itemTexture(context));
        provider.getBuilder(context.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
    }

    @Override
    public void blissBloom(DataGenContext<Block, ? extends DoublePlantBlock> context, RegistrateBlockstateProvider provider) {
        provider.getVariantBuilder(context.get()).forAllStates(state -> {
            var half = state.getValue(DoublePlantBlock.HALF);
            var name = "block/" + context.getName() + "_" + half.getSerializedName();
            var model = provider.models().cross(name, provider.modLoc(name));
            return ConfiguredModel.builder()
                .modelFile(model)
                .build();
        });
    }

    @Override
    public void blissBloom(RegistrateBlockLootTables provider, DoublePlantBlock block) {
        provider.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
            .when(ExplosionCondition.survivesExplosion())
            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
            ))
            .add(LootItem.lootTableItem(block))
        ));
    }

    @Override
    public void wildHemp(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var block = context.get();
        provider.simpleBlock(block, provider.models().cross(context.getName(), provider.blockTexture(block)));
    }

    @Override
    public void wildHemp(RegistrateBlockLootTables provider, Block block) {
        provider.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
            .when(ExplosionCondition.survivesExplosion())
            .add(AlternativesEntry.alternatives(
                LootItem.lootTableItem(block)
                    .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(NirvanaTags.SHEARS))),
                LootItem.lootTableItem(NirvanaItems.HEMP)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
            ))
        ));
    }

    @Override
    public void pipe(DataGenContext<Item, ? extends Item> context, RegistrateItemModelProvider provider) {
        var parent = NirvanaConstants.createId("item/pipe_in_hand");
        provider.withExistingParent(context.getName(), parent).texture("layer0", provider.itemTexture(context));
    }

    @Override
    public void stuffedPipe(DataGenContext<Item, ? extends Item> context, RegistrateRecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, NirvanaItems.STUFFED_PIPE)
            .requires(NirvanaItems.EMPTY_PIPE)
            .requires(NirvanaItems.WEED)
            .requires(NirvanaItems.WEED)
            .requires(NirvanaItems.WEED)
            .unlockedBy("has_pipe", RegistrateRecipeProvider.has(NirvanaItems.EMPTY_PIPE))
            .save(provider);
    }

    @Override
    public void tnt(RegistrateBlockLootTables provider, Block block) {
        provider.add(block, LootTable.lootTable()
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                    StatePropertiesPredicate.Builder.properties().hasProperty(TntBlock.UNSTABLE, false)
                ))
            )));
    }

    @Override
    public void tnt(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        Function<String, ResourceLocation> texture = suffix -> provider.blockTexture(context.get()).withSuffix("_" + suffix);
        provider.simpleBlock(context.get(), provider.models().cubeBottomTop(context.getName(),
            texture.apply("side"),
            texture.apply("bottom"),
            texture.apply("top"))
        );
    }

    @Override
    public void thcMinecart(DataGenContext<Item, ? extends ItemLike> context, RegistrateRecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, context.get())
            .unlockedBy("has_thc", RegistrateRecipeProvider.has(NirvanaBlocks.THC))
            .unlockedBy("has_minecart", RegistrateRecipeProvider.has(Items.MINECART))
            .requires(NirvanaBlocks.THC)
            .requires(Items.MINECART)
            .save(provider);
    }

    @Override
    public void thc(DataGenContext<Item, ? extends ItemLike> context, RegistrateRecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, context.get())
            .unlockedBy("has_weed", RegistrateRecipeProvider.has(NirvanaItems.WEED))
            .unlockedBy("has_gunpowder", RegistrateRecipeProvider.has(Items.GUNPOWDER))
            .pattern("XOX")
            .pattern("OXO")
            .pattern("XOX")
            .define('O', NirvanaItems.WEED)
            .define('X', Items.GUNPOWDER)
            .save(provider);
    }

    @Override
    public void reefer(RegistrateEntityLootTables provider, EntityType<?> type) {
        provider.add(type, new LootTable.Builder()
            .withPool(LootPool.lootPool()
                .when(LootItemEntityPropertyCondition.hasProperties(
                    LootContext.EntityTarget.ATTACKER,
                    EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)
                ))
                .add(LootItem.lootTableItem(NirvanaItems.DISC_JAM))
            )
        );
    }

    @Override
    public void peaceBannerPattern(DataGenContext<Item, ? extends Item> context, RegistrateRecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, context.get())
            .requires(Items.PAPER)
            .requires(NirvanaItems.HEMP)
            .unlockedBy("has_hemp", RegistrateRecipeProvider.has(NirvanaItems.HEMP))
            .save(provider);
    }

    public <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> color(DyeColor color, NonNullSupplier<? extends ItemLike> from) {
        return (context, provider) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
                .requires(from.get())
                .requires(color.getTag())
                .unlockedBy("has_uncolored", RegistrateRecipeProvider.has(from.get()))
                .save(provider, context.getId().withSuffix("_dyeing"));
        };
    }

    @Override
    public <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> hempBurlap(@Nullable DyeColor color) {
        if (color != null) return color(color, NirvanaBlocks.HEMP_BURLAP);
        return (context, provider) -> {
            provider.square(DataIngredient.items(NirvanaItems.HEMP_CLOTH.get()), RecipeCategory.BUILDING_BLOCKS, context, true);
        };
    }

    @Override
    public void hempBurlap(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var model = provider.models().withExistingParent(context.getName(), "block/template_glazed_terracotta")
            .texture("pattern", provider.blockTexture(context.get()));

        provider.horizontalBlock(context.get(), model);
    }

    @Override
    public void wovenHempBurlap(DataGenContext<Block, ? extends RotatedPillarBlock> context, RegistrateBlockstateProvider provider) {
        var texture = provider.blockTexture(context.get());
        provider.axisBlock(context.get(), texture.withSuffix("_side"), texture.withSuffix("_top"));
    }

    @Override
    public <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> wovenHempBurlap(@Nullable DyeColor color) {
        return (context, provider) -> {
            if (color != null) this.<T>color(color, NirvanaBlocks.WOVEN_BURLAP).accept(context, provider);
            var from = color == null ? NirvanaBlocks.HEMP_BURLAP : NirvanaBlocks.COLORED_HEMP_BURLAP.get(color);
            RegistrateRecipeProvider.polished(provider, RecipeCategory.BUILDING_BLOCKS, context.get(), from.get());
        };
    }

    @Override
    public void deerStalker(DataGenContext<Item, DeerStalkerItem> context, RegistrateRecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, context.get())
            .pattern("XXX")
            .pattern("X X")
            .define('X', NirvanaItems.HEMP_CLOTH.get())
            .unlockedBy("has_hemp_cloth", RegistrateRecipeProvider.has(NirvanaItems.HEMP_CLOTH))
            .save(provider);
    }

    @Override
    public void pottedPlant(DataGenContext<Block, ? extends FlowerPotBlock> context, RegistrateBlockstateProvider provider) {
        var model = provider.models().withExistingParent(context.getName(), "block/flower_pot_cross")
            .texture("plant", provider.blockTexture(context.get().getPotted()));
        provider.simpleBlock(context.get(), model);
    }

    @Override
    public void pottedPlant(RegistrateBlockLootTables provider, Block block) {
        provider.dropPottedContents(block);
    }

    @Override
    public void skull(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var model = provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull"));
        provider.simpleBlock(context.get(), model);
    }

}
