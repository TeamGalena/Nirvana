package galena.nirvana.config;

public interface NirvanaCommonConfig {

    double jointRadius();

    double bongRadius();

    default int getBongHits() {
        return 4;
    };

    default int getJointHits() {
        return 3;
    };

    int nauseaAfterHits();

    int reeferAfterHits();

    double reeferChance();

    int jointPeaceSeconds();

    int bongPeaceSeconds();

    int browniesPeaceSeconds();

    int herbalSalveFactor();

    int suspiciousPipeFactor();

    default int getPipeHits() {
        return 6;
    };
}
