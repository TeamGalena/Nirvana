package galena.nirvana.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModdedSkullBlockEntity extends SkullBlockEntity {

    private final BlockEntityType<?> type;

    public ModdedSkullBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(pos, state);
        this.type = type;
    }

    @Override
    public BlockEntityType<?> getType() {
        return type;
    }

}
