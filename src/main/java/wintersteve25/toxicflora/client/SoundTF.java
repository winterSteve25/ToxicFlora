package wintersteve25.toxicflora.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wintersteve25.toxicflora.ToxicFlora;

public class SoundTF {
    public static SoundEvent INFUSER_MUSIC;

    @SubscribeEvent
    public void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        ResourceLocation location = new ResourceLocation(ToxicFlora.MODID, "infuser_music");
        INFUSER_MUSIC = new SoundEvent(location).setRegistryName(location);
        event.getRegistry().register(INFUSER_MUSIC);
    }
}
