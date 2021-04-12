package wintersteve25.toxicflora.common.config;

import net.minecraftforge.common.config.Config;
import wintersteve25.toxicflora.ToxicFlora;

@Config(modid = ToxicFlora.MODID, category = "infuser")
public class InfuserConfig {
    @Config.Comment({"This config is used for changing the default minimum amount of vitality for crafts for infuser"})
    @Config.RequiresMcRestart
    public static int defaultMinVitality = 200;
}
