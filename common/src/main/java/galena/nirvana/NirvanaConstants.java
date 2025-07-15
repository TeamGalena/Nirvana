package galena.nirvana;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NirvanaConstants {

    public static final String MOD_ID = "nirvana";
    public static final String MOD_NAME = "Nirvana";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static ResourceLocation createId(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
