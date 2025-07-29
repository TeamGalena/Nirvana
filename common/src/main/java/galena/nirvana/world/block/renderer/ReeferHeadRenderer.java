package galena.nirvana.world.block.renderer;

import galena.nirvana.NirvanaConstants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

public class ReeferHeadRenderer {

    public static final ResourceLocation TEXTURE = NirvanaConstants.createId("textures/entity/reefer.png");
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(NirvanaConstants.createId("reefer_head"), "main");

    public static LayerDefinition createLayers() {
        var mesh = new MeshDefinition();
        var deformation = CubeDeformation.NONE;

        mesh.getRoot().addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-10.0F, -16.0F, -4.5F, 20.0F, 23.0F, 0.0F, deformation)
                        .texOffs(24, 62).addBox(-10.0F, -5.0F, -4.0F, 20.0F, 2.0F, 0.0F, deformation)
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
                PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 64, 64);
    }
}
