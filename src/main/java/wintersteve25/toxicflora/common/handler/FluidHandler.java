package wintersteve25.toxicflora.common.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class FluidHandler {

    public static boolean isFluidHandlerItem(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

}
