package wintersteve25.toxicflora.client.proxy;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.client.model.ModelDynJar;
import wintersteve25.toxicflora.client.particles.purple.TextureStitcherPurple;
import wintersteve25.toxicflora.common.block.BlocksTF;
import wintersteve25.toxicflora.common.item.tools.ItemFluidJar;
import wintersteve25.toxicflora.common.proxy.CommonProxy;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ToxicFlora.getLogger().info("Registering Particles");
        MinecraftForge.EVENT_BUS.register(new TextureStitcherPurple());
        ModelLoaderRegistry.registerLoader(ModelDynJar.LoaderDynJar.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @SubscribeEvent
    public static void modelRegistration(ModelRegistryEvent event) {
        ToxicFlora.getLogger().info("Registering Models");
        BlocksTF.initModel();
        ItemFluidJar.initModel();
    }
}