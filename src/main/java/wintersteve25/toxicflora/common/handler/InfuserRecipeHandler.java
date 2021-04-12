package wintersteve25.toxicflora.common.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class InfuserRecipeHandler {
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;
    private final ItemStack input;
    private final ItemStack inputSecondary;
    private final int progress;

    public InfuserRecipeHandler(FluidStack fluidInput, FluidStack fluidOutput, ItemStack input, @Nullable ItemStack inputSecondary, int progress) {
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
        this.input = input;
        this.inputSecondary = inputSecondary;
        this.progress = progress;
    }

    public FluidStack getInput() {
        return fluidInput;
    }

    public FluidStack getOutput() {
        return fluidOutput;
    }

    public ItemStack getInputItem() {
        return input;
    }

    public ItemStack getInputSecondary() {
        return inputSecondary;
    }

    public int getProgress() {
        return progress;
    }
}
