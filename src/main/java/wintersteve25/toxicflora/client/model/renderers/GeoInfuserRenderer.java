package wintersteve25.toxicflora.client.model.renderers;

import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import wintersteve25.toxicflora.client.model.gecko_models.GeoInfuserModel;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;

public class GeoInfuserRenderer extends GeoBlockRenderer<TileInfuser> {
    public GeoInfuserRenderer() {
        super(new GeoInfuserModel());
    }
}
