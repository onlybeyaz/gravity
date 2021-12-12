package cope.cosmos.client;

import com.mojang.brigadier.CommandDispatcher;
import cope.cosmos.client.clickgui.windowed.WindowGUI;
import cope.cosmos.client.features.modules.Module;
import cope.cosmos.client.features.modules.client.Colors;
import cope.cosmos.client.features.modules.client.DiscordPresence;
import cope.cosmos.client.features.modules.client.Font;
import cope.cosmos.client.features.modules.client.Social;
import cope.cosmos.client.manager.Manager;
import cope.cosmos.client.manager.managers.*;
import cope.cosmos.event.bus.EventBus;
import cope.cosmos.util.render.FontUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bon55, linustouchtips
 * @since 05/05/2021
 */
@SuppressWarnings("unused")
@Mod(modid = Cosmos.MOD_ID, name = Cosmos.NAME, version = Cosmos.VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class Cosmos {

    // mod info
    public static final String MOD_ID = "cosmos";
    public static final String NAME = "Cosmos";
    public static final String VERSION = "1.1.0";

    // client event bus
    public static final EventBus EVENT_BUS = new EventBus();

    // the client's command prefix
    public static String PREFIX = "*";

    // tracks whether or not the client has already run for the first time
    public static boolean SETUP = false;

    // client instance
    @Mod.Instance
    public static Cosmos INSTANCE;

    // the client gui
    private WindowGUI windowGUI;

    // list of managers
    private final List<Manager> managers = new ArrayList<>();

    // all client managers
    private TickManager tickManager;
    private SocialManager socialManager;
    private PresetManager presetManager;
    private RotationManager rotationManager;
    private ThreadManager threadManager;
    private HoleManager holeManager;
    private FontManager fontManager;
    private NotificationManager notificationManager;
    private ReloadManager reloadManager;
    private PatchManager patchManager;
    private PopManager popManager;
    private InteractionManager interactionManager;
    private ChangelogManager changelogManager;
    private SoundManager soundManager;
    private CommandDispatcher<Object> commandDispatcher;
    
    public Cosmos() {
    	INSTANCE = this;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // load the client custom font
        FontUtil.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Progress Manager
        ProgressManager.ProgressBar progressManager = ProgressManager.push("Cosmos", 16);

        // Register the event manager
        MinecraftForge.EVENT_BUS.register(EventManager.INSTANCE);
        progressManager.step("Registering Events");

        // load the commands (Mojang's Brigadier )
        commandDispatcher = new CommandDispatcher<>();
        CommandManager.registerCommands();
        progressManager.step("Loading Commands");

        // sets up the tick manager
        tickManager = new TickManager();
        managers.add(tickManager);
        progressManager.step("Setting up Tick Manager");

        // set's up the rotation manager
        rotationManager = new RotationManager();
        managers.add(rotationManager);
        progressManager.step("Setting up Rotation Manager");

        // set's up the social manager
        socialManager = new SocialManager();
        managers.add(socialManager);
        progressManager.step("Setting up Social Manager");

        // set's up the preset manager
        presetManager = new PresetManager();
        managers.add(presetManager);
        progressManager.step("Setting up Config Manager");

        // set's up the GUI
        windowGUI = new WindowGUI();
        progressManager.step("Setting up GUI's");

        // set's up the reload manager
        reloadManager = new ReloadManager();
        managers.add(reloadManager);
        progressManager.step("Setting up Reload Manager");

        // set's up the notification manager
        notificationManager = new NotificationManager();
        managers.add(notificationManager);
        progressManager.step("Setting up Notification Manager");

        // set's up the patch manager
        patchManager = new PatchManager();
        managers.add(patchManager);
        progressManager.step("Setting up Patch Helper");

        // set's up the pop manager
        popManager = new PopManager();
        managers.add(popManager);
        progressManager.step("Setting up Pop Manager");

        // set's up the thread manager
        threadManager = new ThreadManager();
        managers.add(threadManager);
        progressManager.step("Setting up Threads");

        // set's up the hole manager
        holeManager = new HoleManager();
        managers.add(holeManager);
        progressManager.step("Setting up Hole Manager");

        // set's up the interaction manager
        interactionManager = new InteractionManager();
        managers.add(interactionManager);
        progressManager.step("Setting up Interaction Manager");

        // set's up the changelog manager
        changelogManager = new ChangelogManager();
        managers.add(changelogManager);
        progressManager.step("Setting up Changelog Manager");

        // set's up the sound manager
        soundManager = new SoundManager();
        managers.add(soundManager);
        progressManager.step("Setting up Sound System");

        ProgressManager.pop(progressManager);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(NAME + " " + VERSION);

        // start the discord presence on startup
        PresenceManager.startPresence();
    }

    public List<Manager> getManagers() {
        return managers;
    }
    
    public WindowGUI getWindowGUI() {
        return windowGUI;
    }

    public TickManager getTickManager() {
        return tickManager;
    }

    public SocialManager getSocialManager() {
        return socialManager;
    }

    public PresetManager getPresetManager() {
        return presetManager;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public RotationManager getRotationManager() {
        return rotationManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public HoleManager getHoleManager() {
        return holeManager;
    }

    public ReloadManager getReloadManager() {
        return reloadManager;
    }

    public PatchManager getPatchManager() {
        return patchManager;
    }

    public PopManager getPopManager() {
        return popManager;
    }

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    public ChangelogManager getChangelogManager() {
        return changelogManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public CommandDispatcher<Object> getCommandDispatcher() {
        return commandDispatcher;
    }
    
    public List<Module> getNullSafeMods() {
    	return Arrays.asList(
                DiscordPresence.INSTANCE,
                Colors.INSTANCE,
                Font.INSTANCE,
                Social.INSTANCE,
                Font.INSTANCE
        );
    }
}
