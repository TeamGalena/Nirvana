package galena.nirvana.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import galena.nirvana.client.PeaceShader;
import galena.nirvana.world.effects.PeaceEffect;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Unique
    @Nullable
    private PostChain nirvana$shader = null;

    @Unique
    private boolean nirvana$shaderFailed = false;

    @Unique
    private boolean nirvana$shouldRender() {
        var accessor = (GameRendererAccessor) this;
        if (accessor.getPostEffect() != null) return false;
        return PeaceEffect.shouldRenderShader(accessor.getMinecraft().player);
    }

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V")
    )
    private void renderPeaceShader(DeltaTracker delta, boolean bl, CallbackInfo ci) {
        if(nirvana$shaderFailed) return;

        var accessor = (GameRendererAccessor) this;
        var minecraft = accessor.getMinecraft();

        if (nirvana$shouldRender()) {
            if (nirvana$shader == null) {
                nirvana$shader = PeaceShader.load(minecraft);
                if (nirvana$shader == null) {
                    nirvana$shaderFailed = true;
                    return;
                }
                nirvana$shader.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
            }

            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            nirvana$shader.process(delta.getGameTimeDeltaTicks());
        } else if (nirvana$shader != null) {
            nirvana$shader.close();
            nirvana$shader = null;
        }
    }

    @Inject(
            method = "resize",
            at = @At("HEAD")
    )
    private void resizeShader(int width, int height, CallbackInfo ci) {
        if (nirvana$shader != null) {
            nirvana$shader.resize(width, height);
        }
    }

}
