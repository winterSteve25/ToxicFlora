package wintersteve25.toxicflora.common.block.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import wintersteve25.toxicflora.ToxicFlora;

public class BaseToxicBlocks extends Block {
    public BaseToxicBlocks(Material material, String registryName) {
        super(material);
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(3);
        setHarvestLevel("pickaxe", 1);
    }

    public BaseToxicBlocks(Material material, String registryName, int hardness) {
        super(material);
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(hardness);
        setHarvestLevel("pickaxe", 1);
    }

    public BaseToxicBlocks(Material material, String registryName, int hardness, String toolClass, int toolLevel) {
        super(material);
        setRegistryName(registryName);
        setTranslationKey(ToxicFlora.MODID + "." + registryName);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(hardness);
        setHarvestLevel(toolClass, toolLevel);
    }
}
