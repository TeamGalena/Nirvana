package galena.nirvana.compat;

import galena.nirvana.NirvanaConstants;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.PotionSubtypeInterpreter;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.SuspiciousStewSubtypeInterpreter;
import net.minecraft.resources.ResourceLocation;

public class NirvanaJeiCompat {

    public static final ResourceLocation ID = NirvanaConstants.createId("jei");

    public static ISubtypeInterpreter potionInterpreter() {
        return PotionSubtypeInterpreter.INSTANCE;
    }

    public static ISubtypeInterpreter suspiciousInterpreter() {
        return SuspiciousStewSubtypeInterpreter.INSTANCE;
    }

}
