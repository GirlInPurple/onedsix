package onedsix.client.graphics.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTextButton;
import onedsix.client.OndsixClient;
import onedsix.core.Vars;
import onedsix.core.util.Logger;

public class MainMenuScreen extends CustomScreen {
    private static final Logger L = new Logger(MainMenuScreen.class);
    public static final VisTextButton singlePlayerButton = new VisTextButton("Singleplayer");
    public static final VisTextButton multiPlayerButton = new VisTextButton("Multiplayer");
    public static final VisTextButton settingsButton = new VisTextButton("Settings");
    public static final VisTextButton modsButton = new VisTextButton("Mods");
    public static final VisTextButton exitButton = new VisTextButton("Exit");

    @Override
    public void createScreen() {
        stage.setViewport(Vars.viewportHUD);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        singlePlayerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.singlePlayerScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        multiPlayerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.connectScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.settingsScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        modsButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.modListScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });

        stage.addActor(root);
        root.clear();
        root.padLeft(Vars.viewportHUD.getWorldWidth()*0.1f).left();
        root.add(singlePlayerButton).width(250).padBottom(20).row();
        root.add(multiPlayerButton).width(250).padBottom(20).row();
        root.add(settingsButton).width(250).padBottom(20).row();
        root.add(modsButton).width(250).padBottom(20).row();
        root.add(exitButton).width(250).padBottom(20).row();
    }
}
