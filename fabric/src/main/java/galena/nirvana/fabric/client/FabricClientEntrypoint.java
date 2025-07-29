package galena.nirvana.fabric.client;

import galena.nirvana.NirvanaClient;
import galena.nirvana.client.CustomItemModel;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;

import java.util.function.Function;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomItemModel.register();

        NirvanaClient.registerParticles(new NirvanaParticles.ParticleRegister() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> options, Function<SpriteSet, ParticleProvider<T>> factory) {
                ParticleFactoryRegistry.getInstance().register(options, factory::apply);
            }
        });

        EntityModelLayerRegistry.registerModelLayer(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        EntityModelLayerRegistry.registerModelLayer(ReeferHeadRenderer.LAYER, ReeferHeadRenderer::createLayers);

        ClientTickEvents.START_CLIENT_TICK.register(event -> {
            NirvanaClient.tickShaders();
        });
    }

}
