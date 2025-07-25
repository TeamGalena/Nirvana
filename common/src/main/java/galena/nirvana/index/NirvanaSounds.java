package galena.nirvana.index;

import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import net.minecraft.sounds.SoundEvent;

public class NirvanaSounds {

    private static final MultikultiRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<SoundEvent> JAM = REGISTRATE
            .sound("music.disc.jam")
            .with("discs/jam")
            .register();

    public static final RegistryEntry<SoundEvent> BONG = REGISTRATE
            .sound("item.use.bong")
            .with("item/bong_1")
            .with("item/bong_2")
            .with("item/bong_3")
            .lang("Bong ripped")
            .register();

    public static final RegistryEntry<SoundEvent> SMOKING = REGISTRATE
            .sound("item.use.smoking")
            .with("item/smoking_1")
            .with("item/smoking_2")
            .with("item/smoking_3")
            .lang("Smoking")
            .register();

    public static final RegistryEntry<SoundEvent> HERBAL_SALVE = REGISTRATE
            .sound("item.use.herbal_salve")
            .with("item/herbal_salve_1")
            .with("item/herbal_salve_2")
            .with("item/herbal_salve_3")
            .with("item/herbal_salve_4")
            .lang("Herbal salve applied")
            .register();

    public static void register() {
        // loads this class
    }

}
