package wintersteve25.toxicflora.common.block.flowers;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.client.particles.purple.PurpleFloraParticle;

import java.util.Random;

public class BaseToxicFlora extends BlockBush {
    public BaseToxicFlora() {
        super(Material.PLANTS);
        setCreativeTab(ToxicFlora.toxicFloraPlants);
        setLightLevel(1.8F);
        setSoundType (SoundType.PLANT);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(worldIn.isRemote) {
            double xpos = pos.getX() + 0.2;
            double ypos = pos.getY() + 0.08;
            double zpos = pos.getZ() + 0.2;
            double velocityX = 0.008;
            double velocityY = 0.01;
            double velocityZ = 0.008;

            PurpleFloraParticle particle = new PurpleFloraParticle(worldIn, xpos, ypos, zpos, velocityX, velocityY, velocityZ);
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        }
    }
}
