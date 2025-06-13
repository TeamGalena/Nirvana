package galena.nirvana.config;

public class NirvanaCommonConfig {

    public double jointRadius() {
        return 15.0;
    }

    public double bongRadius() {
        return 4.0;
    }

    public int getBongHits() {
        return 4;
    }

    public int getJointHits() {
        return 3;
    }

    public int nauseaAfterHits() {
        return 3;
    }

    public int reeferAfterHits() {
        return nauseaAfterHits();
    }

    public float reeferChance() {
        return 0.5F;
    }

    public int jointPeaceSeconds() {
        return 20;
    }

    public int bongPeaceSeconds() {
        return 30;
    }

    public int browniesPeaceSeconds() {
        return 40;
    }

    public int herbalSalveFactor() {
        return 3;
    }

    public int suspiciousPipeFactor() {
        return 4;
    }

    public int getPipeHits() {
        return 6;
    }

    public boolean allowFakePlayerSmoking() {
        return true;
    }

}
