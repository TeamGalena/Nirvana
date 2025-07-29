package galena.nirvana.world.effects;

import galena.nirvana.client.PeaceShader;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaEntities;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class PeaceEffect extends MobEffect implements IStackingEffect {
    public PeaceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xaabf4b);
    }

    private static final int REEFER_MIN_RANGE = 8;
    private static final int REEFER_MAX_RANGE = 20;
    private static final int REEFER_CONVERSION_RANGE = 20;
    private static final int REEFER_CONVERSION_RANGE_SQR = REEFER_CONVERSION_RANGE * REEFER_CONVERSION_RANGE;

    private static boolean fulfills(int hitsTaken, int hitsRequired) {
        return hitsRequired >= 0 && hitsTaken >= hitsRequired;
    }

    @Override
    public void onIncreasedTo(MobEffectInstance instance, ItemStack source, LivingEntity target, Level level) {
        if (!source.is(NirvanaTags.NAUSEATING)) return;
        var hitsTaken = instance.getAmplifier() + 1;

        if (fulfills(hitsTaken, Services.CONFIG.common().nauseaAfterHits())) {
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 0));
        }

        if (fulfills(hitsTaken, Services.CONFIG.common().reeferAfterHits())) {
            spawnReefers(target, level);
            transformCreepers(target.position(), level);
        }
    }

    private static void transformCreepers(Vec3 around, Level level) {
        var box = new AABB(BlockPos.containing(around)).inflate(REEFER_CONVERSION_RANGE + 1);
        var targets = level.getEntitiesOfClass(Creeper.class, box, it ->
                it.getType().is(NirvanaTags.CREEPER_LIKE) && it.distanceToSqr(around) <= REEFER_CONVERSION_RANGE_SQR
        );

        targets.forEach(it -> {
            var replacement = NirvanaEntities.REEFER.create(level);
            if (replacement == null) return;

            replacement.setPos(it.position());
            replacement.setYRot(it.getYRot());
            replacement.setXRot(it.getXRot());

            it.remove(Entity.RemovalReason.DISCARDED);
            if (it.isRemoved()) level.addFreshEntity(replacement);
        });
    }

    private static void spawnReefers(LivingEntity target, Level level) {
        var chance = Services.CONFIG.common().reeferChance();
        if (chance <= 0) return;

        var rolls = level.getDifficulty().getId();
        for (int i = 0; i < rolls; i++) {
            if (target.getRandom().nextFloat() < chance) {
                var reefer = NirvanaEntities.REEFER.create(level);
                if (reefer == null) continue;

                var rangeAround = (REEFER_MAX_RANGE - REEFER_MIN_RANGE) / 2;
                var centerRange = REEFER_MIN_RANGE + rangeAround;
                var vec = new Vec3(
                        (target.getRandom().nextDouble() * 2 - 1),
                        (target.getRandom().nextDouble() * 2 - 1),
                        (target.getRandom().nextDouble() * 2 - 1)
                ).normalize().scale(centerRange);
                var reference = target.position().add(vec);

                level.findSupportingBlock(reefer, new AABB(reference, reference).inflate(rangeAround))
                        .map(it -> Vec3.upFromBottomCenterOf(it, 1))
                        .ifPresent(pos -> {
                            reefer.setPos(pos);
                            level.addFreshEntity(reefer);
                        });
            }
        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int i) {
        super.addAttributeModifiers(entity, attributes, i);
        if (entity instanceof Mob mob) {
            mob.setTarget(null);
        }
    }

    private static boolean shaderEnabledByEffect = false;

    public static void checkShader() {
        var minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        var renderer = minecraft.gameRenderer;
        var enabled = Optional.ofNullable(minecraft.player.getEffect(NirvanaEffects.PEACE.get()))
                .filter(it -> it.getAmplifier() > 1)
                .isPresent();
        var current = Optional.ofNullable(renderer.currentEffect())
                .map(PostChain::getName)
                .map(ResourceLocation::new)
                .filter(it -> it.equals(PeaceShader.ID))
                .isPresent();
        if (current == enabled) return;
        if (enabled) {
            PeaceShader.enable(renderer);
            shaderEnabledByEffect = true;
        } else if (shaderEnabledByEffect) {
            PeaceShader.disable(renderer);
            shaderEnabledByEffect = false;
        }
    }

}
