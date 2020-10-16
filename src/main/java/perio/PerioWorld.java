package perio;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.persistence.FilePersistence;
import nl.han.ica.oopg.persistence.IPersistence;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.CenterFollowingViewport;
import nl.han.ica.oopg.view.View;

// Eigen classes
import perio.buttons.Button;
import perio.buttons.PushButton;
import perio.buttons.SwitchButton;
import perio.obstacles.DummyTarget;
import perio.obstacles.ITarget;
import perio.tiles.FloorTile;

public class PerioWorld extends GameEngine {

    // Game vars
    public static String MEDIA_PATH = "src/main/java/perio/media/";
    public static int worldWidth = 840;
    public static int worldHeight = 1400;
    public static int zoomWidth = 840;
    public static int zoomHeight = 700;

    private IPersistence persistence;
    private TextObject dashboardText;

    // Game Objects
    private Player playerOne;
    private Player playerTwo;
    private FollowObject followObject;

    private Button testPushButton;
    private Button testSwitchButton;
    private ITarget dummyTarget;

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
        // init
        initSound();
        initDashboard(zoomWidth, 100);
        initTileMap();
        initPersistence();

        initGameObjects();

        createVieWithViewport();
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
        dashboardText = new TextObject("test", 40);
        dashboardText.setForeColor(255, 255, 255, 255);

        dashboard.addGameObject(dashboardText);
        addDashboard(dashboard);

    }

    /**
     * Initialiseert alle  spel objecten
     */
    private void initGameObjects() {

        // Players
        playerOne = new Player(this, 1);
        addGameObject(playerOne, 50, worldHeight - 140 - playerOne.getHeight());

        playerTwo = new Player(this, 2);
        addGameObject(playerTwo, 50 + playerOne.getWidth(), worldHeight - 140 - playerTwo.getHeight());

        // Follow Object
        followObject = new FollowObject(this, playerOne, playerOne);
        addGameObject(followObject);

        // Buttons
        testPushButton = new PushButton();
        testSwitchButton = new SwitchButton();

        // Dummy Target
        dummyTarget = new DummyTarget();

        testPushButton.addTarget(dummyTarget);
        testSwitchButton.addTarget(dummyTarget);
        addGameObject(testPushButton, 300, worldHeight - 140 - testPushButton.getHeight());
        addGameObject(testSwitchButton, 500, worldHeight - 140 - testSwitchButton.getHeight());
        addGameObject((GameObject) dummyTarget, 650, worldHeight - 140 - ((GameObject) dummyTarget).getHeight());

    }

    /**
     * Initialiseert de tileMap
     */
    private void initTileMap() {
        // Load Sprites
        Sprite castleLeftSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/castleLeft.png"));
        Sprite castleMidSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/castleMid.png"));
        Sprite castleRightSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/castleRight.png"));
        Sprite castleCenterSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/castleCenter.png"));

        Sprite buttonOnSprite = new Sprite(MEDIA_PATH.concat("tiles/buttons/buttonOn.png"));
        Sprite buttonOffSprite = new Sprite(MEDIA_PATH.concat("tiles/buttons/buttonOff.png"));
        Sprite switchOnSprite = new Sprite(MEDIA_PATH.concat("tiles/buttons/laserSwitchYellowOn.png"));
        Sprite switchOffSprite = new Sprite(MEDIA_PATH.concat("tiles/buttons/laserSwitchYellowOff.png"));

        // Create tile types with the right Tile class and sprite
        TileType<FloorTile> castleLeftTile = new TileType<>(FloorTile.class, castleLeftSprite);
        TileType<FloorTile> castleMidTile = new TileType<>(FloorTile.class, castleMidSprite);
        TileType<FloorTile> castleRightTile = new TileType<>(FloorTile.class, castleRightSprite);
        TileType<FloorTile> castleCenterTile = new TileType<>(FloorTile.class, castleCenterSprite);

        int tileSize = 70;
        TileType[] tileTypes = {castleCenterTile, castleLeftTile, castleMidTile, castleRightTile};
        int tilesMap[][] = {
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},

                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        tileMap = new TileMap(tileSize, tileTypes, tilesMap);

        // Initialize On Sprites for Buttons
//        ((PushButtonTile) tileMap.getTileOnIndex(4, 17)).setOnSprite(buttonOnSprite);
//        ((SwitchTile) tileMap.getTileOnIndex(7, 17)).setOnSprite(switchOnSprite);
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
     */

    private void createVieWithViewport() {
        CenterFollowingViewport viewPort = new CenterFollowingViewport(followObject, zoomWidth, zoomHeight);
        View view = new View(viewPort, worldWidth, worldHeight);

        setView(view);
        size(zoomWidth, zoomHeight);
        view.setBackground(loadImage(MEDIA_PATH.concat("backgrounds/bg.png")));
    }
}
