package galena.nirvana.config;

import galena.nirvana.NirvanaConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeCommonConfig implements NirvanaCommonConfig {

    private final ForgeConfigSpec.DoubleValue jointRadius;
    private final ForgeConfigSpec.IntValue nauseaAfterHits;
    private final ForgeConfigSpec.IntValue reeferAfterHits;
    private final ForgeConfigSpec.DoubleValue reeferChance;
    private final ForgeConfigSpec.IntValue jointPeaceSeconds;
    private final ForgeConfigSpec.DoubleValue bongRadius;
    private final ForgeConfigSpec.IntValue bongPeaceSeconds;
    private final ForgeConfigSpec.IntValue browniesPeaceSeconds;
    private final ForgeConfigSpec.IntValue herbalSalveFactor;
    private final ForgeConfigSpec.IntValue suspiciousPipeFactor;
    private final ForgeConfigSpec.BooleanValue allowFakePlayerSmoking;

    public ForgeCommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push(NirvanaConstants.MOD_ID);

        builder.push("smoking");
        this.nauseaAfterHits = builder.defineInRange("nauseaAfterHits", 3, -1, 256);
        this.reeferAfterHits = builder.defineInRange("reeferAfterHits", 3, -1, 256);
        this.reeferChance = builder.defineInRange("reeferSpawnChance", 0.5F, 0F, 1F);

        builder.comment("Joint");
        builder.push("joint");
        this.jointRadius = builder.defineInRange("radius", 15.0, 0.0, 32.0);
        this.jointPeaceSeconds = builder.defineInRange("peaceSeconds", 20, 1, 60 * 60);
        builder.pop();

        builder.comment("Bong");
        builder.push("bong");
        this.bongRadius = builder.defineInRange("radius", 15.0, 0.0, 32.0);
        this.bongPeaceSeconds = builder.defineInRange("peaceSeconds", 30, 1, 60 * 60);
        builder.pop();

        this.allowFakePlayerSmoking = builder.define("allowFakePlayers",true);

        builder.pop();

        this.browniesPeaceSeconds = builder.defineInRange("brownies.peaceSeconds", 40, 1, 60 * 60);

        this.herbalSalveFactor = builder.defineInRange("herbal_salve.factor", 3, 1, 10);

        this.suspiciousPipeFactor = builder.defineInRange("suspicious_pipe.factor", 4, 1, 10);

        builder.pop();
    }

    @Override
    public double jointRadius() {
        return jointRadius.get();
    }

    @Override
    public double bongRadius() {
        return bongRadius.get();
    }

    @Override
    public int nauseaAfterHits() {
        return nauseaAfterHits.get();
    }

    @Override
    public int reeferAfterHits() {
        return reeferAfterHits.get();
    }

    @Override
    public double reeferChance() {
        return reeferChance.get();
    }

    @Override
    public int jointPeaceSeconds() {
        return jointPeaceSeconds.get();
    }

    @Override
    public int bongPeaceSeconds() {
        return bongPeaceSeconds.get();
    }

    @Override
    public int browniesPeaceSeconds() {
        return browniesPeaceSeconds.get();
    }

    @Override
    public int herbalSalveFactor() {
        return herbalSalveFactor.get();
    }

    @Override
    public int suspiciousPipeFactor() {
        return suspiciousPipeFactor.get();
    }

    @Override
    public boolean allowFakePlayerSmoking() {
        return allowFakePlayerSmoking.get();
    }

}
