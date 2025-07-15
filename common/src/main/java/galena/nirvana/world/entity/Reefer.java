package galena.nirvana.world.entity;

import galena.nirvana.index.NirvanaItems;
import galena.nirvana.world.THCCloud;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class Reefer extends Creeper implements ICustomCreeper {

    public Reefer(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean customExplode(double x, double y, double z, float radius) {
        THCCloud.spawnCloud(level(), position(), 1F, 30, 30);
        return true;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean bl) {
        var cause = source.getEntity();
        if (cause != this && cause instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                spawnAtLocation(NirvanaItems.REEFER_HEAD.get());
            }
        }
    }

}
