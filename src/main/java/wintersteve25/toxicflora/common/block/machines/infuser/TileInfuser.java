package wintersteve25.toxicflora.common.block.machines.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.block.machines.BaseItemInventoryTileTF;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;

import javax.annotation.Nullable;

public class TileInfuser extends BaseItemInventoryTileTF implements ITickable {

    public static boolean isCrafting = false;
    public static int totalTicks = 0;
    public static int remainingTicks = 0;
    public static final int capacity = Fluid.BUCKET_VOLUME*4;
    public FluidTank inputTank = new FluidTank(capacity) {
        @Override
        protected void onContentsChanged() {
            markDirty();
        }
    };
    public FluidTank outputTank = new FluidTank(capacity){
        @Override
        protected void onContentsChanged() {
            markDirty();
        }
    };

    @Override
    public void update() {
        if (!world.isRemote) {
            if (isCrafting) {
                ItemStack itemStack = itemHandler.getStackInSlot(0);
                RecipeInfuser recipe = RecipeInfuser.getRecipe(inputTank.getFluid(), itemStack);
                if (recipe != null) {
                    if (remainingTicks > 0) {
                        remainingTicks--;
                        markDirty();
                        if (itemStack.isEmpty() || inputTank.getFluid() == null) {
                            remainingTicks = 0;
                            isCrafting = false;
                            markDirty();
                        }
                        if (remainingTicks <= 0) {
                            ToxicFlora.getLogger().info("Infuser Craft completed");
                            FluidStack outputFluidContent = RecipeInfuser.getFluidOutput(inputTank, itemStack).copy();
                            outputTank.fill(outputFluidContent, true);
                            inputTank.drain(recipe.getFluidInput(), true);
                            itemHandler.extractItem(0, 1, false);
                            isCrafting = false;
                            remainingTicks = 0;
                            markDirty();

                        }
                    } else {
                        if (outputTank.getFluid() == null) {
                            totalTicks = recipe.getProcessTime();
                            remainingTicks = totalTicks;
                            markDirty();
                        } else if (outputTank.getFluid().containsFluid(recipe.getFluidOutput())) {
                            if (outputTank.getFluidAmount() - recipe.getFluidOutput().amount >= 0) {
                                totalTicks = recipe.getProcessTime();
                                remainingTicks = totalTicks;
                                markDirty();
                            }
                        }
                    }
                }
            }
        }
    }

    public void tryStartCraft() {
        if (isCrafting) {
            return;
        }
        if (!world.isRemote) {
            this.isCrafting = true;
            markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());

        NBTTagCompound inTankNBT = new NBTTagCompound();
        NBTTagCompound outTankNBT = new NBTTagCompound();

        inputTank.writeToNBT(inTankNBT);
        outputTank.writeToNBT(outTankNBT);
        compound.setTag("inTank", inTankNBT);
        compound.setTag("outTank", outTankNBT);

        inputTank.writeToNBT(compound);
        outputTank.writeToNBT(compound);

        compound.setInteger("progress", remainingTicks);
        compound.setBoolean("isCrafting", isCrafting);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }

        inputTank.readFromNBT(compound.getCompoundTag("inTank"));
        outputTank.readFromNBT(compound.getCompoundTag("outTank"));

        inputTank.readFromNBT(compound);
        outputTank.readFromNBT(compound);

        remainingTicks = compound.getInteger("progress");
        isCrafting = compound.getBoolean("isCrafting");
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    public boolean hasItem() {
        ItemStack itemStack = new ItemStack(itemHandler.serializeNBT());
        if (itemStack == null) { return false; } else { return true; }
    }

    @Override
    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand, boolean isCrafting) {
        if (!itemHandler.getStackInSlot(0).isEmpty()) {
            return false;
        }
        if (isCrafting) {
            return false;
        }
        if (itemHandler.getStackInSlot(0).isEmpty()) {
            ItemStack itemAdd = heldItem.copy();
            itemHandler.insertItem(0, itemAdd, false);
            if(player == null || !player.capabilities.isCreativeMode) {
                heldItem.shrink(heldItem.getCount());
            }
            markDirty();
        }
        return true;
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
            if (facing == EnumFacing.DOWN) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputTank);
            } else if (facing == EnumFacing.UP) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
            } else {
                if (outputTank.getFluid() != null) {
                    return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputTank);
                }
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
            }
        }
        return super.getCapability(capability, facing);
    }
}