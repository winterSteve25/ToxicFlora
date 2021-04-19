package wintersteve25.toxicflora.common.crafting;

import net.minecraftforge.fluids.FluidStack;

public abstract class RecipeBaseSimple {
    public abstract Object getItemInput();
    public abstract FluidStack getFluidOutput();
    public abstract FluidStack getFluidInput();
    public abstract int getProcessTime();
    public abstract int getMinVitality();
}
