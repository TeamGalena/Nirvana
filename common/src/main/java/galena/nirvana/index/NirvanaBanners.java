package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;

public class NirvanaBanners {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<BannerPattern, BannerPattern> PEACE = REGISTRATE
            .generic("peace", Registries.BANNER_PATTERN, () -> new BannerPattern(NirvanaConstants.createId("peace"), "peace"))
            .setData(ProviderType.LANG, (context, provider) -> {
                for (DyeColor dye : DyeColor.values()) {
                    var key = context.getId().toLanguageKey("block.minecraft.banner", dye.getSerializedName());
                    provider.add(key, RegistrateLangProvider.toEnglishName(dye.getSerializedName()) + " Peace Sign");
                }
            })
            .register();

    public static void register() {
        // loads this class
    }

}
