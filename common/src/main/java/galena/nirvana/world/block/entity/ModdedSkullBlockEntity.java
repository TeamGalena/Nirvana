package galena.nirvana.world.block.entity;

import galena.nirvana.index.NirvanaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModdedSkullBlockEntity extends SkullBlockEntity {

    public ModdedSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return NirvanaBlocks.MODDED_SKULL.get();
    }

}
