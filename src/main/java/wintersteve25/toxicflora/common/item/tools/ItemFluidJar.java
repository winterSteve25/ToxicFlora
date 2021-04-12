package wintersteve25.toxicflora.common.item.tools;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.FluidContainerColorer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.ToxicFlora;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import wintersteve25.toxicflora.client.model.ModelDynJar;
import wintersteve25.toxicflora.common.handler.FluidCompatHandler;
import wintersteve25.toxicflora.common.item.ItemsTF;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemFluidJar extends ItemFluidContainer {
    //Main Item Constructor
    public final int capacity;
    private final ItemStack empty;

    public ItemFluidJar(int capacity) {
        super(capacity);
        setRegistryName("jar");
        setTranslationKey(ToxicFlora.MODID + ".jar");
        setMaxStackSize(32);
        setCreativeTab(ToxicFlora.toxicFloraTools);
        setHasSubtypes(true);
        this.capacity = capacity;
        this.empty = new ItemStack(ItemsTF.FluidJar);
    }

    @Nullable
    public FluidStack getFluidFromJar(@Nonnull ItemStack container) {
        return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        return this.empty;
    }

    public static ItemStack getFilledJar(Fluid fluid) {
        if (fluid.getName().equals("milk")) {
            return ItemStack.EMPTY;
        }

        ItemStack filledJar = new ItemStack(ItemsTF.FluidJar);
        FluidStack fluidContent = new FluidStack(fluid, 250);

        NBTTagCompound fluidContenttag = new NBTTagCompound();
        fluidContent.writeToNBT(fluidContenttag);
        filledJar.setTagCompound(fluidContenttag);

        return filledJar;
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (this.isInCreativeTab(tab)) {
            subItems.add(new ItemStack(this));

            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (!fluid.getName().equals("milk")) {
                    subItems.add(getFilledJar(fluid));
                }
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidCompatHandler(stack);
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        FluidStack fluidStack = getFluidFromJar(stack);

        if (fluidStack == null) {
            return net.minecraft.util.text.translation.I18n.translateToLocal("item." + ToxicFlora.MODID + ".empty.jar.name");
        }

        String translationKey = this.getTranslationKey(stack);
        return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(translationKey + ".name", fluidStack.getLocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModel() {
        ModelLoader.setCustomMeshDefinition(ItemsTF.FluidJar, stack -> ModelDynJar.LOCATION);
        ModelBakery.registerItemVariants(ItemsTF.FluidJar, ModelDynJar.LOCATION);
    }
}
