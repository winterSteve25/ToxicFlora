package wintersteve25.toxicflora.common.compat.jei.infuser_jei;

import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import wintersteve25.toxicflora.common.block.BlocksTF;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;
import wintersteve25.toxicflora.common.crafting.recipes.ModInfuserRecipe;

import javax.annotation.Nonnull;

import static wintersteve25.toxicflora.common.compat.jei.ToxicFloraJEIPlugin.InfuserIDJEICompatID;

public class CompatInfuserJEIHandler {
    public static void infuserHandling(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(BlocksTF.blockInfuser), InfuserIDJEICompatID);
        registry.addRecipes(ModInfuserRecipe.getRecipeList(), InfuserIDJEICompatID);
        registry.handleRecipes(RecipeInfuser.class, CompatInfuserJEIWrapper::new, InfuserIDJEICompatID);
    }
}