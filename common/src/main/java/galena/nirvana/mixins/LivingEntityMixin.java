package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.world.item.ArmorLike;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(cancellable = true, at = @At("HEAD"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z")
    public void canAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        @SuppressWarnings("DataFlowIssue")
        var self = (LivingEntity) (Object) (this);

        if (NirvanaEffects.arePeaceful(self, target)) {
            cir.setReturnValue(false);
        }
    }

    @WrapOperation(
            method = "doHurtEquipment(Lnet/minecraft/world/damagesource/DamageSource;F[Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;max(FF)F"
            )
    )
    private float hurtDeerStalker(float a, float b, Operation<Float> original) {
        var amount = Math.max(a, b);
        var self = (LivingEntity) (Object) (this);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            var stack = self.getItemBySlot(slot);
            if (stack.getItem() instanceof ArmorLike) {
                stack.hurtAndBreak((int) amount, self, slot);
            }
        }

        return amount;
    }

}
