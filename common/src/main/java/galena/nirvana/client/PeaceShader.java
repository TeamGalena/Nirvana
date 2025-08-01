package galena.nirvana.client;

import com.google.gson.JsonSyntaxException;
import galena.nirvana.NirvanaConstants;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;

public class PeaceShader {

    public static final ResourceLocation ID = NirvanaConstants.createId("shaders/post/peace.json");

    public static PostChain load(Minecraft minecraft) {
        try {
            return new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), PeaceShader.ID);
        } catch (IOException | JsonSyntaxException ex) {
            NirvanaConstants.LOGGER.error("Unable to load shader '{}'", ID, ex);
            return null;
        }
    }
}
