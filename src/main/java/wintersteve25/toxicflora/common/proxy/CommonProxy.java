package wintersteve25.toxicflora.common.proxy;

import net.minecraftforge.registries.IForgeRegistry;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.block.BlocksTF;
import wintersteve25.toxicflora.common.block.flowers.BlockShadeShroom;
import wintersteve25.toxicflora.common.block.machines.infuser.BlockInfuser;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;
import wintersteve25.toxicflora.common.item.tools.ItemFluidJar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    private static final String MODID = ToxicFlora.MODID;

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ToxicFlora.getLogger().info("Registering Blocks");
        //blocks
        IForgeRegistry<Block> br = event.getRegistry();
        br.register(new BlockShadeShroom());
        br.register(new BlockInfuser());

        //tile entities
        TileEntity.register(MODID + ":infuser", TileInfuser.class);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ToxicFlora.getLogger().info("Registering Items");
        //item blocks
        IForgeRegistry<Item> ir = event.getRegistry();
        ir.register(new ItemBlock(BlocksTF.blockShadeShroom).setRegistryName(BlockShadeShroom.SHADESHROOM));
        ir.register(new ItemBlock(BlocksTF.blockInfuser).setRegistryName(BlockInfuser.INFUSER));

        ir.register(new ItemFluidJar(250));
    }
}