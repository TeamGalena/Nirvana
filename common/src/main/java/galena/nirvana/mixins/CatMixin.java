package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import galena.nirvana.NirvanaConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Locale;

@Mixin(Cat.class)
public class CatMixin {

    @Unique
    private static final ResourceLocation SPRIGATITO_TEXTURE = NirvanaConstants.createId("textures/entity/cat/sprigatito.png");

    @Unique
    private boolean nirvana$isSprigatito() {
        var self = (Cat) (Object) (this);
        var customName = self.getCustomName();
        if (customName == null) return false;
        var name = ChatFormatting.stripFormatting(customName.getString()).toLowerCase(Locale.ROOT);
        return name.equals("sprigatito");
    }

    @ModifyReturnValue(
            method = "getTextureId()Lnet/minecraft/resources/ResourceLocation;",
            at = @At("RETURN")
    )
    private ResourceLocation overwriteTexture(ResourceLocation original) {
        if (nirvana$isSprigatito()) return SPRIGATITO_TEXTURE;
        return original;
    }

}
