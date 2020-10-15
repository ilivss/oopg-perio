package perio;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.CenterFollowingViewport;
import nl.han.ica.oopg.view.EdgeFollowingViewport;
import nl.han.ica.oopg.view.View;

// Eigen classes
import perio.players.Player;
import perio.tiles.FloorTile;

import java.util.ArrayList;

public class PerioWorld extends GameEngine {

    // Static variables
    public static String MEDIA_PATH = "src/main/java/perio/media/";

    // Game variables
    private Player playerOne;
    private Player playerTwo;
    private FollowObject followObject;

    // Sounds
    private Sound backgroundSound;
    private Sound walkSound;
    private Sound jumpSound;
    private Sound collectibleSound;
    private Sound consumableSound;
    private Sound npcSound;


    public static void main(String[] args) {
        PerioWorld pw = new PerioWorld();
        pw.runSketch();
    }

    @Override
    public void setupGame() {
        int worldWidth = 840;
        int worldHeight = 700;
        int zoomWidth = 840;
        int zoomHeight = 700;

        // init
        initTileMap();
        initGameObjects();

        // Dit moet altijd erin
        CenterFollowingViewport cfv = new CenterFollowingViewport(followObject, zoomWidth, zoomHeight);

        View view = new View(cfv, worldWidth, worldHeight);
        setView(view);
        size(zoomWidth, zoomHeight);
    }

    @Override
    public void update() {
        // Leeg
        getView().getViewport();
    }

    // Initialization methods
    private void initTileMap() {
        // Load Sprites
        Sprite castleLeftSprite = new Sprite(MEDIA_PATH.concat("tiles/castleLeft.png"));
        Sprite castleMidSprite = new Sprite(MEDIA_PATH.concat("tiles/castleMid.png"));
        Sprite castleRightSprite = new Sprite(MEDIA_PATH.concat("tiles/castleRight.png"));
        Sprite castleCenterSprite = new Sprite(MEDIA_PATH.concat("tiles/castleCenter.png"));


        // Create tile types with the right Tile class and sprite
        TileType<FloorTile> castleLeftTile = new TileType<>(FloorTile.class, castleLeftSprite);
        TileType<FloorTile> castleMidTile = new TileType<>(FloorTile.class, castleMidSprite);
        TileType<FloorTile> castleRightTile = new TileType<>(FloorTile.class, castleRightSprite);
        TileType<FloorTile> castleCenterTile = new TileType<>(FloorTile.class, castleCenterSprite);

        int tileSize = 70;
        TileType[] tileTypes = {castleLeftTile, castleMidTile, castleRightTile, castleCenterTile};
        int tilesMap[][] = {
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
        };

        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    private void initGameObjects() {

        // Players
        playerOne = new Player(this);
        addGameObject(playerOne);

        // Follow Object
        followObject = new FollowObject(this, playerOne, playerOne);
        addGameObject(followObject);
    }
}
