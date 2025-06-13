package galena.nirvana.world.item;

import static galena.nirvana.world.item.FilledPipeItem.DISPENSER_BEHAVIOUR;

import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.platform.Services;
import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class SuspiciousPipeItem extends SuspiciousStewItem {

    public SuspiciousPipeItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOUR);
    }

    private static List<MobEffectInstance> getEffects(ItemStack stack) {
        return SuspiciousItem.getEffects(stack, 160 * Services.CONFIG.common().suspiciousPipeFactor());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (!getEffects(stack).isEmpty()) return UseAnim.SPYGLASS;
        return super.getUseAnimation(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (getEffects(stack).isEmpty()) return InteractionResultHolder.pass(stack);
        return SmokingItem.startUsing(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        getEffects(stack).forEach(effect ->
                SmokingItem.applyEffect(effect, stack, user, user)
        );

        NirvanaParticles.spawnRing(level, user);

        return user instanceof Player player
                ? SmokingItem.takeHit(player, stack)
                : stack;
    }

}
