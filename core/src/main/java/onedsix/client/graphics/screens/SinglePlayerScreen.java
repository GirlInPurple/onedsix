package onedsix.client.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.kotcrab.vis.ui.widget.*;
import onedsix.client.OndsixClient;
import onedsix.core.Vars;
import onedsix.core.util.Logger;

public class SinglePlayerScreen extends CustomScreen {
    private static final Logger L = new Logger(ConnectScreen.class);
    public static final VisTable lanTable = new VisTable();
    public static final VisRadioButton lanButton = new VisRadioButton("Enable LAN");
    public static final VisLabel portLabel = new VisLabel("Port");
    public static final VisTextField portField = new VisTextField("36000");
    public static final VisLabel passLabel = new VisLabel("Password");
    public static final VisTextField passField = new VisTextField("OnlyYouShouldKnowThis");
    public static final VisTextButton backButton = new VisTextButton("Back");
    public static final VisLabel errorLabel = new VisLabel(null);

    @Override
    public void createScreen() {
        stage.setViewport(Vars.viewportHUD);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lanButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                portField.setDisabled(!portField.isDisabled());
                passField.setDisabled(!passField.isDisabled());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.mainMenuScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
                errorLabel.setText(null);
            }
        });

        stage.addActor(root);
        root.clear();
        root.padLeft(Vars.viewportHUD.getWorldWidth()*0.1f).left();

        root.add(lanButton).row();
        root.add(lanTable).row();

        lanTable.add(portLabel).width(250).padBottom(20);
        lanTable.add(portField).width(250).padBottom(20).row();
        portField.setDisabled(true);
        lanTable.add(passLabel).width(250).padBottom(20);
        lanTable.add(passField).width(250).padBottom(20).row();
        passField.setDisabled(true);

        root.add(backButton).width(250).padBottom(20).row();
    }
}
