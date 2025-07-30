package galena.nirvana.index;

import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class NirvanaPaintings {

    private static final MultikultiRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<PaintingVariant, PaintingVariant> THIS_IS_NOT_A_HORN = REGISTRATE
            .painting("this_is_not_a_horn")
            .pixelSized(48, 32)
            // .placeable() TODO
            .lang("This is not a horn", "Yapettoshen")
            .register();

    public static void register() {
        // loads this class
    }

}
