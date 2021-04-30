package wintersteve25.toxicflora.common.block.machines.infuser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.client.SoundTF;
import wintersteve25.toxicflora.common.block.machines.BaseItemInventoryTileTF;
import wintersteve25.toxicflora.common.crafting.RecipeInfuser;

import javax.annotation.Nullable;

public class TileInfuser extends BaseItemInventoryTileTF implements ITickable, IAnimatable {

    public static boolean isCrafting = false;
    public static int totalTicks = 0;
    public static int remainingTicks = 0;
    private static FluidStack inputFluidContent = null;
    private static FluidStack outputFluidContent = null;
    private static final int capacity = Fluid.BUCKET_VOLUME*4;
    public static final FluidTank inputTank = new FluidTank(capacity);
    public static final FluidTank outputTank = new FluidTank(capacity);

    private final AnimationFactory manager = new AnimationFactory(this);

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
                            outputFluidContent = RecipeInfuser.getFluidOutput(inputTank, itemStack).copy();
                            outputTank.fill(outputFluidContent, true);
                            inputFluidContent = null;
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
        if (!world.isRemote) {
            this.isCrafting = true;
            markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
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
        inputTank.readFromNBT(compound);
        inputFluidContent = FluidStack.loadFluidStackFromNBT(compound);

        outputTank.readFromNBT(compound);
        outputFluidContent = FluidStack.loadFluidStackFromNBT(compound);

        remainingTicks = compound.getInteger("progress");
        isCrafting = compound.getBoolean("isCrafting");
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

    public boolean hasItem() {
        ItemStack itemStack = new ItemStack(itemHandler.serializeNBT());
        if (itemStack == null) { return false; } else { return true; }
    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController controller = event.getController();
        controller.transitionLengthTicks = 0;

        if (isCrafting) {
            controller.setAnimation(new AnimationBuilder().addAnimation("infuser.start_craft", false).addAnimation("infuser.crafting", false).addAnimation("infuser.stop_craft", false));
        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("infuser.idle", true));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0, this::predicate);

        controller.registerSoundListener(this::soundListener);
        data.addAnimationController(controller);
    }

    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.playSound(SoundTF.INFUSER_MUSIC, 1, 1);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.manager;
    }
}