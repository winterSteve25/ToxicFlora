package wintersteve25.toxicflora.common.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wintersteve25.toxicflora.common.block.BlocksTF;
import wintersteve25.toxicflora.common.compat.jei.infuser_jei.CompatInfuserJEICategory;
import wintersteve25.toxicflora.common.compat.jei.infuser_jei.CompatInfuserJEIHandler;

import javax.annotation.Nonnull;

@JEIPlugin
public class ToxicFloraJEIPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelper;

    public static final String InfuserIDJEICompatID = "toxicflora.infuser";

    @Override
    public void register(IModRegistry registry) {
        CompatInfuserJEIHandler.infuserHandling(registry);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CompatInfuserJEICategory());
    }
}
