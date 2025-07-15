package galena.nirvana.index;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.platform.Services;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class NirvanaPaintings {

    private static final NirvanaRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<PaintingVariant, PaintingVariant> THIS_IS_NOT_A_HORN = REGISTRATE
            .generic("this_is_not_a_horn", Registries.PAINTING_VARIANT, () -> new PaintingVariant(48, 32, NirvanaConstants.createId("this_is_not_a_horn")))
            .setData(ProviderType.LANG, (context, provider) -> {
                provider.add(context.getId().toLanguageKey("painting", "title"), "This is not a horn");
                provider.add(context.getId().toLanguageKey("painting", "author"), "Yapettoshen ");
            })
            .register();

    public static void register() {
        // loads this class
    }

}
