package galena.nirvana.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class WildHempBlock extends BushBlock {

    private static final MapCodec<WildHempBlock> CODEC = simpleCodec(WildHempBlock::new);

    public WildHempBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

}
