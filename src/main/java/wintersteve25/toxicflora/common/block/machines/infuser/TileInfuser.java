package wintersteve25.toxicflora.common.block.machines.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileInfuser extends TileEntity implements ITickable, IFluidHandler, IFluidTank {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileInfuser.this.markDirty();
        }
    };
    private int progress = 0;
    private boolean isCrafting = false;
    public boolean canCraft = false;
    private int minVitality = 0;
    private FluidStack fluidContent = null;

    private int capacity = Fluid.BUCKET_VOLUME*4;
    public final FluidTank tank = new FluidTank(capacity);
    private IFluidTankProperties[] tankProperties;
    private boolean canFill = true;
    private boolean canDrain = true;

    public TileInfuser() {
    }

    @Override
    public void update() {
        if (!world.isRemote) {
        }
    }

    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand) {
        for (int i = 0; i < 1; i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
            if (isCrafting == true) {
                return false;
            }
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                ItemStack itemAdd = heldItem.copy();
                itemHandler.insertItem(i, itemAdd, false);
                if(player == null || !player.capabilities.isCreativeMode) {
                    heldItem.shrink(heldItem.getCount());
                }
                break;
            }
        }
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        tank.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }
        tank.readFromNBT(compound);
        fluidContent = FluidStack.loadFluidStackFromNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }

        return super.getCapability(capability, facing);
    }

    public boolean hasItem() {
        ItemStack itemStack = new ItemStack(itemHandler.serializeNBT());
        if (itemStack == null) { return false; } else { return true; }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if (this.tankProperties == null)
        {
            this.tankProperties = new IFluidTankProperties[] { new FluidTankPropertiesWrapper(tank) };
        }
        return this.tankProperties;
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return fluidContent;
    }

    @Override
    public int getFluidAmount() {
        if (fluidContent == null)
        {
            return 0;
        }
        return fluidContent.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!canFillFluidType(resource))
        {
            return 0;
        }

        return fillInternal(resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (!canDrainFluidType(getFluid()))
        {
            return null;
        }
        return drainInternal(resource, doDrain);
    }

    @Nullable
    public FluidStack drainInternal(FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(getFluid()))
        {
            return null;
        }
        return drainInternal(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (!canDrainFluidType(fluidContent))
        {
            return null;
        }
        return drainInternal(maxDrain, doDrain);
    }

    @Nullable
    public FluidStack drainInternal(int maxDrain, boolean doDrain)
    {
        TileInfuser tile = new TileInfuser();
        if (fluidContent == null || maxDrain <= 0)
        {
            return null;
        }

        int drained = maxDrain;
        if (fluidContent.amount < drained)
        {
            drained = fluidContent.amount;
        }

        FluidStack stack = new FluidStack(fluidContent, drained);
        if (doDrain)
        {
            fluidContent.amount -= drained;
            if (fluidContent.amount <= 0)
            {
                fluidContent = null;
            }

            onContentsChanged();

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluidContent, tile.getWorld(), tile.getPos(), this, drained));
            }
        }
        return stack;
    }

    public boolean canDrainFluidType(@Nullable FluidStack fluid)
    {
        return fluid != null && canDrain();
    }

    public boolean canDrain() {
        return canDrain;
    }

    public boolean canFillFluidType(FluidStack fluidContent) {
        return canFill();
    }

    public boolean canFill() {
        return canFill;
    }

    public int fillInternal(FluidStack resource, boolean doFill) {
        TileInfuser tile = new TileInfuser();
        if (resource == null || resource.amount <= 0)
        {
            return 0;
        }

        if (!doFill)
        {
            if (fluidContent == null)
            {
                return Math.min(capacity, resource.amount);
            }

            if (!fluidContent.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(capacity - fluidContent.amount, resource.amount);
        }

        if (fluidContent == null)
        {
            fluidContent = new FluidStack(resource, Math.min(capacity, resource.amount));

            onContentsChanged();

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidContent, tile.getWorld(), tile.getPos(), this, fluidContent.amount));
            }
            return fluidContent.amount;
        }

        if (!fluidContent.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluidContent.amount;

        if (resource.amount < filled)
        {
            fluidContent.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            fluidContent.amount = capacity;
        }

        onContentsChanged();

        if (tile != null)
        {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidContent, tile.getWorld(), tile.getPos(), this, filled));
        }
        return filled;
    }

    public void onContentsChanged() {
        markDirty();
    }
}