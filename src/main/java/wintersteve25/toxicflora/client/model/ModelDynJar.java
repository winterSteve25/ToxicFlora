package wintersteve25.toxicflora.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import wintersteve25.toxicflora.ToxicFlora;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ModelDynJar implements IModel {
    public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(ToxicFlora.MODID, "jar"), "inventory");

    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;

    public static final IModel MODEL = new ModelDynJar();

    private final ResourceLocation emptyJarRL;
    @Nullable
    private final ResourceLocation jarFluidRL;
    @Nullable
    private final Fluid fluidFluid;

    public ModelDynJar() {
        this.emptyJarRL = new ResourceLocation(ToxicFlora.MODID, "jar_empty");
        this.jarFluidRL = null;
        this.fluidFluid = null;
    }

    public ModelDynJar(ResourceLocation jarFluidRL, Fluid fluidFluid) {
        this.emptyJarRL = new ResourceLocation(ToxicFlora.MODID, "jar_empty");
        this.jarFluidRL = jarFluidRL;
        this.fluidFluid = fluidFluid;
    }

    @Override
    public Collection<ResourceLocation> getTextures()
    {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

        if (emptyJarRL != null)
            builder.add(emptyJarRL);
        if (jarFluidRL != null)
            builder.add(jarFluidRL);
        if (fluidFluid != null)
            builder.add(fluidFluid.getStill());

        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

        TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
        TextureAtlasSprite fluidSprite = null;
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        if(fluidFluid != null) {
            fluidSprite = bakedTextureGetter.apply(fluidFluid.getStill());
        }

        if (emptyJarRL != null)
        {
            // build base (insidest)
            IBakedModel model = (new ItemLayerModel(ImmutableList.of(emptyJarRL))).bake(state, format, bakedTextureGetter);
            builder.addAll(model.getQuads(null, null, 0));
        }
        if (jarFluidRL != null && fluidSprite != null)
        {
            ResourceLocation sprite = fluidFluid != null ? fluidFluid.getStill() : emptyJarRL;
            int color = fluidFluid != null ? fluidFluid.getColor() : Color.WHITE.getRGB();
            TextureAtlasSprite liquid = bakedTextureGetter.apply(sprite);
            // build liquid layer (inside)
            builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, NORTH_Z_FLUID, fluidSprite, EnumFacing.NORTH, color, -1));
            builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, SOUTH_Z_FLUID, fluidSprite, EnumFacing.SOUTH, color, -1));
        }

        return new ModelDynJar.BakedJar(this, builder.build(), fluidSprite, format, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity());
    }

    @Override
    public IModel process(ImmutableMap<String, String> customData) {
        String fluidName = customData.get("fluid");
        Fluid fluid = FluidRegistry.getFluid(fluidName);

        if (fluid == null) fluid = this.fluidFluid;

        return new ModelDynJar(jarFluidRL, fluid);
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures) {
        ResourceLocation liquid = jarFluidRL;

        if (textures.containsKey("fluid"))
            liquid = new ResourceLocation(textures.get("fluid"));

        return new ModelDynJar(liquid, fluidFluid);
    }

    public enum LoaderDynJar implements ICustomModelLoader {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            return modelLocation.getNamespace().equals(ToxicFlora.MODID) && modelLocation.getPath().contains("jar");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) {
            return MODEL;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }
    }

    private static final class BakedDynJarOverrideHandler extends ItemOverrideList {
        public static final BakedDynJarOverrideHandler INSTANCE = new BakedDynJarOverrideHandler();

        private BakedDynJarOverrideHandler()
        {
            super(ImmutableList.of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
        {
            FluidStack fluidStack = FluidUtil.getFluidContained(stack);

            if (fluidStack == null) {
                return originalModel;
            }

            BakedJar model = (BakedJar)originalModel;

            String fluidName = fluidStack.getFluid().getName();

            if (!model.cache.containsKey(fluidName)) {
                IModel parent = model.parent.process(ImmutableMap.of("fluid", fluidName));
                Function<ResourceLocation, TextureAtlasSprite> textureGetter;
                textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

                IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
                model.cache.put(fluidName, bakedModel);
                return bakedModel;
            }

            return model.cache.get(fluidName);
        }
    }

    private class BakedJar implements IBakedModel {
        private final ModelDynJar parent;
        private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
        private final VertexFormat format;
        private final ImmutableList<BakedQuad> quads;
        private final TextureAtlasSprite particle;
        private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

        private BakedJar(ModelDynJar parent,
                         ImmutableList<BakedQuad> quads,
                         TextureAtlasSprite particle,
                         VertexFormat format,
                         ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                         Map<String, IBakedModel> cache,
                         boolean untransformed) {
            this.format = format;
            this.quads = quads;
            this.particle = particle;
            this.parent = parent;
            this.cache = cache;
            this.transforms = transforms;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side == null) return quads;
            return ImmutableList.of();
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return particle;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return BakedDynJarOverrideHandler.INSTANCE;
        }
    }
}
