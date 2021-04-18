package wintersteve25.toxicflora.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public abstract class RecipeBaseSimple {
    public abstract ItemStack getItemInput();
    public abstract Fluid getFluidOutput();
    public abstract Fluid getFluidInput();
    public abstract int getProcessTime();
    public abstract int getMinVitality();
}
