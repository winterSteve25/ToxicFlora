package wintersteve25.toxicflora.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfuser extends RecipeBaseSimple{
    private Fluid output;
    private Fluid input;
    private ItemStack itemInput;
    private int processTime;
    private int minVitality;

    public final List<RecipeInfuser> recipesInfuser = new ArrayList<RecipeInfuser>();

    public RecipeInfuser(ItemStack itemInput, Fluid input, Fluid output, int processTime, int minVitality) {
        this.itemInput = itemInput;
        this.input = input;
        this.output = output;
        this.processTime = processTime;
        this.minVitality = minVitality;
    }

    @Override
    public ItemStack getItemInput() {
        return itemInput;
    }

    @Override
    public Fluid getFluidOutput() {
        return output;
    }

    @Override
    public Fluid getFluidInput() {
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
