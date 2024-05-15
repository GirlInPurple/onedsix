package onedsix.core.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import onedsix.core.Vars;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import onedsix.client.ClientPlayer;
import onedsix.client.graphics.GeometryHandler;
import onedsix.core.util.FileHandler;
import onedsix.core.util.Logger;
import onedsix.core.util.Logger.Level;

import static onedsix.client.graphics.SvgHandler.loadSvgFromFile;
import static onedsix.client.graphics.SvgHandler.svgToPng;

public class DatagenHandler {
    private static final Logger L = new Logger(DatagenHandler.class);

    public final String name;
    public final JsonObject jo;
    public final JsonArray geometry;
    public final JsonArray npcs;
    public final JsonObject stats;
    public final JsonObject misc;

    public DatagenHandler(JsonObject jo, String name) {
        this.name = name;
        this.jo = jo;
        this.geometry = jo.getAsJsonArray("geom");
        this.npcs = jo.getAsJsonArray("npc");
        this.stats = jo.getAsJsonObject("stats");
        this.misc = jo.getAsJsonObject("misc");

        long startTime = System.nanoTime();

        try {
            buildGeom(this);
            buildEntities(this);
            buildCellStats(this);
        }
        catch (Exception e) {
            L.error(e.getMessage(), e);
        }
        finally {
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double seconds = (double) elapsedTime / 1_000_000_000.0;
            L.info("Built Datagen! Elapsed Time: " + seconds + " seconds");
        }
    }

    public static void read() {
        // slow? yes.
        // can it use runnable? gl crashes if it does.
        // do we have any other options? not really.
        for (String s : Vars.loadList) {
            try {
                JsonObject js;

                L.loadingLogger("Reading " + s, Level.INFO);
                js = FileHandler.RESOURCES.getJson(s);

                if (js != null) {
                    L.loadingLogger("Datagen started for " + s, Level.INFO);
                    if (s.equals("playerdata.json")) {
                        buildPlayer(js);
                    } else {
                        new DatagenHandler(js, s);
                    }
                } else {
                    L.loadingLogger("Could not read " + s, Level.ERROR);
                }
            }
            catch (Exception e) {
                L.error(e.getMessage(), e);
            }
        }

        if (!Vars.loadList.isEmpty()) L.loadingLogger("Finished loading datagen!", Level.INFO);
        Vars.loadList.clear();
    }

    public static void buildGeom(DatagenHandler dgh) {
        for (JsonElement je : dgh.geometry.asList()) {
            switch (je.getAsJsonObject().get("model_type").getAsString()) {
                case "box": Vars.modelInstances.add(GeometryHandler.transformShorthand(
                        // Set the needed position
                        je.getAsJsonObject().get("x").getAsFloat(),
                        je.getAsJsonObject().get("y").getAsFloat(),
                        je.getAsJsonObject().get("z").getAsFloat(),
                        // Create the box
                        new ModelInstance(
                                Vars.modelBuilder.createBox(
                                        je.getAsJsonObject().get("width").getAsFloat(),
                                        je.getAsJsonObject().get("height").getAsFloat(),
                                        je.getAsJsonObject().get("depth").getAsFloat(),
                                        new Material(),
                                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                                ))
                ));
            }
        }
    }

    public static void buildEntities(DatagenHandler dgh) {
        for (JsonElement je : dgh.npcs.asList()) {
            L.info("Would be creating an data right now!");
            je.getAsJsonObject().get("model_type").getAsString();
        }
    }

    public static void buildCellStats(DatagenHandler dgh) {
        L.info("Would be handling cell stats right now!");
    }

    public static void buildEntityStats(JsonObject JO) {
        L.info("Would be handling data stats right now!");
    }

    public static void buildPlayer(JsonObject playerdata) {
        L.loadingLogger("Loading Playerdata...", Level.INFO);
        //Texture t = new Texture(svgToPng(loadSvgFromString(playerdata.get("svg").getAsString()), "player"));
        Texture t = new Texture(svgToPng(loadSvgFromFile(Gdx.files.internal("test.svg").path()), "player"));
        JsonArray pos = playerdata.get("position").getAsJsonArray();

        Vars.player = new ClientPlayer(
                new Identifier<>(ClientPlayer.class, Vars.MOD_ID),
                new Attributes(),
                Decal.newDecal(2, 2, new TextureRegion(t), true),
                "player",
                new Vector3(pos.get(0).getAsFloat(),pos.get(1).getAsFloat(),pos.get(2).getAsFloat())
        );
        L.loadingLogger("Loaded Playerdata!", Level.INFO);
    }
}
