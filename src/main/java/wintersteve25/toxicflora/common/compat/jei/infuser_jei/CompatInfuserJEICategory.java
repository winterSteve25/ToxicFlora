package wintersteve25.toxicflora.common.compat.jei.infuser_jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import wintersteve25.toxicflora.ToxicFlora;

import java.util.List;

import static wintersteve25.toxicflora.common.compat.jei.ToxicFloraJEIPlugin.InfuserIDJEICompatID;

public class CompatInfuserJEICategory implements IRecipeCategory<CompatInfuserJEIWrapper> {

    private final IDrawable background;
    private final IDrawable tankOverlay;

    public CompatInfuserJEICategory(IGuiHelper iGuiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation( "toxicflora:textures/gui/infuser_jei.png");
        background = iGuiHelper.createDrawable(backgroundLocation, 0, 0, 142, 71);

        ResourceLocation tankLocation = new ResourceLocation("toxicflora:textures/gui/fluid_bar_four.png");
        tankOverlay = iGuiHelper.createDrawable(tankLocation, 0, 0, 18, 64, 18, 64);
    }

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

        guiItemStackGroup.init(0, true, 52, 4);
        guiItemStackGroup.set(0, recipeWrapper.getItemInput());

        guiFluidStackGroup.init(0, true, 4, 5, 18, 63, Fluid.BUCKET_VOLUME*4, true, null);
        guiFluidStackGroup.set(0, recipeWrapper.getInput());

        guiFluidStackGroup.init(1, false, 121, 5, 19, 63, Fluid.BUCKET_VOLUME*4, true, null);
        guiFluidStackGroup.set(1, recipeWrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        tankOverlay.draw(minecraft, 3, 4);
        tankOverlay.draw(minecraft, 120, 4);
    }
}
