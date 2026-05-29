package galena.nirvana.forge.data;

import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CompatRegistrate {

    public static class Mods {
        public static final String FARMERS_DELIGHT = "farmersdelight";
        public static final String SUPPLEMENTARIES = "supplementaries";
    }

    static ItemLike SUPPLEMENTARIES_ROPE = ModRegistry.ROPE.get();
    static ItemLike FARMERS_DELIGHT_ROPE = ModItems.ROPE.get();

}
