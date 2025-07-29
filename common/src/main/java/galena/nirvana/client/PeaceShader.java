package galena.nirvana.client;

import galena.nirvana.NirvanaConstants;
import galena.nirvana.mixins.GameRendererAccessor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class PeaceShader {

    public static final ResourceLocation ID = NirvanaConstants.createId("shaders/post/peace.json");

    public static void enable(GameRenderer renderer) {
        var accessor = (GameRendererAccessor) renderer;
        accessor.invokeLoadEffect(ID);
    }

    public static void disable(GameRenderer renderer) {
        renderer.shutdownEffect();
    }

}
