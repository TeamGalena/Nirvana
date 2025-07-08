package galena.nirvana.fabric.services;

import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.fabric.FabricEntrypoint;
import galena.nirvana.fabric.world.item.FabricSpawnEggItem;
import galena.nirvana.platform.registrate.EntityPropertiesBuilder;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import galena.nirvana.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public NirvanaRegistrate<?> getRegistrate() {
        return FabricEntrypoint.REGISTRATE;
    }

    private static <E extends Entity> NonNullConsumer<FabricEntityTypeBuilder<E>> mapFactory(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return builder -> {
            factory.accept(new EntityPropertiesBuilder() {
                @Override
                public EntityPropertiesBuilder sized(float width, float height) {
                    builder.dimensions(EntityDimensions.fixed(width, height));
                    return this;
                }

                @Override
                public EntityPropertiesBuilder clientTrackingRange(int range) {
                    builder.trackRangeChunks(range);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder updateInterval(int interval) {
                    builder.trackedUpdateRate(interval);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder fireImmune() {
                    builder.fireImmune();
                    return this;
                }
            });
        };
    }

    @Override
    public <E extends Entity, P> NonNullFunction<EntityBuilder<E, P>, EntityBuilder<E, P>> entityProperties(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return entry -> entry.properties(mapFactory(factory));
    }

    @Override
    public Ingredient createNBTIngredient(ItemStack stack) {
        var base = Ingredient.of(stack);
        var components = stack.getComponentsPatch();
        return new ComponentsIngredient(base, components).toVanilla();
    }

    @Override
    public boolean isFakePlayer(LivingEntity entity) {
        return entity instanceof FakePlayer;
    }

    @Override
    public boolean createLoaded() {
        return FabricLoader.getInstance().isModLoaded("create");
    }

    @Override
    public Item createSpawnEggItem(NonNullSupplier<? extends EntityType<? extends Mob>> type, int primary, int secodary, Item.Properties properties) {
        return new FabricSpawnEggItem(type, primary, secodary, properties);
    }
}
