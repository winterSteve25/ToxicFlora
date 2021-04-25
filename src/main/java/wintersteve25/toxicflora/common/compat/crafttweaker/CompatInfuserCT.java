package wintersteve25.toxicflora.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;

@ZenRegister
@ZenClass("mods.toxicflora.HerbInfuser")
public class CompatInfuserCT {
    @ZenMethod
    public static void addRecipe(IIngredient itemInput, FluidStack fluidInput, FluidStack fluidOutput) {
        for (IItemStack itemStack : itemInput.getItems()) {
            RecipeInfuser.addRecipe(itemStack, fluidInput, fluidOutput);
        }
    }

    @ZenMethod
    public static void addRecipe(IIngredient itemInput, FluidStack fluidInput, FluidStack fluidOutput, int processTime) {
        for (IItemStack itemStack : itemInput.getItems()) {
            RecipeInfuser.addRecipe(itemStack, fluidInput, fluidOutput, processTime);
        }
    }

    @ZenMethod
    public static void addRecipe(IIngredient itemInput, FluidStack fluidInput, FluidStack fluidOutput, int processTime, int minVitality) {
        for (IItemStack itemStack : itemInput.getItems()) {
            RecipeInfuser.addRecipe(itemStack, fluidInput, fluidOutput, processTime, minVitality);
        }
    }
}
