package wintersteve25.toxicflora.common.compat.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;

@ZenRegister
@ZenClass("mods.toxicflora.HerbInfuser")
public class CompatInfuserCT {
    @ZenMethod
    public static void addRecipe(IItemStack itemInput, ILiquidStack fluidInput, ILiquidStack fluidOutput) {
        RecipeInfuser.addRecipe(InputHelper.toStack(itemInput), InputHelper.toFluid(fluidInput), InputHelper.toFluid(fluidOutput));
    }

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, ILiquidStack fluidInput, ILiquidStack fluidOutput, int processTime) {
        RecipeInfuser.addRecipe(InputHelper.toStack(itemInput), InputHelper.toFluid(fluidInput), InputHelper.toFluid(fluidOutput), processTime);
    }

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, ILiquidStack fluidInput, ILiquidStack fluidOutput, int processTime, int minVitality) {
        RecipeInfuser.addRecipe(InputHelper.toStack(itemInput), InputHelper.toFluid(fluidInput), InputHelper.toFluid(fluidOutput), processTime, minVitality);
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack fluid, IItemStack item) {
        RecipeInfuser.removeRecipe(InputHelper.toFluid(fluid), InputHelper.toStack(item));
    }
}
