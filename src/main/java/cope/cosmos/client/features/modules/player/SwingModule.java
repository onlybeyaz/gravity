package cope.cosmos.client.features.modules.player;

import cope.cosmos.client.features.modules.Category;
import cope.cosmos.client.features.modules.Module;
import cope.cosmos.client.features.setting.Setting;
import net.minecraft.util.EnumHand;

/**
 * @author linustouchtips
 * @since 08/22/2022
 */
public class SwingModule extends Module {
    public static SwingModule INSTANCE;

    public SwingModule() {
        super("Swing", Category.PLAYER, "Modifies the swinging hand");
        INSTANCE = this;
    }

    // **************************** general ****************************

    public static Setting<Mode> mode = new Setting<>("Mode", Mode.MAINHAND)
            .setDescription("Swinging hand");

    // current swinging hand
    private EnumHand hand = EnumHand.MAIN_HAND;

    @Override
    public void onUpdate() {

        // update the player's swinging hand
        switch (mode.getValue()) {
            case OFFHAND:
                hand = EnumHand.OFF_HAND;
                break;
            case MAINHAND:
                hand = EnumHand.MAIN_HAND;
                break;
            case ALTERNATE:

                // alternate between hands
                if (hand.equals(EnumHand.MAIN_HAND)) {
                    hand = EnumHand.OFF_HAND;
                }

                else {
                    hand = EnumHand.MAIN_HAND;
                }

                break;
        }

        // update player's swinging hand
        mc.player.swingingHand = hand;
    }

    /**
     * Gets the current swinging hand
     * @return The current swinging hand
     */
    public EnumHand getHand() {
        return hand;
    }

    public enum Mode {

        /**
         * Swings the mainhand
         */
        MAINHAND,

        /**
         * Swings the offhand
         */
        OFFHAND,

        /**
         * Alternates between hands
         */
        ALTERNATE
    }
}
