package galena.nirvana.world.item;

import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaSounds;
import galena.nirvana.platform.Services;
import galena.nirvana.world.effects.IStackingEffect;
import java.util.stream.Stream;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class SmokingItem extends Item {

    public SmokingItem(Properties properties) {
        super(properties);
    }

    protected void registerDispenserBehaviour() {
        SmokingDispenserBehaviour dispenserBehaviour = (source, pos, look, stack) -> {
            applyEffects(stack, source.level(), source.center(), null);
            var mouth = pos.add(look.scale(0.5));
            source.level().sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    mouth.x(), mouth.y(), mouth.z(),
                    5,
                    0.0, 0.2 + source.level().getRandom().nextDouble() * 0.1, 0.0,
                    0.02
            );
        };
        DispenserBlock.registerBehavior(this, dispenserBehaviour);
    }

    abstract Stream<MobEffectInstance> getEffects(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity);

    abstract double getRadius(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity);

    private void addParticles(ServerLevel level, LivingEntity entity) {
        var rot = entity.getLookAngle().scale(0.6);
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                entity.getX() + rot.x, entity.getEyeY() + rot.y, entity.getZ() + rot.z,
                5,
                0.0, 0.2 + entity.getRandom().nextDouble() * 0.1, 0.0,
                0.02
        );
    }

    public static void applyEffect(MobEffectInstance instance, ItemStack source, LivingEntity target, @Nullable LivingEntity cause) {
        var existing = target.getEffect(instance.getEffect());

        var effect = instance.getEffect();
        if (existing != null && effect.is(NirvanaEffects.STACKING_EFFECTS)) {
            var increased = new MobEffectInstance(
                    instance.getEffect(),
                    instance.getDuration(),
                    existing.getAmplifier() + 1,
                    instance.isAmbient(),
                    instance.isVisible(),
                    instance.showIcon(),
                    null
            );
            target.addEffect(increased);
            if (effect.value() instanceof IStackingEffect stacking) {
                stacking.onIncreasedTo(increased, source, target, target.level());
            }
        } else if (effect.value().isInstantenous()) {
            effect.value().applyInstantenousEffect(cause, cause, target, instance.getAmplifier(), 1.0);
        } else {
            target.addEffect(instance);
        }
    }

    private void applyEffects(ItemStack source, Level level, Vec3 pos, @Nullable LivingEntity user) {
        var range = getRadius(source, level, user) * 2;
        var targets = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(pos, range, range, range));

        targets.forEach(target -> getEffects(source, level, user).forEach(effect ->
                applyEffect(effect, source, target, user)
        ));
    }

    protected @Nullable SoundEvent getUseSound() {
        return NirvanaSounds.SMOKING.get();
    }

    public static ItemStack takeHit(Player player, ItemStack stack) {
        player.getCooldowns().addCooldown(stack.getItem(), 20);
        return takeHit(player.level(), player.position(), SoundSource.PLAYERS, player.getAbilities().instabuild, stack);
    }

    static ItemStack takeHit(Level level, Vec3 pos, SoundSource soundSource, boolean simulate, ItemStack stack) {
        var sound = stack.getItem() instanceof SmokingItem item
                ? item.getUseSound()
                : NirvanaSounds.SMOKING.get();

        if (sound != null) {
            level.playSound(null, pos.x(), pos.y(), pos.z(), sound, soundSource, 1F, 1F);
        }

        if (simulate) return stack;

        var remainder = stack.getItem().getCraftingRemainingItem();
        if (stack.isDamageableItem()) {
            stack.setDamageValue(stack.getDamageValue() + 1);
            if (stack.getDamageValue() == stack.getMaxDamage()) {
                stack.shrink(1);
            }
        } else {
            stack.shrink(1);
        }

        if (stack.isEmpty()) {
            if (remainder != null) {
                return remainder.getDefaultInstance();
            }
        }

        return stack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.gameEvent(GameEvent.DRINK);

        if (level instanceof ServerLevel serverLevel) {
            applyEffects(stack, level, entity.position(), entity);
            addParticles(serverLevel, entity);
        }

        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
        }

        if (entity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            return takeHit(player, stack);
        }

        return stack;
    }

    public static InteractionResultHolder<ItemStack> startUsing(Level level, Player player, InteractionHand hand) {
        if (!canUse(player)) return InteractionResultHolder.pass(player.getItemInHand(hand));
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    public static boolean canUse(LivingEntity entity) {
        return Services.CONFIG.common().allowFakePlayerSmoking() || !Services.PLATFORM.isFakePlayer(entity);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return startUsing(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

}
