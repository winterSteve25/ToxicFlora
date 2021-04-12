package wintersteve25.toxicflora.common.block.flowers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import wintersteve25.toxicflora.ToxicFlora;
import wintersteve25.toxicflora.common.block.flowers.BaseToxicFlora;

public class BlockShadeShroom extends BaseToxicFlora {

    public static final ResourceLocation SHADESHROOM = new ResourceLocation(ToxicFlora.MODID, "shadeshroom");

    public BlockShadeShroom() {
        setRegistryName(SHADESHROOM);
        setTranslationKey(ToxicFlora.MODID + ".shadeshroom");
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(!worldIn.isRemote && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (entityIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entityIn;
                if (!player.isEntityInvulnerable(DamageSource.MAGIC)) {
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, 0));
                }
            }
        }
    }
}