package onedsix.client.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import onedsix.client.OndsixClient;
import onedsix.core.Vars;
import onedsix.core.util.Logger;

public class ModListScreen extends CustomScreen {
    private static final Logger L = new Logger(ModListScreen.class);
    private static final VisTable leftTable = new VisTable();
    private static final VisTextButton backButton = new VisTextButton("Back");
    private static final VisTable modList = new VisTable();
    private static final VisTable modListSubRoot = new VisTable();
    private static final VisTable modInfo = new VisTable();
    private static final VisLabel modLabel = new VisLabel("Installed");

    @Override
    public void createScreen() {
        stage.setViewport(Vars.viewportHUD);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(root);
        root.clear();
        root.setDebug(true, true);
        root.setSize(Vars.viewportHUD.getWorldWidth()/3, Vars.viewportHUD.getWorldHeight()/2);

        root.add(leftTable).size(root.getWidth()*.25f, root.getHeight());
        root.add(modInfo).size(root.getWidth()*.75f, root.getHeight()).row();

        modLabel.setFontScale(1f);
        leftTable.add(modLabel).row();
        leftTable.add(backButton).padBottom(20).row();
        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                OndsixClient.getClient().setScreen(Vars.mainMenuScreen);
                super.touchUp(event, x, y, pointer, button);
                super.setVisualPressed(false);
            }
        });
        leftTable.add(scroll).row();

        scroll.setActor(modList);

        VisTable modTable = new VisTable();
        modList.add(modTable);

        // TODO: make this loop over all the mods and grab the needed data
        modTable.add(new VisImage(new Texture(Gdx.files.internal("badlogic.jpg"))))
                .size((float) (scroll.getWidth()*.33), (float) (scroll.getWidth()*.33));
        modTable.add(modListSubRoot).row();
        modTable.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                L.info("clicked");
                super.touchUp(event, x, y, pointer, button);
            }
        });

        modListSubRoot.add("name").height(10).row();
        modListSubRoot.add("mod version").height(10).row();
        modListSubRoot.add("first credit").height(10).row();

        modInfo.add("name").height(10).row();
        modInfo.add("mod version").height(10).row();
        modInfo.add("first credit").height(10).row();
    }
}
