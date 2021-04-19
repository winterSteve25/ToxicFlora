package wintersteve25.toxicflora.common.crafting.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;
import wintersteve25.toxicflora.common.item.ItemsTF;

public class ModInfuserRecipe {
    public static RecipeInfuser recipeTest;

    public static void init() {
        recipeTest = RecipeInfuser.addRecipe(new ItemStack(ItemsTF.ToxicIngot, 1), new FluidStack(FluidRegistry.WATER, 250), new FluidStack(FluidRegistry.LAVA, 250), 0, 0);
    }
}
