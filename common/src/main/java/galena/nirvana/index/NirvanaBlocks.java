package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import galena.nirvana.compat.DyeColors;
import galena.nirvana.platform.Services;
import galena.nirvana.world.block.CrateBlock;
import galena.nirvana.world.block.HempCropBlock;
import galena.nirvana.world.block.ModdedSkullBlock;
import galena.nirvana.world.block.ModdedWallSkullBlock;
import galena.nirvana.world.block.ThcBlock;
import galena.nirvana.world.block.WildHempBlock;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class NirvanaBlocks {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final BlockEntry<HempCropBlock> HEMP = REGISTRATE
            .block("hemp", HempCropBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .addLayer(() -> RenderType::cutout)
            .blockstate(Services.DATAGEN::hempCrop)
            .tag(BlockTags.CROPS)
            .tag(NirvanaTags.SUMMER_CROPS)
            .loot(Services.DATAGEN::hempCrop)
            .register();

    public static final BlockEntry<CrateBlock> HEMP_CRATE = REGISTRATE
            .block("hemp_crate", CrateBlock::new)
            .initialProperties(() -> Blocks.BARREL)
            .blockstate(Services.DATAGEN::crate)
            .recipe((c, p) -> p.storage(NirvanaItems.HEMP, RecipeCategory.DECORATIONS, c))
            .tag(BlockTags.MINEABLE_WITH_AXE)
            .tag(NirvanaTags.SMOKING_CRATES)
            .item()
            .tab(CreativeModeTabs.BUILDING_BLOCKS)
            .build()
            .register();

    public static final BlockEntry<CrateBlock> WEED_CRATE = REGISTRATE
            .block("weed_crate", CrateBlock::new)
            .initialProperties(() -> Blocks.BARREL)
            .blockstate(Services.DATAGEN::crate)
            .recipe((c, p) -> p.storage(NirvanaItems.WEED, RecipeCategory.DECORATIONS, c))
            .tag(BlockTags.MINEABLE_WITH_AXE)
            .tag(NirvanaTags.SMOKING_CRATES)
            .item()
            .tab(CreativeModeTabs.BUILDING_BLOCKS)
            .build()
            .register();

    public static final BlockEntry<DoublePlantBlock> BLISS_BLOOM = REGISTRATE
            .block("bliss_bloom", DoublePlantBlock::new)
            .initialProperties(() -> Blocks.ROSE_BUSH)
            .addLayer(() -> RenderType::cutout)
            .blockstate(Services.DATAGEN::blissBloom)
            .loot(Services.DATAGEN::blissBloom)
            .tag(BlockTags.TALL_FLOWERS)
            .item()
            .tab(CreativeModeTabs.NATURAL_BLOCKS)
            .model((c, p) -> p.generated(c, p.modLoc("block/" + c.getName() + "_upper")))
            .onRegister(it -> ComposterBlock.COMPOSTABLES.put(it, 0.85F))
            .build()
            .register();

    public static final BlockEntry<? extends BushBlock> WILD_HEMP = REGISTRATE
            .block("wild_hemp", WildHempBlock::new)
            .initialProperties(() -> Blocks.FERN)
            .addLayer(() -> RenderType::cutout)
            .blockstate(Services.DATAGEN::wildHemp)
            .loot(Services.DATAGEN::wildHemp)
            .tag(BlockTags.SMALL_FLOWERS)
            .item()
            .tab(CreativeModeTabs.NATURAL_BLOCKS)
            .model((c, p) -> p.blockSprite(c))
            .onRegister(it -> ComposterBlock.COMPOSTABLES.put(it, 0.65F))
            .build()
            .register();

    public static final BlockEntry<ThcBlock> THC = REGISTRATE
            .block("thc", ThcBlock::new)
            .lang("THC")
            .initialProperties(() -> Blocks.TNT)
            .blockstate(Services.DATAGEN::tnt)
            .loot(Services.DATAGEN::tnt)
            .item()
            .tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .recipe(Services.DATAGEN::thc)
            .build()
            .register();

    public static final SkullBlock.Type REEFER_SKULL_TYPE = () -> "reefer";

    public static final BlockEntry<? extends SkullBlock> REEFER_HEAD = REGISTRATE
            .block("reefer_head", it -> new ModdedSkullBlock(REEFER_SKULL_TYPE, it))
            .lang("Reefer Head")
            .initialProperties(() -> Blocks.CREEPER_HEAD)
            .blockstate(Services.DATAGEN::skull)
            .register();

    public static final BlockEntry<? extends WallSkullBlock> REEFER_WALL_HEAD = REGISTRATE
            .block("reefer_wall_head", it -> new ModdedWallSkullBlock(REEFER_SKULL_TYPE, it))
            .lang("Reefer Head")
            .initialProperties(() -> Blocks.CREEPER_WALL_HEAD)
            .blockstate(Services.DATAGEN::skull)
            .register();

    public static final BlockEntityEntry<SkullBlockEntity> MODDED_SKULL = REGISTRATE
            .<SkullBlockEntity>blockEntity("skull", (type, pos, state) -> new SkullBlockEntity(pos, state))
            .renderer(() -> SkullBlockRenderer::new)
            .validBlocks(REEFER_HEAD, REEFER_WALL_HEAD)
            .register();

    public static final BlockEntry<FlowerPotBlock> POTTED_WILD_HEMP = REGISTRATE
            .block("potted_wild_hemp", it -> new FlowerPotBlock(WILD_HEMP.get(), it))
            .lang("Potted Hemp")
            .initialProperties(() -> Blocks.POTTED_CACTUS)
            .addLayer(() -> RenderType::cutout)
            .blockstate(Services.DATAGEN::pottedPlant)
            .loot(Services.DATAGEN::pottedPlant)
            .register();

    private static BlockEntry<GlazedTerracottaBlock> createHempBurlap(@Nullable DyeColor color) {
        var name = "hemp_burlap";
        var translation = "Burlap";
        return REGISTRATE
                .block(color == null ? name : color + "_" + name, GlazedTerracottaBlock::new)
                .lang(color == null ? translation : RegistrateLangProvider.toEnglishName(color.getSerializedName()) + " " + translation)
                .initialProperties(() -> Blocks.HAY_BLOCK)
                .properties(it -> it.sound(SoundType.WOOL))
                .properties(it -> color == null ? it.mapColor(MapColor.WOOL) : it.mapColor(color))
                .blockstate(Services.DATAGEN::hempBurlap)
                .tag(BlockTags.SWORD_EFFICIENT)
                .item()
                .tab(CreativeModeTabs.BUILDING_BLOCKS)
                .recipe(Services.DATAGEN.hempBurlap(color))
                .tag(NirvanaTags.BURLAP)
                .build()
                .register();
    }

    public static final BlockEntry<GlazedTerracottaBlock> HEMP_BURLAP = createHempBurlap(null);
    public static final Map<DyeColor, BlockEntry<GlazedTerracottaBlock>> COLORED_HEMP_BURLAP = colored(NirvanaBlocks::createHempBurlap);

    private static BlockEntry<RotatedPillarBlock> createWovenBurlap(@Nullable DyeColor color) {
        var name = "woven_burlap";
        return REGISTRATE
                .block(color == null ? name : color + "_" + name, RotatedPillarBlock::new)
                .initialProperties(() -> Blocks.HAY_BLOCK)
                .properties(it -> it.sound(SoundType.WOOL))
                .properties(it -> color == null ? it.mapColor(MapColor.WOOL) : it.mapColor(color))
                .blockstate(Services.DATAGEN::wovenHempBurlap)
                .tag(BlockTags.SWORD_EFFICIENT)
                .item()
                .tab(CreativeModeTabs.BUILDING_BLOCKS)
                .recipe(Services.DATAGEN.wovenHempBurlap(color))
                .build()
                .register();
    }

    public static final BlockEntry<RotatedPillarBlock> WOVEN_BURLAP = createWovenBurlap(null);
    public static final Map<DyeColor, BlockEntry<RotatedPillarBlock>> COLORED_WOVEN_BURLAP = colored(NirvanaBlocks::createWovenBurlap);

    private static <T> Map<DyeColor, T> colored(Function<DyeColor, T> mapper) {
        return DyeColors.supported().collect(Collectors.toMap(it -> it, mapper));
    }

    public static void register() {
        // loads this class
    }

}
