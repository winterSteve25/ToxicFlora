package wintersteve25.toxicflora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.block.blocks.BaseToxicBlocks;
import wintersteve25.toxicflora.common.block.flowers.BlockShadeShroom;
import wintersteve25.toxicflora.common.block.machines.infuser.BlockInfuser;
import wintersteve25.toxicflora.common.handler.ICustomModelHandler;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class BlocksTF {
    @GameRegistry.ObjectHolder("toxicflora:shadeshroom")
    public static BlockShadeShroom blockShadeShroom;
    @GameRegistry.ObjectHolder("toxicflora:infuser")
    public static BlockInfuser blockInfuser;
    public static void initModel() {
        blockShadeShroom.initModel();
        blockInfuser.initModel();
    }

    public static Block BlOCKSHADOWBRICK;

    static List<Block> blocksToxicFlora = new ArrayList<>();
    static List<Item> itemBlocksToxicFlora = new ArrayList<>();
    static {
        BlOCKSHADOWBRICK = initBlock(new BaseToxicBlocks(Material.ROCK, "shadow_brick"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent e) {
        ToxicFlora.getLogger().info("Registering Models");
        for (Block b : blocksToxicFlora) {
            initModel(b);
        }
    }
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        for (Block b : blocksToxicFlora){
            event.getRegistry().register(b);
            ToxicFlora.getLogger().info(b + "Registered");
        }
    }
    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        for (Item ib : itemBlocksToxicFlora) {
            event.getRegistry().register(ib);
            ToxicFlora.getLogger().info(ib + "Registered");
        }
    }
    @SideOnly(Side.CLIENT)
    private static void initModel(Block b) {
        if (b instanceof ICustomModelHandler) ((ICustomModelHandler)b).initModel();
        else ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
    }
    private static Block initBlock(Block b) {
        blocksToxicFlora.add(b);
        itemBlocksToxicFlora.add(new ItemBlock(b).setRegistryName(b.getRegistryName()));
        return b;
    }
}
