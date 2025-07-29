package galena.nirvana.world.block;

import galena.nirvana.index.NirvanaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModdedSkullBlock extends SkullBlock {

    public ModdedSkullBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return NirvanaBlocks.MODDED_SKULL.create(pos, state);
    }

}
