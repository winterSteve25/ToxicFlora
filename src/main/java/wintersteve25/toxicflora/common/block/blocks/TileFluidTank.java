package wintersteve25.toxicflora.common.block.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class TileFluidTank extends FluidTank {

    public TileFluidTank(@Nullable FluidStack fluid, int capacity) {
        super(fluid, capacity);
    }

    public TileFluidTank(@Nullable Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return super.canFillFluidType(fluid);
    }

    @Override
    public boolean canDrain() {
        return true;
    }

    @Override
    public void onContentsChanged() {
        if (this.tile != null && !tile.getWorld().isRemote) {
            final IBlockState state = this.tile.getWorld().getBlockState(this.tile.getPos());
            this.tile.getWorld().notifyBlockUpdate(this.tile.getPos(), state, state, 8);
            this.tile.markDirty();
        }
    }
}
