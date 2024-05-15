package onedsix.client.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import onedsix.client.OndsixClient;
import onedsix.core.Vars;
import onedsix.core.util.Logger;

public class ConnectScreen extends CustomScreen {
    private static final Logger L = new Logger(ConnectScreen.class);
    public static final VisLabel addressLabel = new VisLabel("Address");
    public static final VisTextField addressField = new VisTextField("localhost");
    public static final VisLabel portLabel = new VisLabel("Port");
    public static final VisTextField portField = new VisTextField("36000");
    public static final VisLabel usernameLabel = new VisLabel("Username");
    public static final VisTextField usernameField = new VisTextField("Username");
    public static final VisTextButton connectButton = new VisTextButton("Connect");
    public static final VisTextButton backButton = new VisTextButton("Back");
    public static final VisLabel errorLabel = new VisLabel(null);

    @Override
    public void createScreen() {
        stage.setViewport(Vars.viewportHUD);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        connectButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    Vars.networker.start();
                    ((Client) Vars.networker).connect(15000, addressField.getText(), Integer.parseInt(portField.getText()),  Integer.parseInt(portField.getText()));
                }
                catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    L.error(e.getMessage(), e);

                    super.touchUp(event, x, y, pointer, button);
                    super.setVisualPressed(false);
                    return; // Exit this method due to the failed connection
                }

                // If you get to here, the connection was successful



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
                errorLabel.setText(null);
            }
        });

        stage.addActor(root);
        root.clear();
        root.padLeft(Vars.viewportHUD.getWorldWidth()*0.1f).left();
        root.add(addressLabel).width(250).padBottom(20);
        root.add(addressField).width(250).padBottom(20).row();
        root.add(portLabel).width(250).padBottom(20);
        root.add(portField).width(250).padBottom(20).row();
        root.add(usernameLabel).width(250).padBottom(20);
        root.add(usernameField).width(250).padBottom(20).row();
        root.add(connectButton).width(250).padBottom(20);
        root.add(backButton).width(250).padBottom(20).row();
        root.add(errorLabel).colspan(2);
    }
}
