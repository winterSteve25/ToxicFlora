package wintersteve25.toxicflora.common.compat.jei.infuser_jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class CompatInfuserJEIWrapper implements IRecipeWrapper {

    private final List<ItemStack> itemInput;
    private final FluidStack input;
    private final FluidStack output;
    private final int processTime;
    private final int minVitality;

    public CompatInfuserJEIWrapper(RecipeInfuser recipe) {
        this.itemInput = Collections.singletonList(((ItemStack) recipe.getItemInput()).copy());
        this.input = recipe.getFluidInput().copy();
        this.output = recipe.getFluidOutput().copy();
        this.processTime = recipe.getProcessTime();
        this.minVitality = recipe.getMinVitality();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!itemInput.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput);
        }
        if (input.getFluid() != null) {
            ingredients.setInput(FluidStack.class, input);
        }
        if (output.getFluid() != null) {
            ingredients.setOutput(FluidStack.class, output);
        }
    }

    public FluidStack getInput() {
        return input;
    }

    public FluidStack getOutput() {
        return output;
    }

    public List<ItemStack> getItemInput() {
        return itemInput;
    }
}
