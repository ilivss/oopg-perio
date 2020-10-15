package perio;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.persistence.FilePersistence;
import nl.han.ica.oopg.persistence.IPersistence;
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

    // Game vars
    public static String MEDIA_PATH = "src/main/java/perio/media/";
    private IPersistence persistence;

    // Game Objects
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
        initSound();
        initDashboard(zoomWidth, 100);
        initTileMap();
        initPersistence();

        initGameObjects();

        createVieWithViewport(worldWidth, worldHeight, zoomWidth, zoomHeight);
    }

    @Override
    public void update() {
        // Leeg
    }

    /**
     * Initialiseert alle geluidsobjecten
     */
    private void initSound() {
//        backgroundSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"));
//        walkSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"));
//        jumpSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"));
//        collectibleSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"));
//        consumableSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"));
//        npcSound = new Sound(this, MEDIA_PATH.concat("sound.mp3"))
    }

    /**
     * Initialiseert dashboard
     *
     * @param dashboardWidth  Breedte van het dashboard
     * @param dashboardHeight Hoogte van het dashboard
     */
    private void initDashboard(int dashboardWidth, int dashboardHeight) {
        Dashboard dashboard = new Dashboard(0, 0, dashboardWidth, dashboardHeight);
        dashbo
    }

    /**
     * Initialiseert alle  spel objecten
     */
    private void initGameObjects() {

        // Players
        playerOne = new Player(this);
        addGameObject(playerOne);

        playerTwo = new Player(this);
        addGameObject(playerTwo);

        // Follow Object
        followObject = new FollowObject(this, playerOne, playerTwo);
        addGameObject(followObject);
    }

    /**
     * Initialiseert de tileMap
     */
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

    /**
     * Initialiseert opslag van speldata
     * Elke startup van het spel zal eerst proberen eerdere data te recoveren.
     */
    private void initPersistence() {
        persistence = new FilePersistence(MEDIA_PATH.concat("highscores.txt"));

        if (persistence.fileExists()) {
            // Doe iets met data in bestand...
        }
    }

    /**
     * Creeert de view een met Central Following Viewport
     *
     * @param worldWidth  Breedte van de wereld
     * @param worldHeight Hoogte van de wereld
     * @param zoomWidth   Breedte van het scherm
     * @param zoomHeight  Hoogte van het scherm
     */

    private void createVieWithViewport(int worldWidth, int worldHeight, int zoomWidth, int zoomHeight) {
        CenterFollowingViewport viewPort = new CenterFollowingViewport(followObject, zoomWidth, zoomHeight);
        View view = new View(viewPort, worldWidth, worldHeight);

        setView(view);
        size(zoomWidth, zoomHeight);
        view.setBackground(loadImage(MEDIA_PATH.concat("background.png")));
    }
}
