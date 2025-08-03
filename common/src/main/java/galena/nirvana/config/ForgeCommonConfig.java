package galena.nirvana.config;

import galena.nirvana.NirvanaConstants;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ForgeCommonConfig implements NirvanaCommonConfig {

    private final DoubleValue jointRadius;
    private final IntValue nauseaAfterHits;
    private final IntValue hungerAfterHits;
    private final IntValue reeferAfterHits;
    private final DoubleValue reeferChance;
    private final IntValue jointPeaceSeconds;
    private final DoubleValue bongRadius;
    private final IntValue bongPeaceSeconds;
    private final IntValue herbalSalveFactor;
    private final IntValue suspiciousPipeFactor;
    private final BooleanValue allowFakePlayerSmoking;
    private final BooleanValue generateBongTranslations;

    public ForgeCommonConfig(ModConfigSpec.Builder builder) {
        builder.push(NirvanaConstants.MOD_ID);

        builder.push("smoking");
        this.nauseaAfterHits = builder
                .comment("number of uses after which smoking causes nausea")
                .defineInRange("nauseaAfterHits", 3, -1, 256);
        this.hungerAfterHits = builder
                .comment("number of uses after which smoking causes hunger")
                .defineInRange("hungerAfterHits", 5, -1, 256);
        this.reeferAfterHits = builder
                .comment("number of uses after which smoking causes reefers to spawn")
                .defineInRange("reeferAfterHits", 3, -1, 256);
        this.reeferChance = builder
                .comment("chance of a reefer spawning")
                .defineInRange("reeferSpawnChance", 0.5F, 0F, 1F);

        builder.comment("Joint");
        builder.push("joint");
        this.jointRadius = builder
                .comment("radius around the user within entities will also get the peace effect")
                .defineInRange("radius", 15.0, 0.0, 32.0);
        this.jointPeaceSeconds = builder
                .comment("number of seconds the peace effect from a joint lasts")
                .defineInRange("peaceSeconds", 20, 1, 60 * 60);
        builder.pop();

        builder.comment("Bong");
        builder.push("bong");
        this.bongRadius = builder
                .comment("radius around the user within entities will also get the effect from a bong")
                .defineInRange("radius", 15.0, 0.0, 32.0);
        this.bongPeaceSeconds = builder
                .comment("number of seconds the peace effect from a normal bong lasts")
                .defineInRange("peaceSeconds", 30, 1, 60 * 60);
        this.generateBongTranslations = builder
                .comment(
                        "(ADVANCED) when on, translations for potion bongs are dynamically generated, allowing support for modded potions.",
                        "If disabled, falls back to the default translations as provided in the language file (for example en_us.json)."
                ).define("generateTranslations", true);
        builder.pop();

        this.allowFakePlayerSmoking = builder
                .comment("whether fake players like dispensers or create's deployers are allowed to smoke joint & co.")
                .define("allowFakePlayers", true);

        builder.pop();

        this.herbalSalveFactor = builder
                .comment("how much longer herbal salve effects last in comparison to the suspicious soup equivalent (3 => 3x times)")
                .defineInRange("herbal_salve.factor", 3, 1, 10);

        this.suspiciousPipeFactor = builder
                .comment("how much longer suspicious pipe effects last in comparison to the suspicious soup equivalent (4 => 4x times)")
                .defineInRange("suspicious_pipe.factor", 4, 1, 10);

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
    public int hungerAfterHits() {
        return hungerAfterHits.get();
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

    @Override
    public boolean generateBongTranslations() {
        return generateBongTranslations.get();
    }
}
