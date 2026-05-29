package galena.nirvana.forge.data;

import static galena.nirvana.forge.ForgeEntrypoint.REGISTRATE;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import galena.nirvana.compat.DyeColors;
import galena.nirvana.index.NirvanaBanners;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.world.item.PotionBongItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class DataEntrypoint {

    private static final ProviderType<RegistrateTagsProvider.IntrinsicImpl<BannerPattern>> BANNER_PATTERN_TAGS =
        ProviderType.registerIntrinsicTag("Banner Tags", "tags/banner_pattern", Registries.BANNER_PATTERN, null);

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onInitializeDataGenerator(GatherDataEvent event) {
        addDefaultTranslations();

        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var existing = event.getExistingFileHelper();
        var registries = event.getLookupProvider();

        generator.addProvider(true, new AdditionalNirvanaRecipes(output, registries));
        generator.addProvider(true, new PackMetadataProvider(output));
        generator.addProvider(true, new DataRegistries(output, registries));
        generator.addProvider(true, new NirvanaBiomeTagsProvider(output, registries, existing));
        generator.addProvider(true, new NirvanaPotionTagsProvider(output, registries, existing));

        new PotionTranslationProvider(REGISTRATE).addTranslations();

        var initializer = REGISTRATE.getDataGenInitializer();
        initializer.addDependency(BANNER_PATTERN_TAGS, ProviderType.DYNAMIC);

        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, provider ->
            provider.addTag(NirvanaTags.CREEPER_LIKE).add(EntityType.CREEPER)
        );

        REGISTRATE.addDataGenerator(BANNER_PATTERN_TAGS, provider -> {
            provider.addTag(NirvanaTags.PEACE_BANNER_PATTERN).add(NirvanaBanners.PEACE.getKey());
        });

        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, provider ->
            DyeColors.supported().forEach(color -> {
                var forgeTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "dyes/" + color));
                provider.addTag(color.getTag()).addOptionalTag(forgeTag);
            })
        );

        REGISTRATE.addRawLang(PotionBongItem.PATTERN_TRANSLATION_KEY, "^Potion of (.+)$");
    }

    private static void addDefaultTranslations() {
        var peaceId = NirvanaEffects.PEACE.getId();
        REGISTRATE.addLang("effect", peaceId, "Peace");
        REGISTRATE.addLang("effect", peaceId, "description", "A blissful aura befogs your brain");

        REGISTRATE.addLang("item", NirvanaItems.POTION_BONG.getId(), "effect.empty", "Effect Bong");
    }

}
