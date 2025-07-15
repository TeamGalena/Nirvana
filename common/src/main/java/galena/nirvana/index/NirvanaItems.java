package galena.nirvana.index;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.CreativeModeTabModifier;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import galena.nirvana.NirvanaClient;
import galena.nirvana.platform.Services;
import galena.nirvana.world.item.BongItem;
import galena.nirvana.world.item.CustomMinecartItem;
import galena.nirvana.world.item.FilledPipeItem;
import galena.nirvana.world.item.HerbalSalveItem;
import galena.nirvana.world.item.JointItem;
import galena.nirvana.world.item.PotionBongItem;
import galena.nirvana.world.item.SuspiciousPipeItem;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.ComposterBlock;

public class NirvanaItems {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final ItemEntry<Item> HEMP = REGISTRATE
            .item("hemp", Item::new)
            .tab(CreativeModeTabs.INGREDIENTS)
            .onRegister(it -> ComposterBlock.COMPOSTABLES.put(it, 0.65F))
            .register();

    public static final ItemEntry<ItemNameBlockItem> HEMP_SEEDS = REGISTRATE
            .item("hemp_seeds", p -> new ItemNameBlockItem(NirvanaBlocks.HEMP.get(), p))
            .tag(NirvanaTags.SEEDS)
            .tag(NirvanaTags.CHICKEN_FOOD)
            .tab(CreativeModeTabs.NATURAL_BLOCKS)
            .recipe((c, p) -> p.singleItem(DataIngredient.items(HEMP.get()), RecipeCategory.MISC, c, 1, 2))
            .onRegister(it -> ComposterBlock.COMPOSTABLES.put(it, 0.3F))
            .register();

    public static final ItemEntry<Item> WEED = REGISTRATE
            .item("weed", Item::new)
            .lang("Weed Bud")
            .tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .recipe((c, p) -> {
                p.smelting(DataIngredient.items(HEMP.get()), RecipeCategory.MISC, c, 0.25F);
                p.smoking(DataIngredient.items(HEMP.get()), RecipeCategory.MISC, c, 0.25F);
                p.campfire(DataIngredient.items(HEMP.get()), RecipeCategory.MISC, c, 0.25F);
            })
            .register();

    private static final FoodProperties BROWNIE_FOOD = new FoodProperties.Builder()
            .effect(new MobEffectInstance(NirvanaEffects.PEACE, 20 * Services.CONFIG.common().browniesPeaceSeconds(), 0), 1.0F)
            .nutrition(2)
            .saturationModifier(0.1F)
            .build();

    public static final ItemEntry<Item> WEED_BROWNIE = REGISTRATE
            .item("weed_brownie", Item::new)
            .properties(it -> it.food(BROWNIE_FOOD))
            .tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .recipe((c, p) -> ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.FOOD, c.get(), 2)
                    .requires(HEMP_SEEDS)
                    .requires(Items.WHEAT)
                    .requires(Items.COCOA_BEANS)
                    .unlockedBy("has_hemp_seed", RegistrateRecipeProvider.has(HEMP_SEEDS))
                    .save(p)
            )
            .register();

    public static final ItemEntry<BongItem> BONG = REGISTRATE
            .item("bong", BongItem::new)
            .tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .properties(it -> it.durability(Services.CONFIG.common().getBongHits()))
            .properties(it -> it.craftRemainder(Items.GLASS_BOTTLE))
            .register();

    private static <T extends Item> Consumer<CreativeModeTabModifier> addPotionStacks(ItemBuilder<T, ?> item) {
        return modifier -> BuiltInRegistries.POTION.holders()
                .filter(it -> !it.is(Potions.WATER))
                .map(it -> {
                    var stack = new ItemStack(item.getEntry());
                    stack.set(DataComponents.POTION_CONTENTS, new PotionContents(it));
                    return stack;
                })
                .forEach(modifier::accept);
    }

    private static <T extends Item> Consumer<CreativeModeTabModifier> addSuspiciousStack(ItemBuilder<T, ?> item, IntSupplier factor) {
        return modifier -> NirvanaRecipeTypes.getSuspiciousVariants(item.getEntry(), factor.getAsInt())
                .map(Pair::getSecond)
                .forEach(modifier::accept);
    }

    public static final ItemEntry<PotionBongItem> POTION_BONG = REGISTRATE
            .item("potion_bong", PotionBongItem::new)
            .lang("Bong of %s")
            .transform(it -> it.tab(CreativeModeTabs.FOOD_AND_DRINKS, NirvanaItems.addPotionStacks(it)))
            .color(() -> () -> NirvanaClient.POTION_COLOR)
            .properties(it -> it.durability(Services.CONFIG.common().getBongHits()))
            .properties(it -> it.craftRemainder(Items.GLASS_BOTTLE))
            .tag(NirvanaTags.SMOKING_ITEM)
            .model((c, p) -> p.generated(c, p.modLoc("item/bong_potion"), p.modLoc("item/bong_potion_overlay")))
            .register();

    public static final ItemEntry<JointItem> JOINT = REGISTRATE
            .item("joint", Services.PLATFORM::createJointItem)
            .properties(it -> it.durability(Services.CONFIG.common().getJointHits()))
            .tag(NirvanaTags.NAUSEATING)
            .tag(NirvanaTags.SMOKING_ITEM)
            .tag(NirvanaTags.ATTACHED_TO_HEAD)
            .tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .model(Services.DATAGEN::flatItem)
            .recipe((c, p) -> ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.FOOD, c.get())
                    .requires(Items.PAPER)
                    .requires(WEED)
                    .unlockedBy("has_weed", RegistrateRecipeProvider.has(WEED))
                    .save(p)
            )
            .register();

    public static final ItemEntry<HerbalSalveItem> HERBAL_SALVE = REGISTRATE
            .item("herbal_salve", HerbalSalveItem::new)
            .properties(it -> it.stacksTo(1))
            .properties(it -> it.craftRemainder(Items.BOWL))
            .transform(it -> it.tab(CreativeModeTabs.FOOD_AND_DRINKS, NirvanaItems.addSuspiciousStack(it, () -> Services.CONFIG.common().herbalSalveFactor())))
            .register();

    public static final ItemEntry<? extends Item> DISC_JAM = REGISTRATE
            .item("music_disc_jam", Item::new)
            .properties(it -> it.stacksTo(1))
            .properties(it -> it.rarity(Rarity.RARE))
            .properties(it -> it.jukeboxPlayable(NirvanaSounds.JAM_KEY))
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .setData(ProviderType.LANG, (context, provider) -> {
                provider.add(context.get(), "Music Disc");
                provider.add(context.get().getDescriptionId() + ".desc", "Jam - firch");
            })
            .register();

    public static final ItemEntry<? extends Item> EMPTY_PIPE = REGISTRATE
            .item("old_pipe", Item::new)
            .properties(it -> it.stacksTo(1))
            .properties(it -> it.rarity(Rarity.UNCOMMON))
            .model(Services.DATAGEN::pipe)
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register();

    public static final ItemEntry<? extends Item> STUFFED_PIPE = REGISTRATE
            .item("stuffed_pipe", FilledPipeItem::new)
            .properties(it -> it.durability(Services.CONFIG.common().getPipeHits()))
            .properties(it -> it.rarity(Rarity.UNCOMMON))
            .properties(it -> it.craftRemainder(EMPTY_PIPE.asItem()))
            .model(Services.DATAGEN::pipe)
            .tag(NirvanaTags.SMOKING_ITEM)
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .recipe(Services.DATAGEN::stuffedPipe)
            .register();

    public static final ItemEntry<? extends Item> SUSPICIOUS_PIPE = REGISTRATE
            .item("suspicious_pipe", SuspiciousPipeItem::new)
            .properties(it -> it.durability(Services.CONFIG.common().getPipeHits()))
            .properties(it -> it.rarity(Rarity.UNCOMMON))
            .properties(it -> it.craftRemainder(EMPTY_PIPE.asItem()))
            .model(Services.DATAGEN::pipe)
            .tag(NirvanaTags.SMOKING_ITEM)
            .transform(it -> it.tab(CreativeModeTabs.TOOLS_AND_UTILITIES, NirvanaItems.addSuspiciousStack(it, () -> Services.CONFIG.common().suspiciousPipeFactor())))
            .register();

    public static final ItemEntry<? extends Item> REEFER_SPAWN_EGG = REGISTRATE
            .item("reefer_spawn_egg", it -> Services.PLATFORM.createSpawnEggItem(NirvanaEntities.REEFER, 0x619932, 0x2f4f15, it))
            .color(() -> () -> (ItemColor) (stack, i) -> ((SpawnEggItem) stack.getItem()).getColor(i))
            .model((c, p) -> p.withExistingParent(c.getName(), "item/template_spawn_egg"))
            .tab(CreativeModeTabs.SPAWN_EGGS)
            .register();

    public static final ItemEntry<? extends Item> THC_MINECART = REGISTRATE
            .item("thc_minecart", it -> new CustomMinecartItem(it, NirvanaEntities.THC_MINECART))
            .properties(it -> it.stacksTo(1))
            .lang("Minecart with THC")
            .recipe(Services.DATAGEN::thcMinecart)
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .onRegister(CustomMinecartItem::registerDispenseBehaviour)
            .register();

    public static final ItemEntry<? extends Item> PEACE_BANNER_PATTERN = REGISTRATE
            .item("peace_banner_pattern", it -> new BannerPatternItem(NirvanaTags.PEACE_BANNER_PATTERN, it))
            .properties(it -> it.stacksTo(1))
            .properties(it -> it.rarity(Rarity.UNCOMMON))
            .setData(ProviderType.LANG, (context, provider) -> {
                provider.add(context.get(), "Banner Pattern");
                provider.addTooltip(context, "Peace Sign");
            })
            .recipe(Services.DATAGEN::peaceBannerPattern)
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register();

    public static final ItemEntry<? extends Item> HEMP_CLOTH = REGISTRATE
            .item("hemp_cloth", Item::new)
            .recipe((c, p) -> p.square(DataIngredient.items(HEMP.get()), RecipeCategory.MISC, c, true))
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register();

    public static final ItemEntry<? extends Item> DEERSTALKER = REGISTRATE
            .item("deerstalker", Services.PLATFORM::createDeerstalkerItem)
            .properties(it -> it.durability(ArmorItem.Type.HELMET.getDurability(5)))
            .recipe(Services.DATAGEN::deerStalker)
            .model(Services.DATAGEN::flatItem)
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register();

    public static final ItemEntry<? extends Item> REEFER_HEAD = REGISTRATE
            .item("reefer_head", it -> new StandingAndWallBlockItem(NirvanaBlocks.REEFER_HEAD.get(), NirvanaBlocks.REEFER_WALL_HEAD.get(), it, Direction.DOWN))
            .properties(it -> it.rarity(Rarity.UNCOMMON))
            .model((c, p) -> p.withExistingParent(c.getName(), "item/template_skull"))
            .tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .tag(NirvanaTags.HEADS)
            .register();

    public static void register() {
        // loads this class
    }

}
