package galena.nirvana.fabric;

import static galena.nirvana.NirvanaConstants.MOD_ID;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import galena.nirvana.NirvanaCommon;
import galena.nirvana.NirvanaTrades;
import galena.nirvana.compat.DyeColors;
import galena.nirvana.fabric.services.FabricConfigs;
import galena.nirvana.index.NirvanaBanners;
import galena.nirvana.index.NirvanaBlocks;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.world.item.PotionBongItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class FabricEntrypoint implements ModInitializer {

    public static final FabricNirvanaRegistrate REGISTRATE = new FabricNirvanaRegistrate(MOD_ID);

    private static final ResourceKey<PlacedFeature> WILD_HEMP_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, "patch_wild_hemp"));

    private static final ProviderType<RegistrateTagsProvider.IntrinsicImpl<BannerPattern>> BANNER_PATTERN_TAGS = ProviderType.register("tags/banner_pattern", type -> (p, e) ->
            new RegistrateTagsProvider.IntrinsicImpl<>(p, type, "blocks", e.output(), Registries.BANNER_PATTERN, e.registriesLookup(), it -> BuiltInRegistries.BANNER_PATTERN.getResourceKey(it).orElseThrow())
    );

    @Override
    public void onInitialize() {
        FabricConfigs.register();
        NirvanaCommon.init();
        REGISTRATE.register();

        modifyLootTables();

        BiomeModifications.addFeature(BiomeSelectors.tag(NirvanaTags.GENERATES_WILD_HEMP), GenerationStep.Decoration.VEGETAL_DECORATION, WILD_HEMP_FEATURE);

        NirvanaTrades.register((profession, level, listing) ->
                TradeOfferHelper.registerVillagerOffers(profession, level, list -> list.add(listing))
        );

        setupAdditionalDatagen();
    }

    private static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resources, manager, id, table, source) -> {
            if (!source.isBuiltin()) return;
            if (BuiltInLootTables.SNIFFER_DIGGING.equals(id)) {
                table.modifyPools(it -> {
                    it.add(LootItem.lootTableItem(NirvanaBlocks.BLISS_BLOOM));
                });
            } else if (BuiltInLootTables.IGLOO_CHEST.equals(id)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(NirvanaItems.EMPTY_PIPE))
                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                );
            } else if (BuiltInLootTables.WOODLAND_MANSION.equals(id)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(NirvanaItems.EMPTY_PIPE))
                        .when(LootItemRandomChanceCondition.randomChance(0.33F))
                );
            }
        });
    }

    private static void setupAdditionalDatagen() {
        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, provider ->
                provider.addTag(NirvanaTags.CREEPER_LIKE).add(EntityType.CREEPER)
        );

        REGISTRATE.addDataGenerator(BANNER_PATTERN_TAGS, provider -> {
            provider.addTag(NirvanaTags.PEACE_BANNER_PATTERN).add(NirvanaBanners.PEACE.get());
        });

        REGISTRATE.addDataGenerator(ProviderType.RECIPE, provider -> {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LEATHER)
                    .pattern("XXX")
                    .pattern("XXX")
                    .define('X', NirvanaItems.HEMP_CLOTH.get())
                    .unlockedBy("has_hemp", RegistrateRecipeProvider.has(NirvanaItems.HEMP_CLOTH))
                    .save(provider, new ResourceLocation(MOD_ID, "leather_from_hemp"));
        });

        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, provider ->
                DyeColors.supported().forEach(color -> {
                    var forgeTag = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "dyes/" + color));
                    provider.addTag(color.getTag()).addOptionalTag(forgeTag);
                })
        );

        REGISTRATE.addRawLang(PotionBongItem.PATTERN_TRANSLATION_KEY, "^Potion of (.+)$");
    }

}
