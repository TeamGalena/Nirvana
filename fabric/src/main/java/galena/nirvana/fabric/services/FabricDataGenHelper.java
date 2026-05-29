package galena.nirvana.fabric.services;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.providers.loot.RegistrateEntityLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import galena.nirvana.platform.services.IDataGenHelper;
import galena.nirvana.world.item.DeerStalkerItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import org.jetbrains.annotations.Nullable;

public class FabricDataGenHelper implements IDataGenHelper {

    private void NOOP() {
        throw new IllegalStateException("DataGen should only happen in neoforge");
    }

    @Override
    public void hempCrop(DataGenContext<Block, ? extends CropBlock> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void hempCrop(RegistrateBlockLootTables provider, CropBlock block) {
        NOOP();
    }

    @Override
    public void crate(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void flatItem(DataGenContext<Item, ? extends Item> context, RegistrateItemModelProvider provider) {
        NOOP();
    }

    @Override
    public void blissBloom(DataGenContext<Block, ? extends DoublePlantBlock> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void blissBloom(RegistrateBlockLootTables provider, DoublePlantBlock block) {
        NOOP();
    }

    @Override
    public void wildHemp(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void wildHemp(RegistrateBlockLootTables provider, Block block) {
        NOOP();
    }

    @Override
    public void pipe(DataGenContext<Item, ? extends Item> context, RegistrateItemModelProvider provider) {
        NOOP();
    }

    @Override
    public void stuffedPipe(DataGenContext<Item, ? extends Item> context, RegistrateRecipeProvider provider) {
        NOOP();
    }

    @Override
    public void tnt(RegistrateBlockLootTables provider, Block block) {
        NOOP();
    }

    @Override
    public void tnt(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void thcMinecart(DataGenContext<Item, ? extends ItemLike> context, RegistrateRecipeProvider provider) {
        NOOP();
    }

    @Override
    public void thc(DataGenContext<Item, ? extends ItemLike> context, RegistrateRecipeProvider provider) {
        NOOP();
    }

    @Override
    public void reefer(RegistrateEntityLootTables provider, EntityType<?> type) {
        NOOP();
    }

    @Override
    public void peaceBannerPattern(DataGenContext<Item, ? extends Item> context, RegistrateRecipeProvider provider) {
        NOOP();
    }

    @Override
    public <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> hempBurlap(@Nullable DyeColor color) {
        return (c, p) -> NOOP();
    }

    @Override
    public void hempBurlap(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> wovenHempBurlap(@Nullable DyeColor color) {
        return (c, p) -> NOOP();
    }

    @Override
    public void deerStalker(DataGenContext<Item, DeerStalkerItem> context, RegistrateRecipeProvider provider) {
        NOOP();
    }

    @Override
    public void pottedPlant(DataGenContext<Block, ? extends FlowerPotBlock> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void pottedPlant(RegistrateBlockLootTables provider, Block block) {
        NOOP();
    }

    @Override
    public void skull(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

    @Override
    public void wovenHempBurlap(DataGenContext<Block, ? extends RotatedPillarBlock> context, RegistrateBlockstateProvider provider) {
        NOOP();
    }

}
