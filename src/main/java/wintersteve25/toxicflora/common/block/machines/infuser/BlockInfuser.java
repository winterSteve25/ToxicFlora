package wintersteve25.toxicflora.common.block.machines.infuser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wintersteve25.toxicflora.ToxicFlora;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.common.handler.InventoryHandler;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockInfuser extends Block implements ITileEntityProvider {

    private static final AxisAlignedBB INFUSERHITBOX = new AxisAlignedBB(0, 0, 0, 1, 0.0625 * 13, 1);

    public static final ResourceLocation INFUSER = new ResourceLocation(ToxicFlora.MODID, "infuser");

    public BlockInfuser() {
        super(Material.ROCK);
        setRegistryName(INFUSER);
        setTranslationKey(ToxicFlora.MODID + ".infuser");
        setHarvestLevel("pickaxe", 2);
        setHardness(2.5f);
        setResistance(6f);
        setSoundType(SoundType.METAL);
        setCreativeTab(ToxicFlora.toxicFloraMachines);
    }

    @Override
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return INFUSERHITBOX;
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, INFUSERHITBOX);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfuser();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(hand);

            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileInfuser) {
                TileInfuser teinfuser = (TileInfuser) te;
                if (!itemstack.isEmpty()) {
                    boolean result = teinfuser.addItem(playerIn, itemstack, hand);
                    return result;
                } else if (itemstack.isEmpty() && teinfuser.hasItem() && playerIn.isSneaking()) {
                    InventoryHandler.withdrawFromInventory(teinfuser, playerIn);
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    private IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos){
        TileInfuser teinfuser = (TileInfuser) world.getTileEntity(pos);
        return teinfuser.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileInfuser) {
            TileInfuser teinfuser = (TileInfuser) te;
            for (int i = 0; i < 1; i++) {
                if (teinfuser.getItemHandler().getStackInSlot(i).isEmpty() == false) {
                    InventoryHandler.dropInventory(teinfuser, worldIn, state, pos);
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
