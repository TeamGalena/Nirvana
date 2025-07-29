package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import galena.nirvana.client.PeaceShader;
import galena.nirvana.world.effects.PeaceEffect;
import galena.nirvana.world.entity.Reefer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @WrapOperation(
            method = "checkEntityPostEffect(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/renderer/GameRenderer;loadEffect(Lnet/minecraft/resources/ResourceLocation;)V")
    )
    private void applyReeferShader(GameRenderer instance, ResourceLocation id, Operation<Void> original, @Local Entity entity) {
        if (entity == null) {
            PeaceEffect.checkShader();
        } else {
            original.call(instance, id);
        }
    }

}
