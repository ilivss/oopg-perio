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
import perio.NPCs.Ghost;
import perio.NPCs.NPC;
import perio.buttons.Button;
import perio.buttons.PushButton;
import perio.buttons.SwitchButton;
import perio.consumables.Coin;
import perio.consumables.Consumable;
import perio.consumables.Gem;
import perio.consumables.Health;
import perio.obstacles.Flag;
import perio.obstacles.IObstacle;
import perio.obstacles.Lava;
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
    private int timer = 0;

    private IPersistence persistence;
    private TextObject playerOneDashboardText;
    private TextObject playerTwoDashboardText;

    // Game Objects
    private Player playerOne;
    private Player playerTwo;
    private FollowObject followObject;

    private ArrayList<Consumable> consumables;
    private ArrayList<Button> buttons;
    private ArrayList<IObstacle> obstacles;
    private ArrayList<NPC> NPCs;

    // Sounds
    private Sound backgroundSound;
    private Sound gameOverSound;
    private Sound coinSound;
    private Sound gemSound;
    private Sound healthSound;
    private Sound pushButtonSound;
    private Sound switchButtonSound;
    private Sound liftSound;
    private Sound lavaSound;
    private Sound flagSound;
    private Sound ghostSound;

    public static void main(String[] args) {
        PerioWorld pw = new PerioWorld();
        pw.runSketch();
    }

    @Override
    public void setupGame() {
        // init
        initSound();
        initDashboard();
        initTileMap();
        initPersistence();

        initGameObjects();

        createViewWithViewport();
    }

    @Override
    public void update() {
        updateDashboard();

        // TODO: Stop game wanneer 1 vd 2 spelers dood gaat!
    }

    /**
     * Initialiseert alle geluidsobjecten
     */
    private void initSound() {
        backgroundSound = new Sound(this, MEDIA_PATH.concat("backgrounds/bg.mp3"));
        gameOverSound = new Sound(this, MEDIA_PATH.concat("characters/gameOverSound.mp3"));
        coinSound = new Sound(this, MEDIA_PATH.concat("consumables/coinSound.mp3"));
        gemSound = new Sound(this, MEDIA_PATH.concat("consumables/gemSound.mp3"));
        healthSound = new Sound(this, MEDIA_PATH.concat("consumables/healthSound.mp3"));
        pushButtonSound = new Sound(this, MEDIA_PATH.concat("buttons/pushButtonSound.mp3"));
        switchButtonSound = new Sound(this, MEDIA_PATH.concat("buttons/pushButtonSound.mp3"));
        liftSound = new Sound(this, MEDIA_PATH.concat("obstacles/liftSound.mp3"));
        lavaSound = new Sound(this, MEDIA_PATH.concat("obstacles/lavaSound.mp3"));
        flagSound = new Sound(this, MEDIA_PATH.concat("obstacles/flagSound.mp3"));
        ghostSound = new Sound(this, MEDIA_PATH.concat("NPCs/ghostSound.mp3"));

        // TODO: Zet achtergrond muziek aan!
//        backgroundSound.loop(-1);
    }

    /**
     * Initialiseert dashboard
     */
    private void initDashboard() {
        Dashboard dashboardPlayerOne = new Dashboard(0, 0, (float) ZOOMWIDTH / 2, 100);
        Dashboard dashboardPlayerTwo = new Dashboard(0, 0, (float) ZOOMWIDTH / 2, 100);

        playerOneDashboardText = new TextObject("Player One", 20);
        playerOneDashboardText.setForeColor(255, 255, 255, 255);

        playerTwoDashboardText = new TextObject("Player Two", 20);
        playerTwoDashboardText.setForeColor(255, 255, 255, 255);

        dashboardPlayerOne.addGameObject(playerOneDashboardText);
        dashboardPlayerTwo.addGameObject(playerTwoDashboardText);

        addDashboard(dashboardPlayerOne, 0, 0);
        addDashboard(dashboardPlayerTwo, ZOOMWIDTH / 2, 0);
    }

    /**
     * Updates dashboard met huidige spelers informatie
     */
    private void updateDashboard() {
        playerOneDashboardText.setText("Player One\n" + "Levens: " + playerOne.getHealth() + "\n" + "Punten: " + playerOne.getPoints());
        playerTwoDashboardText.setText("Player Two\n" + "Levens: " + playerTwo.getHealth() + "\n" + "Punten: " + playerTwo.getPoints());

    }

    /**
     * Initialiseert alle spel objecten
     */
    private void initGameObjects() {

        // Players
        playerOne = new Player(this, 1, gameOverSound);
        addGameObject(playerOne, columnToXCoordinate(1), rowToYCoordinate(16));

        playerTwo = new Player(this, 2, gameOverSound);
        addGameObject(playerTwo, columnToXCoordinate(2), rowToYCoordinate(16));

        // Follow Object
        followObject = new FollowObject(this, playerOne, playerTwo);
        addGameObject(followObject);

        // Consumables
        consumables = new ArrayList<>();

        consumables.add(new Coin(this, coinSound));         // 0
        consumables.add(new Gem(this, gemSound));           // 1
        consumables.add(new Health(this, healthSound));     // 2

        addGameObject(consumables.get(0), columnToXCoordinate(3), rowToYCoordinate(12));
        addGameObject(consumables.get(1), columnToXCoordinate(4), rowToYCoordinate(12));
        addGameObject(consumables.get(2), columnToXCoordinate(5), rowToYCoordinate(12));

        // Buttons
        buttons = new ArrayList<>();

        buttons.add(new PushButton(pushButtonSound));       // 0
        buttons.add(new PushButton(pushButtonSound));       // 1
        buttons.add(new SwitchButton(switchButtonSound));   // 2

        addGameObject(buttons.get(0), columnToXCoordinate(7), rowToYCoordinate(17));
        addGameObject(buttons.get(1), columnToXCoordinate(7), rowToYCoordinate(12));
        addGameObject(buttons.get(2), columnToXCoordinate(2), rowToYCoordinate(12));

        // Obstacles
        obstacles = new ArrayList<>();

        obstacles.add(new Lift(this, liftSound, rowToYCoordinate(18), rowToYCoordinate(13)));     // 0
        obstacles.add(new Lava(lavaSound));                                                             // 1
        obstacles.add(new Lava(lavaSound));                                                             // 2
        obstacles.add(new Lava(lavaSound));                                                             // 3
        obstacles.add(new Flag(this, flagSound));                                                 // 4

        addGameObject((GameObject) obstacles.get(0), columnToXCoordinate(10), rowToYCoordinate(18));
//        addGameObject((GameObject) obstacles.get(1), columnToXCoordinate(4), rowToYCoordinate(18));
//        addGameObject((GameObject) obstacles.get(2), columnToXCoordinate(5), rowToYCoordinate(18));
//        addGameObject((GameObject) obstacles.get(3), columnToXCoordinate(6), rowToYCoordinate(18));
        addGameObject((GameObject) obstacles.get(4), columnToXCoordinate(5), rowToYCoordinate(12));

        // Koppel buttons aan obstacles
        buttons.get(0).addTarget(obstacles.get(0));
        buttons.get(1).addTarget(obstacles.get(0));
        buttons.get(2).addTarget(obstacles.get(4));

        // NPCs
        NPCs = new ArrayList<>();

        NPCs.add(new Ghost(this, ghostSound, columnToXCoordinate(0), columnToXCoordinate(4)));

        addGameObject(NPCs.get(0), columnToXCoordinate(0), rowToYCoordinate(12));
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

        Sprite lavaSprite = new Sprite(MEDIA_PATH.concat("obstacles/lavaSprite.png"));

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
     * Creeert de view een met een Center Following Viewport
     */
    private void createViewWithViewport() {
        CenterFollowingViewport viewPort = new CenterFollowingViewport(followObject, ZOOMWIDTH, ZOOMHEIGHT);
        View view = new View(viewPort, WORLDWIDTH, WORLDHEIGHT);

        setView(view);
        size(ZOOMWIDTH, ZOOMHEIGHT);
        view.setBackground(loadImage(MEDIA_PATH.concat("backgrounds/bg.png")));
    }

    /**
     * Helper functie: Converteer index van column in de tileMap naar daadwerkelijk X Coordinaat (float)
     *
     * @param column Index van column in tileMap waar object geplaatst moet worden.
     * @return X Coordinaat in float
     */
    private float columnToXCoordinate(int column) {
        return column * tileMap.getTileSize();
    }

    /**
     * Helper functie: Converteer index van row in de tileMap naar daadwerkelijk Y Coordinaat (float)
     *
     * @param row Index van row in tileMap waar object geplaatst moet worden.
     * @return Y Coordinaat in float
     */
    private float rowToYCoordinate(int row) {
        return row * tileMap.getTileSize();
    }
}
