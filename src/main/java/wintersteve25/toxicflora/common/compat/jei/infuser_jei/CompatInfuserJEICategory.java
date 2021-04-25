package wintersteve25.toxicflora.common.compat.jei.infuser_jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.compat.jei.ToxicFloraJEIPlugin;

import javax.annotation.Nonnull;
import java.util.List;

import static wintersteve25.toxicflora.common.compat.jei.ToxicFloraJEIPlugin.InfuserIDJEICompatID;

public class CompatInfuserJEICategory implements IRecipeCategory<CompatInfuserJEIWrapper> {

    @Nonnull
    private final IDrawable background = ToxicFloraJEIPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ToxicFlora.MODID + "gui/infuser_jei.png"), 7, 7, 116, 57);

    @Override
    public String getUid() {
        return InfuserIDJEICompatID;
    }

    @Override
    public String getTitle() {
        return "Herbological Infuser";
    }

    @Override
    public String getModName() {
        return ToxicFlora.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CompatInfuserJEIWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();

        guiItemStackGroup.init(0, true, 7, 7);
        guiFluidStackGroup.init(1, true, 58, 7, 14, 14, Fluid.BUCKET_VOLUME*4, true, null);
        guiFluidStackGroup.init(2, false, 103, 35, 20, 20, Fluid.BUCKET_VOLUME*4, true, null);

        List<ItemStack> inputItem = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<FluidStack> input = ingredients.getInputs(VanillaTypes.FLUID).get(1);
        List<FluidStack> output = ingredients.getOutputs(VanillaTypes.FLUID).get(2);

        guiItemStackGroup.set(0, inputItem);
        guiFluidStackGroup.set(1, input);
        guiFluidStackGroup.set(2, output);
    }
}
