package galena.nirvana.fabric.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import galena.nirvana.world.block.ICustomTntBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TntBlock.class)
public class TntBlockMixin {

    @Unique
    private boolean onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if(this instanceof ICustomTntBlock tnt) {
            tnt.onCaughtFire(state, level, pos, face, igniter);
            return false;
        }

        return true;
    }

    @WrapWithCondition(
            method = "onPlace",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TntBlock;explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
            )
    )
    public boolean onPlace(Level level, BlockPos pos, @Local(ordinal = 0, argsOnly = true) BlockState state) {
        return onCaughtFire(state, level, pos, null, null);
    }

    @WrapWithCondition(
            method = "neighborChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TntBlock;explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
            )
    )
    public boolean neighborChanged(Level level, BlockPos pos, @Local(ordinal = 0, argsOnly = true) BlockState state) {
        return onCaughtFire(state, level, pos, null, null);
    }

    @WrapWithCondition(
            method = "playerWillDestroy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TntBlock;explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
            )
    )
    public boolean playerWillDestroy(Level level, BlockPos pos, @Local(ordinal = 0, argsOnly = true) BlockState state, @Local(argsOnly = true) Player player) {
        return onCaughtFire(state, level, pos, null, player);
    }

    @WrapWithCondition(
        method = "useItemOn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/TntBlock;explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    public boolean use(Level level, BlockPos pos, LivingEntity igniter, @Local(ordinal = 0, argsOnly = true) BlockState state, @Local(argsOnly = true) BlockHitResult hit) {
        return onCaughtFire(state, level, pos, hit.getDirection(), igniter);
    }

    @WrapWithCondition(
        method = "onProjectileHit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/TntBlock;explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    public boolean onProjectileHit(Level level, BlockPos pos, LivingEntity igniter, @Local(ordinal = 0, argsOnly = true) BlockState state, @Local(argsOnly = true) BlockHitResult hit) {
        return onCaughtFire(state, level, pos, hit.getDirection(), igniter);
    }

}
