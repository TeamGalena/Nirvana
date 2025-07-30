package galena.nirvana.fabric.datagen;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class CompatRegistrate {

    public static class Mods {
        public static final String FARMERS_DELIGHT = "farmersdelight";
        public static final String SUPPLEMENTARIES = "supplementaries";
    }

    private static final Registrate SUPPLEMENTARIES = Registrate.create("supplementaries");
    private static final Registrate FARMERS_DELIGHT = Registrate.create("farmersdelight");

    static ItemEntry<? extends Item> SUPPLEMENTARIES_ROPE = SUPPLEMENTARIES.item("rope", Item::new).register();
    static ItemEntry<? extends Item> FARMERS_DELIGHT_ROPE = FARMERS_DELIGHT.item("rope", Item::new).register();

    public static void register() {
        SUPPLEMENTARIES.register();
        FARMERS_DELIGHT.register();
    }

}
