package galena.nirvana.index;

import com.tterrag.registrate.providers.ProviderType;
import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.platform.Services;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class NirvanaSounds {

    private static final MultikultiRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<SoundEvent, SoundEvent> JAM_SOUND = REGISTRATE
            .sound("music.disc.jam")
            .with("discs/jam")
            .register();

    public static final ResourceKey<JukeboxSong> JAM_KEY = ResourceKey.create(Registries.JUKEBOX_SONG, NirvanaConstants.createId("jam"));

    private static final String JAM_DESCRIPTION_ID = Util.makeDescriptionId("jukebox_song", JAM_KEY.location());

    public static final RegistryEntry<JukeboxSong, JukeboxSong> JAM = REGISTRATE
            .generic(JAM_KEY.location().getPath(), Registries.JUKEBOX_SONG, () -> new JukeboxSong(
                    BuiltInRegistries.SOUND_EVENT.wrapAsHolder(JAM_SOUND.get()),
                    Component.translatable(JAM_DESCRIPTION_ID),
                    150, 13)
            )
            .setData(ProviderType.LANG, (context, provider) -> {
                provider.add(JAM_DESCRIPTION_ID, "Jam - firch");
            })
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> BONG = REGISTRATE
            .sound("item.use.bong")
            .with("item/bong_1")
            .with("item/bong_2")
            .with("item/bong_3")
            .lang("Bong ripped")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> SMOKING = REGISTRATE
            .sound("item.use.smoking")
            .with("item/smoking_1")
            .with("item/smoking_2")
            .with("item/smoking_3")
            .lang("Smoking")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> HERBAL_SALVE = REGISTRATE
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
