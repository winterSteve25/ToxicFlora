package wintersteve25.toxicflora.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.toxicflora.common.block.machines.infuser.TileInfuser;

import javax.annotation.Nullable;

public abstract class BaseItemInventoryTileTF extends TileEntity{

    protected ItemStackHandler itemHandler = createHandler();

    public abstract boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand, boolean isCrafting);

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public abstract int getInvSize();

    protected ItemStackHandler createHandler() {
        return new ItemStackHandlerTF(this);
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

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    protected static class ItemStackHandlerTF extends ItemStackHandler {
        private final BaseItemInventoryTileTF tile;

        public ItemStackHandlerTF(BaseItemInventoryTileTF inv) {
            super(inv.getInvSize());
            tile = inv;
        }

        @Override
        public void onContentsChanged(int slot) {
            IBlockState state = tile.world.getBlockState(tile.pos);
            tile.world.notifyBlockUpdate(tile.pos, state, state, 3);
            tile.markDirty();
        }
    }
}
