package galena.nirvana.forge.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import galena.nirvana.index.NirvanaItems;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    @WrapOperation(
            method = "renderArmWithItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/common/extensions/IItemExtension;canPerformAction(Lnet/minecraft/world/item/ItemStack;Lnet/neoforged/neoforge/common/ItemAbility;)Z"
            )
    )
    public boolean isUsingJoint(ItemStack instance, ItemAbility ability, Operation<Boolean> original) {
        return NirvanaItems.JOINT.isIn(instance) || original.call(instance, ability);
    }

}
