package wintersteve25.toxicflora.common.config;

import net.minecraftforge.common.config.Config;
import wintersteve25.toxicflora.ToxicFlora;

@Config(modid = ToxicFlora.MODID, category = "infuser")
public class InfuserConfig {
    @Config.Comment({"This value will be used as default minimum vitality required for crafts that did not specify the minimum vitality amount"})
    @Config.RequiresMcRestart
    public static int defaultMinVitality = 200;

    @Config.Comment({"This value will be used as default processing speed(ticks) required for crafts that did not specify the processing speed"})
    @Config.RequiresMcRestart
    public static int defaultProcessSpeed = 200;
}
