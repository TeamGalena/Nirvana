package galena.nirvana.platform;

import galena.nirvana.platform.services.IBrewingRegistry;
import galena.nirvana.platform.services.IConfigs;
import galena.nirvana.platform.services.IDataGenHelper;
import galena.nirvana.platform.services.IPlatformHelper;
import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IDataGenHelper DATAGEN = load(IDataGenHelper.class);
    public static final IBrewingRegistry BREWING = load(IBrewingRegistry.class);
    public static final IConfigs CONFIG = load(IConfigs.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }

}
