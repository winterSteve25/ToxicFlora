package wintersteve25.toxicflora.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.item.ingredients.BaseToxicItems;
import wintersteve25.toxicflora.common.item.tools.BaseToxicTools;
import wintersteve25.toxicflora.common.item.tools.ItemFluidJar;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemsTF {

    public static Item MortarPestal;
    public static Item MixingBowl;
    public static Item DaturaPetal;
    public static Item HyacinthPetal;
    public static Item OleanderPetal;
    public static Item ShadowCap;
    public static Item ToxicIngot;
    public static Item ToxicCompound;

    @GameRegistry.ObjectHolder("toxicflora:jar")
    public static ItemFluidJar FluidJar;

    static List<Item> itemsToxicFlora = new ArrayList<>();

    static {
        MortarPestal = initItem(new BaseToxicTools("mortar_pestle", 180));
        MixingBowl = initItem(new BaseToxicTools("mixing_bowl"));
        DaturaPetal = initItem(new BaseToxicItems("petal_datura"));
        HyacinthPetal = initItem(new BaseToxicItems("petal_hyacinth"));
        OleanderPetal = initItem(new BaseToxicItems("petal_oleander"));
        ShadowCap = initItem(new BaseToxicItems("shadow_cap"));
        ToxicIngot = initItem(new BaseToxicItems("ingot_toxic"));
        ToxicCompound = initItem(new BaseToxicItems("compound_toxic"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent e) {
        ToxicFlora.getLogger().info("Registering Item Models");
        for (Item i : itemsToxicFlora) {
            initModel(i);
        }
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        for (Item i : itemsToxicFlora){
            event.getRegistry().register(i);
            ToxicFlora.getLogger().info(i + "Registered");
        }
    }
    @SideOnly(Side.CLIENT)
    private static void initModel(Item i) {
        ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
    }
    private static Item initItem(Item i) {
        itemsToxicFlora.add(i);
        return i;
    }
}
