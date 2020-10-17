package perio;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
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
import perio.collectibles.Coin;
import perio.collectibles.Collectible;
import perio.collectibles.Gem;
import perio.obstacles.IObstacle;
import perio.obstacles.Lift;
import perio.tiles.FloorTile;

import java.util.ArrayList;

public class PerioWorld extends GameEngine {

    // Game vars
    public static String MEDIA_PATH = "src/main/java/perio/media/";
    public static int WORLDWIDTH = 840;
    public static int WORLDHEIGHT = 1400;
    public static int ZOOMWIDTH = 840;
    public static int ZOOMHEIGHT = 700;

    private IPersistence persistence;
    private TextObject dashboardText;

    // Game Objects
    private Player playerOne;
    private Player playerTwo;
    private FollowObject followObject;
    private ArrayList<Collectible> collectibles;
    private ArrayList<Button> buttons;
    private ArrayList<IObstacle> obstacles;

    // Sounds
    private Sound backgroundSound;
    private Sound coinSound;
    private Sound gemSound;
    private Sound pushButtonSound;
    private Sound switchButtonSound;
    private Sound liftSound;
    private Sound jumpSound;
    private Sound crouchSound;

    public static void main(String[] args) {
        PerioWorld pw = new PerioWorld();
        pw.runSketch();
    }

    @Override
    public void setupGame() {
        // init
        initSound();
        initDashboard(ZOOMWIDTH, 100);
        initTileMap();
        initPersistence();

        initGameObjects();

        createViewWithViewport();
    }

    @Override
    public void update() {
        // Leeg
    }

    /**
     * Initialiseert alle geluidsobjecten
     */
    private void initSound() {
        backgroundSound = new Sound(this, MEDIA_PATH.concat("backgrounds/bg.mp3"));
        coinSound = new Sound(this, MEDIA_PATH.concat("collectibles/coinSound.mp3"));
        gemSound = new Sound(this, MEDIA_PATH.concat("collectibles/gemSound.mp3"));
        pushButtonSound = new Sound(this, MEDIA_PATH.concat("buttons/pushButtonSound.mp3"));
        switchButtonSound = new Sound(this, MEDIA_PATH.concat("buttons/pushButtonSound.mp3"));
        liftSound = new Sound(this, MEDIA_PATH.concat("obstacles/liftSound.mp3"));
//        jumpSound = new Sound(this, MEDIA_PATH.concat("characters/jumpSound.mp3"));
//        crouchSound = new Sound(this, MEDIA_PATH.concat("characters/crouchSound.mp3"));

//        backgroundSound.loop(-1);
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
        addGameObject(playerOne, columnToXCoordinate(1), rowToYCoordinate(16));

        playerTwo = new Player(this, 2);
        addGameObject(playerTwo, columnToXCoordinate(2), rowToYCoordinate(16));

        // Follow Object
        followObject = new FollowObject(this, playerOne, playerTwo);
        addGameObject(followObject);

        // Collectibles
        collectibles = new ArrayList<>();

        collectibles.add(new Coin(this));
        collectibles.add(new Gem(this));

        addGameObject(collectibles.get(0), columnToXCoordinate(3), rowToYCoordinate(12));
        addGameObject(collectibles.get(1), columnToXCoordinate(1), rowToYCoordinate(12));

        // Buttons
        buttons = new ArrayList<>();

        buttons.add(new PushButton(pushButtonSound));      // 0
        buttons.add(new PushButton(pushButtonSound));      // 1
        buttons.add(new SwitchButton(switchButtonSound));    // 2

        addGameObject(buttons.get(0), columnToXCoordinate(8), rowToYCoordinate(17));
        addGameObject(buttons.get(1), columnToXCoordinate(8), rowToYCoordinate(12));
        addGameObject(buttons.get(2), columnToXCoordinate(4), rowToYCoordinate(17));

        // Obstacles
        obstacles = new ArrayList<>();

        obstacles.add(new Lift(this, rowToYCoordinate(13), liftSound));

        addGameObject((SpriteObject) obstacles.get(0), columnToXCoordinate(10), rowToYCoordinate(17));

        // Koppel buttons aan obstacles
        buttons.get(0).addTarget(obstacles.get(0));
        buttons.get(1).addTarget(obstacles.get(0));
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
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 3, -1, -1},
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

    private void createViewWithViewport() {
        CenterFollowingViewport viewPort = new CenterFollowingViewport(followObject, ZOOMWIDTH, ZOOMHEIGHT);
        View view = new View(viewPort, WORLDWIDTH, WORLDHEIGHT);

        setView(view);
        size(ZOOMWIDTH, ZOOMHEIGHT);
        view.setBackground(loadImage(MEDIA_PATH.concat("backgrounds/bg.png")));
    }

    /**
     * Helper functie: Calculeert X positie (in pixels) in tileMap om object te plaatsen.
     * @param   column  Index van colom in tileMap waar object geplaatst moet worden.
     * @return          X Coordinaat in float
     */
    private float columnToXCoordinate(int column) {
        return column * tileMap.getTileSize();
    }

    /**
     * Helper functie: Calculeert Y positie (in pixels) in tileMap om object te plaatsen.
     * @param   row     Index van row in tileMap waar object geplaatst moet worden.
     * @return          Y Coordinaat in float
     */
    private float rowToYCoordinate(int row) {
        return row * tileMap.getTileSize();
    }
}
