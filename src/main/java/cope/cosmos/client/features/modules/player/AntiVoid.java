package cope.cosmos.client.features.modules.player;

import cope.cosmos.client.features.modules.Category;
import cope.cosmos.client.features.modules.Module;
import cope.cosmos.client.features.setting.Setting;
import cope.cosmos.util.client.ChatUtil;
import cope.cosmos.util.world.TeleportUtil;

/**
 * @author aesthetical, linustouchtips
 * @since 11/21/2021
 */
@SuppressWarnings("unused")
public class AntiVoid extends Module {
    public static AntiVoid INSTANCE;

    public AntiVoid() {
        super("AntiVoid", Category.PLAYER, "Prevents you from getting stuck in the void");
        INSTANCE = this;
    }

    public static Setting<Mode> mode = new Setting<>("Mode", Mode.SUSPEND).setDescription("How to stop you from falling into the void");

    // speed settings
    public static Setting<Double> glide = new Setting<>("GlideSpeed", 1.0, 5.0, 10.0, 1).setDescription("The value to divide your vertical motion by").setVisible(() -> mode.getValue().equals(Mode.GLIDE));
    public static Setting<Double> rise = new Setting<>("RiseSpeed",  0.1, 0.5, 5.0, 0).setDescription("What to set your vertical motion to").setVisible(() -> mode.getValue().equals(Mode.RISE));

    @Override
    public void onUpdate() {
        // if we are in the void, aka below y-pos 0
        if (mc.player.posY <= 0) {
            // notify the player that we are attempting to get out of the void
            ChatUtil.sendMessageWithOptionalDeletion("Attempting to get player out of void!", 100);

            switch (mode.getValue()) {
                case SUSPEND:
                    // stop all vertical motion
                    mc.player.motionY = 0;
                    break;
                case GLIDE:
                    // fall slower
                    if (mc.player.motionY < 0) {
                        mc.player.motionY /= glide.getValue();
                    }

                    break;
                case RUBBERBAND:
                    // attempt to rubberband out of the void
                    TeleportUtil.teleportPlayerKeepMotion(mc.player.posX, mc.player.posY + 4, mc.player.posZ);
                    break;
                case RISE:
                    // attempt to float up out of the void
                    mc.player.motionY = rise.getValue();
                    break;
            }
        }
    }

    public enum Mode {
        /**
         * Stops all vertical motion, freezes the player
         */
        SUSPEND,

        /**
         * Slows down vertical movement
         */
        GLIDE,

        /**
         * Attempts to rubberband out of the void
         */
        RUBBERBAND,

        /**
         * Attempts to float up out of the void
         */
        RISE
    }
}
