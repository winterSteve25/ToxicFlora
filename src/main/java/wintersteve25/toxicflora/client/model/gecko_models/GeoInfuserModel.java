package wintersteve25.toxicflora.client.model.gecko_models;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;

public class GeoInfuserModel extends AnimatedGeoModel<TileInfuser> {
    @Override
    public ResourceLocation getAnimationFileLocation(TileInfuser animatable)
    {
        return new ResourceLocation(ToxicFlora.MODID, "animations/machines/infuser.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(TileInfuser animatable)
    {
        return new ResourceLocation(ToxicFlora.MODID, "geo/machines/infuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileInfuser entity)
    {
        return new ResourceLocation(ToxicFlora.MODID, "textures/blocks/machines/infuser_all.png");
    }
}
