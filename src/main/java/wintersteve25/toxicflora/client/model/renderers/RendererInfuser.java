package wintersteve25.toxicflora.client.model.renderers;

import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import wintersteve25.toxicflora.client.model.gecko_models.ModelInfuser;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;

public class RendererInfuser extends GeoBlockRenderer<TileInfuser> {

    public RendererInfuser() {
        super(new ModelInfuser());
    }
}
