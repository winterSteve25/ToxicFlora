package wintersteve25.toxicflora.common.item.tools;

import net.minecraft.item.Item;
import wintersteve25.toxicflora.ToxicFlora;

public class BaseToxicTools extends Item {
    public BaseToxicTools(String registryName) {
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(ToxicFlora.toxicFloraTools);
        setMaxStackSize(1);
    }

    public BaseToxicTools(String registryName, int maxDamage) {
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(ToxicFlora.toxicFloraTools);
        setMaxDamage(maxDamage);
        setMaxStackSize(1);
    }

    public BaseToxicTools(String registryName, int maxDamage, int maxStackSize) {
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(ToxicFlora.toxicFloraTools);
        setMaxDamage(maxDamage);
        setMaxStackSize(maxStackSize);
    }
}
