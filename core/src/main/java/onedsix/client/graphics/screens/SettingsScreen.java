package onedsix.client.graphics.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.util.Validators;
import com.kotcrab.vis.ui.widget.*;
import onedsix.client.OndsixClient;
import onedsix.core.Vars;
import onedsix.core.systems.GameSettings;
import onedsix.core.util.Logger;

public class SettingsScreen extends CustomScreen {
    private static final Logger L = new Logger(SettingsScreen.class);
    private static final VisTable settingsContainer = new VisTable();
    public static final VisTextButton saveAndBackButton = new VisTextButton("Save");
    public static final VisTextButton backButton = new VisTextButton("Cancel");
    private static GameSettings.SettingsJson writableSettings;

    @Override
    public void createScreen() {
        stage.setViewport(Vars.viewportHUD);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        saveAndBackButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameSettings.writeSettings();
                OndsixClient.getClient().setScreen(Vars.mainMenuScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.mainMenuScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        stage.addActor(root);
        root.clear();

        root.padLeft(Vars.viewportHUD.getWorldWidth()*0.1f).left();
        root.add(scroll).colspan(3).maxHeight(Vars.viewportHUD.getWorldHeight()/2).row();
        root.add(saveAndBackButton).width(150).padBottom(20);
        root.add(new Actor()).width(20).padBottom(20);
        root.add(backButton).width(150).padBottom(20).row();

        scroll.setActor(settingsContainer);
        scroll.setScrollBarPositions(false, false);
        scroll.setScrollbarsVisible(true);


        VisLabel scrollLabel = new VisLabel("(this is a scrollable interface! click on it to be able to scroll!)");
        settingsContainer.add(scrollLabel).padBottom(20).colspan(2).row();

        VisLabel graphicalLabel = new VisLabel("Graphical");
        graphicalLabel.setFontScale(3);
        new Tooltip.Builder("Everything related to Graphics. Some options may be locked due to OpenGL or OS incompatibility.").target(graphicalLabel).build();
        settingsContainer.add(graphicalLabel).padBottom(20).colspan(2).row();

        VisRadioButton vsync = new VisRadioButton("Use Vsync?");
        new Tooltip.Builder("Should the game use Vsync while rendering. If your selected framerate is higher than your monitors refresh rate,\n it will be capped to the refresh rate if this settings is turned on.").target(vsync).build();
        settingsContainer.add(vsync).width(200).padBottom(30).colspan(2).row();

        VisValidatableTextField fps = new VisValidatableTextField(new Validators.IntegerValidator());
        fps.setText("60");
        VisLabel fpsLabel = new VisLabel("Prefered FPS");
        new Tooltip.Builder("Your prefered FPS.\nMay not work properly-or at all-due to OpenGL or OS incompatibility.").target(fps).build();
        new Tooltip.Builder("Your prefered FPS.\nMay not work properly-or at all-due to OpenGL or OS incompatibility.").target(fpsLabel).build();
        settingsContainer.add(fpsLabel).padBottom(10);
        settingsContainer.add(fps).padBottom(10).row();


        VisLabel modsLabel = new VisLabel("Mods and Datagen");
        modsLabel.setFontScale(3);
        new Tooltip.Builder("Mods, Datagen, and File Handling.").target(modsLabel).build();
        settingsContainer.add(modsLabel).padBottom(20).colspan(2).row();

        VisTextField path = new VisTextField("./temp/");
        VisLabel pathLabel = new VisLabel("Custom Extract Path");
        new Tooltip.Builder("A custom path for where the game extracts data to.\nBy default its ./temp/, but that can be changed to somewhere else in case you have an automated system for it for malware scanning/data collection/automated deletion.").target(path).build();
        new Tooltip.Builder("A custom path for where the game extracts data to.\nBy default its ./temp/, but that can be changed to somewhere else in case you have an automated system for it for malware scanning/data collection/automated deletion.").target(pathLabel).build();
        settingsContainer.add(pathLabel).padBottom(10);
        settingsContainer.add(path).padBottom(10).row();

        VisRadioButton dlOnJoin = new VisRadioButton("Allow Downloading on Join");
        new Tooltip.Builder("Whenever you connect tot a server without a specific mod, the server may be able to send you that mod for you to use, after the game restarts of course.\nThis is obviously a security vulnerability, so it is disabled by default.\nBy turning this on you acknowledge this fact and will not complain about it in online spaces.").target(dlOnJoin).build();
        settingsContainer.add(dlOnJoin).padBottom(30).colspan(2).row();


        VisLabel miscLabel = new VisLabel("Miscellaneous");
        miscLabel.setFontScale(3);
        new Tooltip.Builder("Miscellaneous settings that don't fit elsewhere.").target(miscLabel).build();
        settingsContainer.add(miscLabel).padBottom(20).colspan(2).row();

        VisRadioButton splash = new VisRadioButton("Use Splash Text?");
        new Tooltip.Builder("This setting exists mostly for compatibility reasons, as some screen readers and misc. programs look for the name of the process to find a specific one.\n This also exists as a way to turn off splash texts globally in case some mod adds one you dont like.").target(splash).build();
        settingsContainer.add(splash).padBottom(30).colspan(2).row();


        VisLabel dangerLabel = new VisLabel("Danger Zone");
        dangerLabel.setFontScale(3);new Tooltip.Builder("Settings for dangerous stuff, like resetting the game, uninstalling it, cleaning temporary files, etc.").target(dangerLabel).build();
        settingsContainer.add(dangerLabel).padBottom(20).colspan(2).row();

        VisTextButton trashButton = new VisTextButton("Empty ./temp/ and ./logs/ folders");
        new Tooltip.Builder("Safely deletes everything inside both the ./temp/ and ./logs/ folders and restarts the game.\nThis will clear out a signifigant amount of space off your drive, but will cost you time in the form of having to re-cache everything again on next launch.\nRestarting may not work on some systems.").target(trashButton).build();
        settingsContainer.add(trashButton).padBottom(10).colspan(2).row();

        VisTextButton resetButton = new VisTextButton("Reset Game Data");
        new Tooltip.Builder("Deletes your local playerdata.json file and any other files associated with you.\nBasically a nuke to everything you've done in the game, locally at least.\nMake sure you really want to do this before you go through with it.").target(resetButton).build();
        settingsContainer.add(resetButton).padBottom(10).colspan(2).row();

        VisTextButton uninstall = new VisTextButton("Uninstall Game :(");
        new Tooltip.Builder("Deletes everything associated with 1D6.\nOptional setting to delete the current directory as well.").target(uninstall).build();
        settingsContainer.add(uninstall).padBottom(10).colspan(2).row();
    }

    @Override
    public void show() {
        writableSettings = Vars.settings;
        super.show();
    }
}
