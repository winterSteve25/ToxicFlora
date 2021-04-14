package wintersteve25.toxicflora.client.model;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;

public class TileInfuserRenderer extends TileEntitySpecialRenderer<TileInfuser> {
    @Override
    public void render(TileInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!(te instanceof TileInfuser) || !te.hasWorld()) return;

        TileEntity teinfuser = (TileInfuser) te;
    }
}
