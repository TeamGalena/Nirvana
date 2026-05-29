package galena.nirvana.index;

import galena.nirvana.NirvanaConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class NirvanaTags {

    public static final TagKey<Block>[] HEMP_SEASONS_BLOCKS = new TagKey[] {
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sereneseasons", "spring_crops")),
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sereneseasons", "summer_crops"))
    };

    public static final TagKey<Item>[] HEMP_SEASONS_ITEMS = new TagKey[] {
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("sereneseasons", "spring_crops")),
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("sereneseasons", "summer_crops"))
    };

    public static final TagKey<Item> NAUSEATING = TagKey.create(Registries.ITEM, NirvanaConstants.createId("nauseating"));

    public static final TagKey<Item> ATTACHED_TO_HEAD = TagKey.create(Registries.ITEM, NirvanaConstants.createId("attached_to_head"));

    public static final TagKey<Biome> GENERATES_WILD_HEMP = TagKey.create(Registries.BIOME, NirvanaConstants.createId("has_feature/wild_hemp"));

    public static final TagKey<Item> SHEARS = TagKey.create(Registries.ITEM, NirvanaConstants.createId("shears"));

    public static final TagKey<Item> SMOKING_ITEM = TagKey.create(Registries.ITEM, NirvanaConstants.createId("smoking_item"));

    public static final TagKey<EntityType<?>> CREEPER_LIKE = TagKey.create(Registries.ENTITY_TYPE, NirvanaConstants.createId("creeper_like"));

    public static final TagKey<Block> SMOKING_CRATES = TagKey.create(Registries.BLOCK, NirvanaConstants.createId("smoking_crates"));

    public static final TagKey<Block>[] STORAGE_BLOCKS = platformSpecific(Registries.BLOCK, "storage_blocks");

    public static final TagKey<BannerPattern> PEACE_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, NirvanaConstants.createId("peace_banner_patterns"));

    public static final TagKey<Item> BURLAP = TagKey.create(Registries.ITEM, NirvanaConstants.createId("burlap"));

    public static final TagKey<Item> CHICKEN_FOOD = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("chicken_food"));

    public static final TagKey<Item>[] SEEDS = platformSpecific(Registries.ITEM, "seeds");

    public static final TagKey<Item>[] HEADS = platformSpecific(Registries.ITEM, "heads");

    @SuppressWarnings("unchecked")
    private static <T> TagKey<T>[] platformSpecific(ResourceKey<Registry<T>> registry, String path) {
        return (TagKey<T>[]) new TagKey[]{
                TagKey.create(registry, ResourceLocation.fromNamespaceAndPath("forge", path)),
                TagKey.create(registry, ResourceLocation.fromNamespaceAndPath("c", path)),
        };
    }

}
