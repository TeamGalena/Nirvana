package galena.nirvana.world.item;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CustomMinecartItem extends MinecartItem {

    private final NonNullSupplier<EntityType<? extends AbstractMinecart>> entity;

    private final DispenseItemBehavior dispenseBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

        public ItemStack execute(BlockSource source, ItemStack stack) {
            var direction = source.state().getValue(DispenserBlock.FACING);
            var level = source.level();

            var center = source.center();
            var x = center.x() + (double) direction.getStepX() * 1.125;
            var y = Math.floor(center.y()) + (double) direction.getStepY();
            var z = center.z() + (double) direction.getStepZ() * 1.125;

            var pos = source.pos().relative(direction);
            var state = level.getBlockState(pos);
            var railShape = state.getBlock() instanceof BaseRailBlock ? state.getValue(((BaseRailBlock) state.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;

            double yOffset;
            if (state.is(BlockTags.RAILS)) {
                yOffset = railShape.isAscending() ? 0.6 : 0.1;
            } else {
                if (!state.isAir() || !level.getBlockState(pos.below()).is(BlockTags.RAILS)) {
                    return defaultBehaviour.dispense(source, stack);
                }

                var belowState = level.getBlockState(pos.below());
                var belowRailShape = belowState.getBlock() instanceof BaseRailBlock ? belowState.getValue(((BaseRailBlock) belowState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;

                yOffset = direction != Direction.DOWN && belowRailShape.isAscending() ? -0.4 : -0.9;
            }

            var minecart = place(level, x, y + yOffset, z);
            if (minecart == null) return stack;

            EntityType.createDefaultStackConfig(level, stack, null).accept(minecart);
            level.addFreshEntity(minecart);
            stack.shrink(1);
            return stack;
        }
    };

    public void registerDispenseBehaviour() {
        DispenserBlock.registerBehavior(asItem(), dispenseBehavior);
    }

    public CustomMinecartItem(Properties properties, Supplier<? extends EntityType<? extends AbstractMinecart>> entity) {
        super(AbstractMinecart.Type.TNT, properties);
        this.entity = entity::get;
    }

    private AbstractMinecart place(Level level, double x, double y, double z) {
        var minecart = entity.get().create(level);
        if (minecart == null) return null;

        minecart.setPos(x, y, z);
        minecart.xo = x;
        minecart.yo = y;
        minecart.zo = z;
        return minecart;
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        if (!state.is(BlockTags.RAILS)) {
            return InteractionResult.FAIL;
        } else {
            ItemStack stack = context.getItemInHand();
            if (level instanceof ServerLevel serverLevel) {
                var railshape = state.getBlock() instanceof BaseRailBlock rail ? state.getValue(rail.getShapeProperty()) : RailShape.NORTH_SOUTH;
                var yOffset = railshape.isAscending() ? 0.5F : 0F;

                var minecart = place(level, pos.getX() + 0.5F, pos.getY() + 0.0625F + yOffset, pos.getZ() + 0.5F);
                if (minecart == null) return InteractionResult.FAIL;

                EntityType.createDefaultStackConfig(serverLevel, stack, context.getPlayer()).accept(minecart);
                level.addFreshEntity(minecart);
                level.gameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Context.of(context.getPlayer(), level.getBlockState(pos.below())));
            }

            stack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

}
