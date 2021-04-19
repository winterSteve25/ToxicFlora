package wintersteve25.toxicflora.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.oredict.OreDictionary;
import wintersteve25.toxicflora.common.config.InfuserConfig;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfuser extends RecipeBaseSimple{
    private FluidStack output;
    private FluidStack input;
    private Object itemInput;
    private int processTime;
    private int minVitality;

    public static final List<RecipeInfuser> recipesInfuser = new ArrayList<RecipeInfuser>();

    public RecipeInfuser(Object itemInput, FluidStack input, FluidStack output, int processTime, int minVitality) {
        this.itemInput = itemInput;
        this.input = input;
        this.output = output;
        this.processTime = processTime;
        this.minVitality = minVitality;

        if (itemInput == null) {
            throw new IllegalArgumentException("Item input can not be null!");
        }
        if (itemInput instanceof ItemStack) {
            this.itemInput = ((ItemStack) itemInput).copy();
        } else if (itemInput instanceof String) {
            this.itemInput = OreDictionary.getOres((String) itemInput);
        } else {
            throw new IllegalArgumentException("Invalid item input, must be an ore dictionary or itemstack");
        }
    }

    public RecipeInfuser(Object itemInput, FluidStack input, FluidStack output, int processTime) {
        this.itemInput = itemInput;
        this.input = input;
        this.output = output;
        this.processTime = processTime;
        this.minVitality = InfuserConfig.defaultMinVitality;

        if (itemInput == null) {
            throw new IllegalArgumentException("Item input can not be null!");
        }
        if (itemInput instanceof ItemStack) {
            this.itemInput = ((ItemStack) itemInput).copy();
        } else if (itemInput instanceof String) {
            this.itemInput = OreDictionary.getOres((String) itemInput);
        } else {
            throw new IllegalArgumentException("Invalid item input, must be an ore dictionary or itemstack");
        }
    }

    public RecipeInfuser(Object itemInput, FluidStack input, FluidStack output) {
        this.itemInput = itemInput;
        this.input = input;
        this.output = output;
        this.processTime = InfuserConfig.defaultProcessSpeed;
        this.minVitality = InfuserConfig.defaultMinVitality;

        if (itemInput == null) {
            throw new IllegalArgumentException("Item input can not be null!");
        }
        if (itemInput instanceof ItemStack) {
            this.itemInput = ((ItemStack) itemInput).copy();
        } else if (itemInput instanceof String) {
            this.itemInput = OreDictionary.getOres((String) itemInput);
        } else {
            throw new IllegalArgumentException("Invalid item input, must be an ore dictionary or itemstack");
        }
    }

    /*
        This is having issue i think
     */
    public boolean isRecipeMatch(IFluidTank tankInput, ItemStack itemInput) {
        if (tankInput.getFluid() == null || !tankInput.getFluid().isFluidEqual(getFluidInput())) {
            return false;
        } else if (tankInput.getFluid().amount < getFluidInput().amount) {
            return false;
        } else if (itemInput.isEmpty()) {
            return false;
        } else if (itemInput.getCount() < ((ItemStack) this.itemInput).getCount()) {
            return false;
        } else if (itemInput == this.itemInput && tankInput.getFluid() == this.input) {
            return true;
        }
        return false;
    }

    public static RecipeInfuser addRecipe(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid, int processTime, int minVitality) {
        RecipeInfuser recipe = new RecipeInfuser(inputItem, inputFluid, outputFluid, processTime, minVitality);
        recipesInfuser.add(recipe);
        return recipe;
    }

    public static void addRecipe(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid, int processTime) {
        recipesInfuser.add(new RecipeInfuser(inputItem, inputFluid, outputFluid, processTime));
    }

    public static void addRecipe(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid) {
        recipesInfuser.add(new RecipeInfuser(inputItem, inputFluid, outputFluid));
    }

    public static FluidStack getFluidOutput(IFluidTank tank, ItemStack item) {
        RecipeInfuser recipeInfuser = getRecipe(tank, item);
        FluidStack fluidStack = recipeInfuser != null ? recipeInfuser.getFluidOutput() : null;
        return fluidStack;
    }

    public static RecipeInfuser getRecipe(IFluidTank tank, ItemStack item) {
        for (RecipeInfuser recipes : recipesInfuser) {
            if(recipes.isRecipeMatch(tank, item)) {
                return recipes;
            }
        }
        return null;
    }

    @Override
    public Object getItemInput() {
        return itemInput;
    }

    @Override
    public FluidStack getFluidOutput() {
        return output;
    }

    @Override
    public FluidStack getFluidInput() {
        return input;
    }

    @Override
    public int getProcessTime() {
        return processTime;
    }

    @Override
    public int getMinVitality() {
        return minVitality;
    }
}
