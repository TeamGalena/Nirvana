package galena.nirvana.world.item;

import galena.nirvana.platform.Services;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public interface SmokingDispenserBehaviour extends DispenseItemBehavior {

    DefaultDispenseItemBehavior DEFAULT = new DefaultDispenseItemBehavior();

    @Override
    default ItemStack dispense(BlockSource source, ItemStack stack) {
        if(!Services.CONFIG.common().allowFakePlayerSmoking()) return DEFAULT.dispense(source, stack);

        var pos = source.center();
        var facing = source.state().getValue(DispenserBlock.FACING);
        var look = new Vec3(facing.step());

        takeHit(source, pos, look, stack);

        return SmokingItem.takeHit(source.level(), pos, SoundSource.BLOCKS, false, stack);
    }

    void takeHit(BlockSource source, Vec3 pos, Vec3 look, ItemStack stack);

}
