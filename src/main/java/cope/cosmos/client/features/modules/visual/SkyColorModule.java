package cope.cosmos.client.features.modules.visual;

import cope.cosmos.client.events.render.world.RenderFogColorEvent;
import cope.cosmos.client.events.render.world.RenderSkyEvent;
import cope.cosmos.client.features.modules.Category;
import cope.cosmos.client.features.modules.Module;
import cope.cosmos.client.features.setting.Setting;
import cope.cosmos.util.string.ColorUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author linustouchtips
 * @since 12/28/2021
 */
public class SkyColorModule extends Module {
    public static SkyColorModule INSTANCE;

    public SkyColorModule() {
        super("SkyColor", Category.VISUAL, "Colors the sky");
        INSTANCE = this;
    }

    // **************************** general ****************************

    public static Setting<Boolean> sky = new Setting<>("Sky", true)
            .setDescription("Colors the sky");

    public static Setting<Boolean> fog = new Setting<>("Fog", true)
            .setDescription("Colors the fog");

    @SubscribeEvent
    public void onRenderSky(RenderSkyEvent event) {
        if (sky.getValue()) {

            // override sky color
            event.setColor(ColorUtil.getPrimaryColor());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderFogColor(RenderFogColorEvent event) {
        if (fog.getValue()) {

            // override fog color
            event.setColor(ColorUtil.getPrimaryColor());
            event.setCanceled(true);
        }
    }
}
