package galena.nirvana.world.item;

import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class FilledPipeItem extends Item {

    static final SmokingDispenserBehaviour DISPENSER_BEHAVIOUR = (source, pos, look, stack) -> {
        source.getLevel().sendParticles(NirvanaParticles.SMOKE_RING.get(), pos.x, pos.y, pos.z, 0, look.x, look.y, look.z, 0.1);
    };

    public FilledPipeItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOUR);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return SmokingItem.startUsing(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        var effect = new MobEffectInstance(NirvanaEffects.PEACE.get(), 160 * Services.CONFIG.common().suspiciousPipeFactor(), 0);
        SmokingItem.applyEffect(effect, stack, user, user);

        NirvanaParticles.spawnRing(level, user);

        return user instanceof Player player
                ? SmokingItem.takeHit(player, stack)
                : stack;
    }

}
