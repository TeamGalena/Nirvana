package galena.nirvana.mixins;

import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Inventory.class)
public class InventoryMixin {

    /*
    @Inject(
            method = "hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F[I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private void hurtDeerStalker(DamageSource damageSource, float f, int[] is, CallbackInfo ci, @Local(ordinal = 0) ItemStack stack) {
        if (stack.getItem() instanceof ArmorLike equipable) {
            var self = (Inventory) (Object) this;
            stack.hurtAndBreak((int) f, self.player, (player) -> player.broadcastBreakEvent(equipable.getEquipmentSlot()));
        }
    }
    */

}
