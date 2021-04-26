package wintersteve25.toxicflora;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import software.bernie.geckolib3.GeckoLib;
import wintersteve25.toxicflora.common.block.BlocksTF;
import wintersteve25.toxicflora.common.item.ItemsTF;
import wintersteve25.toxicflora.common.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ToxicFlora.MODID, name = ToxicFlora.NAME, version = ToxicFlora.VERSION, dependencies = "required-after:forge@[14.23.3.2847,);required-after:mtlib@[3.0.5,)")
public class ToxicFlora {
    public static final String MODID = "toxicflora";
    public static final String NAME = "Toxic Flora";
    public static final String VERSION = "1.0.17";

    @SidedProxy(clientSide = "wintersteve25.toxicflora.client.proxy.ClientProxy", serverSide = "wintersteve25.toxicflora.common.proxy.ServerProxy")
    public static CommonProxy proxy;

    //creative tabs
    public static CreativeTabs toxicFloraPlants = new CreativeTabs ("ToxicFloraHerbs") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlocksTF.blockShadeShroom);
        }
    };

    public static CreativeTabs toxicFloraMachines = new CreativeTabs("ToxicFloraMachines") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlocksTF.blockInfuser);
        }
    };

    public static CreativeTabs toxicFloraTools = new CreativeTabs("ToxicFloraTools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemsTF.MortarPestal);
        }
    };

    public static CreativeTabs toxicFloraIngredient = new CreativeTabs("ToxicFloraIngredient") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemsTF.DaturaPetal);
        }
    };

    @Mod.Instance
    public static ToxicFlora instance;

    public static Logger logger;

    public static Logger getLogger() {
        if(logger == null){
            logger = LogManager.getFormatterLogger(MODID);
        }
        return logger;
    }

    //stages
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
        GeckoLib.initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
