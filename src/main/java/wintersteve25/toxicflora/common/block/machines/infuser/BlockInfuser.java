package wintersteve25.toxicflora.common.block.machines.infuser;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import wintersteve25.toxicflora.ToxicFlora;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.client.model.renderers.RendererInfuser;
import wintersteve25.toxicflora.common.handler.InventoryHandler;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockInfuser extends BlockDirectional implements ITileEntityProvider {

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

        setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfuser();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(hand);

            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileInfuser) {
                TileInfuser teinfuser = (TileInfuser) te;
                if (!itemstack.isEmpty() && !FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing)) {
                    boolean result = teinfuser.addItem(playerIn, itemstack, hand);
                    return result;
                } else if (itemstack.isEmpty() && teinfuser.hasItem() && playerIn.isSneaking()) {
                    InventoryHandler.withdrawFromInventory(teinfuser, playerIn);
                    return true;
                }
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileInfuser.class, new RendererInfuser());
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            if(worldIn.isBlockPowered(pos)) {
                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile instanceof TileInfuser) {
                    TileInfuser tileInfuser = ((TileInfuser) tile);
                    tileInfuser.tryStartCraft();
                }
            }
        }
    }
}
