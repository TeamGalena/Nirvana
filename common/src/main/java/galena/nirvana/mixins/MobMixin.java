package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaItems;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public abstract class MobMixin {

    @WrapWithCondition(
            method = "serverAiStep()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tickRunningGoals(Z)V",
                    ordinal = 0
            )
    )
    public boolean interruptTargetGoal(GoalSelector targetSelector, boolean argument) {
        @SuppressWarnings("DataFlowIssue")
        var self = (Mob) (Object) (this);
        return !self.hasEffect(NirvanaEffects.PEACE);
    }

    @WrapWithCondition(
            method = "serverAiStep()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tick()V",
                    ordinal = 0
            )
    )
    public boolean interruptTargetGoal(GoalSelector targetSelector) {
        @SuppressWarnings("DataFlowIssue")
        var self = (Mob) (Object) (this);
        return !self.hasEffect(NirvanaEffects.PEACE);
    }

    @ModifyExpressionValue(
            method = "checkAndHandleImportantInteractions(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
                    ordinal = 0
            )
    )
    public boolean checkAndHandleImportantInteractions(boolean original, @Local ItemStack stack) {
        return original || stack.is(NirvanaItems.HERBAL_SALVE.get());
    }

}
