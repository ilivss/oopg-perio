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
import perio.NPCs.Frog;
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
import perio.tiles.DecorationTile;
import perio.tiles.FloorTile;
import perio.tiles.LadderTile;

import java.util.ArrayList;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 *  PerioWorld is een object dat... TODO: Documentatie schrijven
 */
public class PerioWorld extends GameEngine {
    /**
     * GameState enum wordt gebruikt om de staat van het spel te regelen.
     * Deze enum wordt gebruikt in verschillende objecten.
     */
    public enum GameState {
        START,
        RUNNING,
        END,
    }

    // Global variabelen
    /**
     * Houdt bij welke state de game momenteel is. Maakt gebruik van enum GameState
     */
    public static GameState gameState;
    /**
     * Deze variabele maakt het makkelijker om te refereren naar media objecten in alle klassen.
     * Gebruik: MEDIA_PATH.concat("path/to/media/file.png")
     */
    public static String MEDIA_PATH = "src/main/java/perio/media/";

    /**
     * Breedte van de speelwereld.
     */
    public static int WORLDWIDTH = 840;

    /**
     * Hoogte van de speelwereld.
     */
    public static int WORLDHEIGHT = 2800;

    /**
     * Breedte van speelscherm
     */
    public static int ZOOMWIDTH = 840;

    /**
     * Hoogte van speelscherm
     */
    public static int ZOOMHEIGHT = 700;

    private IPersistence persistence;
    private int highscore;
    private int timer;
    private int timerout = 1;

    // Dashboard Objects
    private TextObject playerOneDashboardText;
    private TextObject playerTwoDashboardText;
    private TextObject startGameDashboardText;
    private TextObject endGameDashboardText;
    private Dashboard dashboardStartGame;
    private Dashboard dashboardEndGame;
    private Dashboard dashboardPlayerOne;
    private Dashboard dashboardPlayerTwo;

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
    private Sound frogSound;

    public static void main(String[] args) {
        PerioWorld pw = new PerioWorld();
        pw.runSketch();
    }

    private void timer() {
        int endtime = 120;
        timer += 1;
        timerout = endtime - (timer / 60);
    }

    @Override
    public void setupGame() {
        // Spel initialiseren
        gameState = GameState.START;

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

        // Start timer wanneer spel start.
        if (gameState == GameState.RUNNING) {
            timer();
        }

        // TODO: Stop game wanneer 1 vd 2 spelers dood gaat!
        if (playerOne.getHealth() <= 0 || playerTwo.getHealth() <= 0) {
            // stop game
        }
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
        frogSound = new Sound(this, MEDIA_PATH.concat("NPCs/frogSound.mp3"));

        // Achtergrond muziek
        backgroundSound.loop(-1);
    }

    /**
     * Initialiseert dashboard
     */
    private void initDashboard() {
        dashboardStartGame = new Dashboard(0, 0, (float) ZOOMWIDTH, ZOOMHEIGHT);
        dashboardPlayerOne = new Dashboard(0, 0, (float) ZOOMWIDTH / 2, 120);
        dashboardPlayerTwo = new Dashboard(0, 0, (float) ZOOMWIDTH / 2, 120);

        TextObject startGameDashboardTexttitle = new TextObject("Welkom bij perioworld", 25);
        startGameDashboardTexttitle.setForeColor(255, 255, 255, 255);

        startGameDashboardText = new TextObject("Spel uitleg", 15);
        startGameDashboardText.setText("zorg dat je binnen de tijd het kasteel verlaat en haal de vlag op \nzorg dat allebei de spelers het eind van het level haalt anders ben je af!\n\n\n Druk op R om het spel te starten");        startGameDashboardText.setForeColor(255, 255, 255, 255);
        dashboardStartGame.setBackground(15, 175, 41);

        playerOneDashboardText = new TextObject("Player One", 20);
        playerOneDashboardText.setForeColor(255, 255, 255, 255);

        playerTwoDashboardText = new TextObject("Player Two", 20);
        playerTwoDashboardText.setForeColor(255, 255, 255, 255);

        dashboardStartGame.addGameObject(startGameDashboardTexttitle, 250, 200);
        dashboardStartGame.addGameObject(startGameDashboardText, 180, 250);
        dashboardPlayerOne.addGameObject(playerOneDashboardText);
        dashboardPlayerTwo.addGameObject(playerTwoDashboardText);


        addDashboard(dashboardStartGame, 0, 0);
        addDashboard(dashboardPlayerOne, 0, 0);
        addDashboard(dashboardPlayerTwo, ZOOMWIDTH / 2, 0);
    }

    /**
     * Updates dashboard met huidige spelers informatie
     */
    private void updateDashboard() {
        playerOneDashboardText.setText("Player One\n" + "Levens: " + playerOne.getHealth() + "\n" + "Punten: " + playerOne.getPoints() + "\n" + "Timer: " + timerout);
        playerTwoDashboardText.setText("Player Two\n" + "Levens: " + playerTwo.getHealth() + "\n" + "Punten: " + playerTwo.getPoints());

        if (gameState == GameState.RUNNING) {
            deleteDashboard(dashboardStartGame);

        }

        // laat eindscherm zien wanneer spelers af zijn
        if (playerOne.getHealth() == 0 || playerTwo.getHealth() == 0 || timerout <= 0) {
            Dashboard dashboardEndGame = new Dashboard(0, 0, (float) ZOOMWIDTH, ZOOMHEIGHT);

            endGameDashboardText = new TextObject("Game over", 20);
            endGameDashboardText.setText("Game over\n" + "Player one Punten: "+ playerOne.getPoints() + "\n" + "Player two Punten: " + playerTwo.getPoints());
            endGameDashboardText.setForeColor(255, 255, 255, 255);

            dashboardEndGame.addGameObject(endGameDashboardText, 300, 250);
            dashboardEndGame.setBackground(174, 126,126);

            addDashboard(dashboardEndGame, 0, 0);
        }
    }

    /**
     * Initialiseert alle spel objecten
     */
    private void initGameObjects() {

        // Players
        playerOne = new Player(this, 1, gameOverSound);
        addGameObject(playerOne, columnToXCoordinate(0), rowToYCoordinate(37));

        playerTwo = new Player(this, 2, gameOverSound);
        addGameObject(playerTwo, columnToXCoordinate(1), rowToYCoordinate(37));

        // Follow Object
        followObject = new FollowObject(playerOne, playerTwo);
        addGameObject(followObject);

        // Consumables
        consumables = new ArrayList<>();
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Coin(this, coinSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Gem(this, gemSound));
        consumables.add(new Health(this, healthSound));
        consumables.add(new Health(this, healthSound));
        consumables.add(new Health(this, healthSound));
        consumables.add(new Health(this, healthSound));

        addGameObject(consumables.get(0), columnToXCoordinate(4), rowToYCoordinate(37));
        addGameObject(consumables.get(1), columnToXCoordinate(6), rowToYCoordinate(36));
        addGameObject(consumables.get(2), columnToXCoordinate(8), rowToYCoordinate(37));
        addGameObject(consumables.get(3), columnToXCoordinate(2), rowToYCoordinate(29));
        addGameObject(consumables.get(4), columnToXCoordinate(5), rowToYCoordinate(27));
        addGameObject(consumables.get(5), columnToXCoordinate(10), rowToYCoordinate(25));
        addGameObject(consumables.get(6), columnToXCoordinate(3), rowToYCoordinate(16));
        addGameObject(consumables.get(7), columnToXCoordinate(1), rowToYCoordinate(11));
        addGameObject(consumables.get(8), columnToXCoordinate(0), rowToYCoordinate(12));
        addGameObject(consumables.get(9), columnToXCoordinate(1), rowToYCoordinate(13));
        addGameObject(consumables.get(10), columnToXCoordinate(5), rowToYCoordinate(36));
        addGameObject(consumables.get(11), columnToXCoordinate(7), rowToYCoordinate(36));
        addGameObject(consumables.get(12), columnToXCoordinate(10), rowToYCoordinate(37));
        addGameObject(consumables.get(13), columnToXCoordinate(1), rowToYCoordinate(30));
        addGameObject(consumables.get(14), columnToXCoordinate(6), rowToYCoordinate(27));
        addGameObject(consumables.get(15), columnToXCoordinate(11), rowToYCoordinate(25));
        addGameObject(consumables.get(16), columnToXCoordinate(7), rowToYCoordinate(16));
        addGameObject(consumables.get(17), columnToXCoordinate(0), rowToYCoordinate(11));
        addGameObject(consumables.get(18), columnToXCoordinate(1), rowToYCoordinate(12));
        addGameObject(consumables.get(19), columnToXCoordinate(0), rowToYCoordinate(13));
        addGameObject(consumables.get(20), columnToXCoordinate(3), rowToYCoordinate(28));
        addGameObject(consumables.get(21), columnToXCoordinate(0), rowToYCoordinate(23));
        addGameObject(consumables.get(22), columnToXCoordinate(11), rowToYCoordinate(16));
        addGameObject(consumables.get(23), columnToXCoordinate(0), rowToYCoordinate(0));



        // Buttons
        buttons = new ArrayList<>();
        buttons.add(new PushButton(pushButtonSound));       // 0 - Lift button
        buttons.add(new PushButton(pushButtonSound));       // 1 - Lift button
        buttons.add(new SwitchButton(switchButtonSound));   // 2 - Lift switch
        buttons.add(new SwitchButton(switchButtonSound));   // 3 - Lift switch
        buttons.add(new SwitchButton(switchButtonSound));   // 4 - Flag switch

        addGameObject(buttons.get(0), columnToXCoordinate(8), rowToYCoordinate(28));
        addGameObject(buttons.get(1), columnToXCoordinate(8), rowToYCoordinate(23));
        addGameObject(buttons.get(2), columnToXCoordinate(9), rowToYCoordinate(16));
        addGameObject(buttons.get(3), columnToXCoordinate(3), rowToYCoordinate(11));
        addGameObject(buttons.get(4), columnToXCoordinate(9), rowToYCoordinate(11));

        // Obstacles
        obstacles = new ArrayList<>();
        obstacles.add(new Lava(lavaSound));                                                             // 0
        obstacles.add(new Lava(lavaSound));                                                             // 1
        obstacles.add(new Lava(lavaSound));                                                             // 2
        obstacles.add(new Lift(this, liftSound, rowToYCoordinate(28), rowToYCoordinate(24)));     // 3
        obstacles.add(new Lift(this, liftSound, rowToYCoordinate(16), rowToYCoordinate(12)));     // 4
        obstacles.add(new Flag(this, flagSound));                                                 // 5

        addGameObject((GameObject) obstacles.get(0), columnToXCoordinate(5), rowToYCoordinate(38));
        addGameObject((GameObject) obstacles.get(1), columnToXCoordinate(6), rowToYCoordinate(38));
        addGameObject((GameObject) obstacles.get(2), columnToXCoordinate(7), rowToYCoordinate(38));
        addGameObject((GameObject) obstacles.get(3), columnToXCoordinate(10), rowToYCoordinate(28));
        addGameObject((GameObject) obstacles.get(4), columnToXCoordinate(0), rowToYCoordinate(16));
        addGameObject((GameObject) obstacles.get(5), columnToXCoordinate(6), rowToYCoordinate(11));

        // Koppel buttons aan obstacles
        buttons.get(0).addTarget(obstacles.get(3));
        buttons.get(1).addTarget(obstacles.get(3));
        buttons.get(2).addTarget(obstacles.get(4));
        buttons.get(3).addTarget(obstacles.get(4));
        buttons.get(4).addTarget(obstacles.get(5));

        // NPCs
        NPCs = new ArrayList<>();
        NPCs.add(new Ghost(ghostSound, columnToXCoordinate(5), columnToXCoordinate(9)));     // 0
        NPCs.add(new Ghost(ghostSound, columnToXCoordinate(4), columnToXCoordinate(7)));     // 1
        NPCs.add(new Ghost(ghostSound, columnToXCoordinate(0), columnToXCoordinate(6)));     // 2
        NPCs.add(new Frog(frogSound, columnToXCoordinate(5), columnToXCoordinate(11)));      // 3

        addGameObject(NPCs.get(0), columnToXCoordinate(9), rowToYCoordinate(32));
        addGameObject(NPCs.get(1), columnToXCoordinate(4), rowToYCoordinate(28));
        addGameObject(NPCs.get(2), columnToXCoordinate(0), rowToYCoordinate(23));
        addGameObject(NPCs.get(3), columnToXCoordinate(11), rowToYCoordinate(11));
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
        Sprite doorBottomSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/doorLock.png"));
        Sprite doorTopSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/doorTop.png"));
        Sprite torchSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/tochLit.png"));
        Sprite ladderBottomSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/ladder_mid.png"));
        Sprite ladderTopSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/ladder_top.png"));
        Sprite windowBottomSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/windowHighLeadlightBottom.png"));
        Sprite windowMidSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/windowHighLeadlightMid.png"));
        Sprite windowTopSprite = new Sprite(MEDIA_PATH.concat("tiles/castle/windowHighLeadlightTop.png"));

        Sprite grassLeftSprite = new Sprite(MEDIA_PATH.concat("tiles/outside/grassLeft.png"));
        Sprite grassMidSprite = new Sprite(MEDIA_PATH.concat("tiles/outside/grassMid.png"));
        Sprite grassRightSprite = new Sprite(MEDIA_PATH.concat("tiles/outside/grassRight.png"));
        Sprite grassCenterSprite = new Sprite(MEDIA_PATH.concat("tiles/outside/grassCenter.png"));

        TileType<FloorTile> castleLeftTile = new TileType<>(FloorTile.class, castleLeftSprite);
        TileType<FloorTile> castleMidTile = new TileType<>(FloorTile.class, castleMidSprite);
        TileType<FloorTile> castleRightTile = new TileType<>(FloorTile.class, castleRightSprite);
        TileType<FloorTile> castleCenterTile = new TileType<>(FloorTile.class, castleCenterSprite);
        TileType<DecorationTile> doorBottomTile = new TileType<>(DecorationTile.class, doorBottomSprite);
        TileType<DecorationTile> doorTopTile = new TileType<>(DecorationTile.class, doorTopSprite);
        TileType<DecorationTile> torchTile = new TileType<>(DecorationTile.class, torchSprite);
        TileType<LadderTile> ladderBottomTile = new TileType<>(LadderTile.class, ladderBottomSprite);
        TileType<LadderTile> ladderTopTile = new TileType<>(LadderTile.class, ladderTopSprite);
        TileType<DecorationTile> windowBottomTile = new TileType<>(DecorationTile.class, windowBottomSprite);
        TileType<DecorationTile> windowMidTile = new TileType<>(DecorationTile.class, windowMidSprite);
        TileType<DecorationTile> windowTopTile = new TileType<>(DecorationTile.class, windowTopSprite);

        TileType<FloorTile> grassLeftTile = new TileType<>(FloorTile.class, grassLeftSprite);
        TileType<FloorTile> grassMidTile = new TileType<>(FloorTile.class, grassMidSprite);
        TileType<FloorTile> grassRightTile = new TileType<>(FloorTile.class, grassRightSprite);
        TileType<FloorTile> grassCenterTile = new TileType<>(FloorTile.class, grassCenterSprite);


        int tileSize = 70;
        TileType[] tileTypes = {
                castleCenterTile,   // 0
                castleLeftTile,     // 1
                castleMidTile,      // 2
                castleRightTile,    // 3
                doorBottomTile,     // 4
                doorTopTile,        // 5
                torchTile,          // 6
                ladderBottomTile,   // 7
                ladderTopTile,      // 8
                windowBottomTile,   // 9
                windowMidTile,      // 10
                windowTopTile,      // 11
                grassCenterTile,   // 12
                grassLeftTile,     // 13
                grassMidTile,      // 14
                grassRightTile,    // 15

        };
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
                {-1, -1, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 8, -1, -1, -1, -1, -1, -1},
                {14, 14, 14, 15, -1, 7, -1, 13, 14, 14, 14, 14},
                {-1, -1, -1, -1, 6, 7, 6, -1, -1, -1, -1, -1},
                {-1, 11, -1, -1, -1, 7, -1, -1, -1, -1, 11, -1},

                {6, 10, 6, -1, 6, 7, 6, -1, -1, 6, 10, 6},
                {-1, 9, -1, -1, -1, 7, -1, -1, -1, -1, 9, -1},
                {-1, -1, -1, -1, 6, 7, 6, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, -1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 3, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, 11, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1},
                {-1, 9, -1, 6, -1, 9, -1, 6, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, 1, 2, 2, 2, 2, 2, 2, 2, 2},

                {-1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, 6, -1, 6, -1, 6, -1, 6, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 3, -1, 7},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7},
                {5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7},
                {4, -1, 6, -1, 6, -1, 6, -1, 6, -1, -1, 7},
                {4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7},
                {2, 2, 2, 2, 3, -1, -1, -1, 1, 2, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    /**
     * Initialiseert opslag van speldata
     * Elke startup van het spel zal eerst proberen eerdere data te recoveren.
     */
    private void initPersistence() {
        persistence = new FilePersistence("main/java/perio/media/highscore.txt");

        if (persistence.fileExists()) {
            highscore = Integer.parseInt(persistence.loadDataString());
        } else {
            highscore = 0;
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
     * Getter functie voor highscore variabele
     * @return Huidige highscore als integer.
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     * Setter functie voor highscore variabele. Controleert eerst of de meegegeven highscore groter is dan de huidige highscore.
     * @param   score   Integer met de score als variabele.
     */
    public void setHighscore(int score) {
        if (score > this.highscore){
            this.highscore = score;
            persistence.saveData(Integer.toString(highscore));
        }
    }

    /**
     * Helper functie: Converteer index van column in de tileMap naar daadwerkelijk X Coordinaat (float)
     *
     * @param   column    Index van column in tileMap waar object geplaatst moet worden.
     * @return  X Coordinaat als float
     */
    private float columnToXCoordinate(int column) {
        return column * tileMap.getTileSize();
    }

    /**
     * Helper functie: Converteer index van row in de tileMap naar daadwerkelijk Y Coordinaat (float)
     *
     * @param   row       Index van row in tileMap waar object geplaatst moet worden.
     * @return  Y Coordinaat als float
     */
    private float rowToYCoordinate(int row) {
        return row * tileMap.getTileSize();
    }
}
