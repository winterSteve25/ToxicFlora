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

    public boolean isRecipeMatch(IFluidTank tankInput, Object itemInput) {
        if (tankInput.getFluid() == null || !tankInput.getFluid().isFluidEqual(getFluidInput())) {
            return false;
        }
        if (tankInput.getFluid().amount < getFluidInput().amount) {
            return false;
        }
        if (itemInput instanceof ItemStack) {
            ItemStack itemStack = ((ItemStack) itemInput);
            return areStacksTheSame((ItemStack) (getItemInput()), itemStack, false);
        } else if (itemInput instanceof String) {
            List<ItemStack> list = OreDictionary.getOres((String) itemInput);
            for (ItemStack stack : list) {
                return areStacksTheSame((ItemStack) (getItemInput()), stack, false);
            }
        }
        return false;
    }

    //This method is copied from primal tech WoodenBasinRecipe.java, coded by vadis365, full credits to them. Download PrimalTech here:https://www.curseforge.com/minecraft/mc-mods/primal-tech
    public static boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
        if (stack1.isEmpty() && !stack2.isEmpty() || !stack1.isEmpty() && stack2.isEmpty())
            return false;

        if (stack1.getItem() == stack2.getItem())
            if (stack1.getItemDamage() == stack2.getItemDamage() || isWildcard(stack1.getItemDamage()) || isWildcard(stack2.getItemDamage()))
                if (!matchSize || stack1.getCount() == stack2.getCount()) {
                    if (stack1.hasTagCompound() && stack2.hasTagCompound())
                        return stack1.getTagCompound().equals(stack2.getTagCompound());
                    return stack1.hasTagCompound() == stack2.hasTagCompound();
                }
        return false;
    }

    public static RecipeInfuser addRecipe(Object inputItem, FluidStack inputFluid, FluidStack outputFluid, int processTime, int minVitality) {
        RecipeInfuser recipe = new RecipeInfuser(inputItem, inputFluid, outputFluid, processTime, minVitality);
        recipesInfuser.add(recipe);
        return recipe;
    }

    public static RecipeInfuser addRecipe(Object inputItem, FluidStack inputFluid, FluidStack outputFluid, int processTime) {
        RecipeInfuser recipe = new RecipeInfuser(inputItem, inputFluid, outputFluid, processTime);
        recipesInfuser.add(new RecipeInfuser(inputItem, inputFluid, outputFluid, processTime));
        return recipe;
    }

    public static RecipeInfuser addRecipe(Object inputItem, FluidStack inputFluid, FluidStack outputFluid) {
        RecipeInfuser recipe = new RecipeInfuser(inputItem, inputFluid, outputFluid);
        recipesInfuser.add(new RecipeInfuser(inputItem, inputFluid, outputFluid));
        return recipe;
    }

    public static FluidStack getFluidOutput(IFluidTank tank, Object item) {
        RecipeInfuser recipeInfuser = getRecipe(tank, item);
        FluidStack fluidStack = recipeInfuser != null ? recipeInfuser.getFluidOutput() : null;
        return fluidStack;
    }

    public static RecipeInfuser getRecipe(IFluidTank tank, Object item) {
        for (RecipeInfuser recipes : recipesInfuser) {
            if(recipes.isRecipeMatch(tank, item)) {
                return recipes;
            }
        }
        return null;
    }

    //this method is also from primal tech
    private static boolean isWildcard(int meta) {
        return meta == OreDictionary.WILDCARD_VALUE;
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
