package wintersteve25.toxicflora.common.item.ingredients;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import wintersteve25.toxicflora.ToxicFlora;

public class BaseToxicItems extends Item {
    public BaseToxicItems(String registryName) {
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(ToxicFlora.toxicFloraIngredient);
    }

    public BaseToxicItems(String registryName, CreativeTabs tabs) {
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(tabs);
    }
}
