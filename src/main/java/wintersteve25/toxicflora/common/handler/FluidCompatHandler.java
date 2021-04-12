package wintersteve25.toxicflora.common.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nullable;

public class FluidCompatHandler extends FluidHandlerItemStack implements ICapabilityProvider {
    public FluidCompatHandler(ItemStack container) {
        super(container, 250);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return super.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return super.drain(resource, doDrain);
    }

    @Override
    @Nullable
    public FluidStack getFluid() {
        NBTTagCompound tagCompound = container.getTagCompound();
        if (tagCompound == null) {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(tagCompound);
    }

    @Override
    protected void setFluid(FluidStack fluid) {
        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        fluid.writeToNBT(container.getTagCompound());
    }

    @Override
    protected void setContainerToEmpty() {
        container.getTagCompound().removeTag("FluidName");
        container.getTagCompound().removeTag("Amount");
    }
}
