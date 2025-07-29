package galena.nirvana.forge;

import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;

public class ForgeNirvanaRegistrate extends MultikultiRegistrate<ForgeNirvanaRegistrate> {

    public static ForgeNirvanaRegistrate create(String modid) {
        var registrate =  new ForgeNirvanaRegistrate(modid);
        registrate.registerEventListeners(registrate.getModEventBus());
        return registrate;
    }

    private ForgeNirvanaRegistrate(String modid) {
        super(modid);
    }

}
