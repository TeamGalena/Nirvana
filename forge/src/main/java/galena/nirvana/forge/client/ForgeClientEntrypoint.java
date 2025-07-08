package galena.nirvana.forge.client;

import galena.nirvana.NirvanaClient;
import galena.nirvana.client.CustomItemModel;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.function.Function;

public class ForgeClientEntrypoint {

    public static void init(IEventBus modBus) {
        CustomItemModel.register();

        modBus.addListener(ForgeClientEntrypoint::registerParticles);
        modBus.addListener(ForgeClientEntrypoint::registerLayers);
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        NirvanaClient.registerParticles(new NirvanaParticles.ParticleRegister() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> options, Function<SpriteSet, ParticleProvider<T>> factory) {
                event.registerSpriteSet(options, factory::apply);
            }
        });
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        event.registerLayerDefinition(ReeferHeadRenderer.LAYER, SkullModel::createMobHeadLayer);
    }

}
