package wintersteve25.toxicflora.client.particles.purple;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcherPurple {
    @SubscribeEvent
    public void stitcherEventPre(TextureStitchEvent.Pre event) {
        ResourceLocation purpleparticles_rl = new ResourceLocation("toxicflora:entity/purple_particles");
        event.getMap().registerSprite(purpleparticles_rl);
    }
}
