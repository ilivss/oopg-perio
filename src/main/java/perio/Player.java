package perio;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.tiles.FloorTile;
import perio.obstacles.LavaTile;
import processing.core.PVector;

import java.util.List;

public class Player extends AnimatedSpriteObject implements ICollidableWithTiles {

    public enum Direction {
        IDLE,
        UP,
        RIGHT,
        DOWN,
        LEFT,
    }

    private final PerioWorld world;
    private Direction direction;
    private int playerNo;
    private int health;
    private int points;

    private Sound gameOverSound;


    public Player(PerioWorld world, int playerNo, Sound gameOverSound) {
        super(new Sprite(playerNo == 1 ? PerioWorld.MEDIA_PATH.concat("characters/marioSprite.png") : PerioWorld.MEDIA_PATH.concat("characters/peachSprite.png")), 45);

        this.world = world;
        this.playerNo = playerNo;
        this.gameOverSound = gameOverSound;

        this.health = 3;
        this.points = 0;

        setCurrentFrameIndex(0);
        setFriction(0.10f);
        setGravity(0.5f);
    }

    @Override
    public void update() {
        handleDirection();
        handleBoundaries();
    }

    @Override
    public void keyPressed(int keyCode, char key) {
        // Controls Player 1
        if (keyCode == world.UP && playerNo == 1) {
            direction = Direction.UP;
        } else if (keyCode == world.RIGHT && playerNo == 1) {
            direction = Direction.RIGHT;
        } else if (keyCode == world.DOWN && playerNo == 1) {
            setFriction(0.02f);
            direction = Direction.DOWN;
        } else if (keyCode == world.LEFT && playerNo == 1) {
            direction = Direction.LEFT;
        }

        // Controls Player 2
        if (keyCode == 87 && playerNo == 2) {           // W
            direction = Direction.UP;
        }
        if (keyCode == 68 && playerNo == 2) {           // D
            direction = Direction.RIGHT;
        } else if (keyCode == 83 && playerNo == 2) {    // S
            setFriction(0.02f);
            direction = Direction.DOWN;
        } else if (keyCode == 65 && playerNo == 2) {    // A
            direction = Direction.LEFT;
        }
    }

    @Override
    public void keyReleased(int keyCode, char key) {
        direction = Direction.IDLE;
        setFriction(0.10f);
        setCurrentFrameIndex(0);
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;

        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof FloorTile) {
                // Springen alleen mogelijk wanneer speler op een tile is.
                if (direction == Direction.UP) {
                    setDirectionSpeed(0, 20);
                }

                try {
                    vector = world.getTileMap().getTilePixelLocation(ct.getTile());

                    if (ct.getCollisionSide() == CollisionSide.BOTTOM) {
                        setY(vector.y + world.getTileMap().getTileSize());
                    } else {
                        setY(vector.y - getHeight());
                    }
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (ct.getTile() instanceof LavaTile) {
                gameOverSound.rewind();
                gameOverSound.play();

                world.deleteGameObject(this);
            }
        }
    }

    /**
     * Voert handelingen uit aan de hand van de variabele 'direction'
     * <p>
     * Wij hebben het bewegen van de spelers op deze manier geimplementeerd omdat het anders onmogelijk was om twee spelers
     * tegelijk te besturen. OOPG kan namelijk maar een keyCode tegelijk registreren in de methodes keyPressed() en keyReleased().
     * <p>
     * Er kan alleen bewogen worden als de speler nog levens heeft.
     */
    private void handleDirection() {
        if (direction == Direction.UP) {
            jumpAnimation();
            // Springen worden gehandeld in tileCollisionOccurred, omdat het poppetje alleen mag springen als het een tile aanraakt.
        } else if (direction == Direction.RIGHT) {
            walkAnimation();
            setDirectionSpeed(90, 5);
        } else if (direction == Direction.DOWN) {
            crouchAnimation();
            setFriction(0.05f);

        } else if (direction == Direction.LEFT) {
            walkAnimation();
            setDirectionSpeed(270, 5);
        } else if (direction == Direction.IDLE) {
            setCurrentFrameIndex(0);
        }
    }

    /**
     * Zorgt ervoor dat de speler zich nooit buiten de wereld kan begeven.
     * <p>
     * Maakt gebruik van global final variabelen die gedefinieerd zijn in PerioWorld.java
     */
    private void handleBoundaries() {
        if (getX() < 0) {
            setxSpeed(0);
            setX(0);
        }
        if (getY() < 0) {
            setySpeed(0);
            setY(0);
        }
        if (getX() >= PerioWorld.WORLDWIDTH - this.width) {
            setxSpeed(0);
            setX(PerioWorld.WORLDWIDTH - this.width - 1);
        }
        if (getY() >= PerioWorld.WORLDHEIGHT - this.height) {
            setySpeed(0);
            setY(PerioWorld.WORLDHEIGHT - this.height);
        }
    }

    // Animations
    private void walkAnimation() {
        if (direction == Direction.RIGHT) {
            if (getCurrentFrameIndex() < 37 || getCurrentFrameIndex() > 43) {
                setCurrentFrameIndex(37);
            } else if (getCurrentFrameIndex() < 43) {
                nextFrame();
            } else {
                setCurrentFrameIndex(37);
            }
        } else if (direction == Direction.LEFT) {
            // TODO: Fix animatie nu hetzelfde als rechts!
            if (getCurrentFrameIndex() < 37 || getCurrentFrameIndex() > 43) {
                setCurrentFrameIndex(37);
            } else if (getCurrentFrameIndex() < 43) {
                nextFrame();
            } else {
                setCurrentFrameIndex(37);
            }
        }
    }

    private void crouchAnimation() {
        setCurrentFrameIndex(31);
    }

    private void jumpAnimation() {
        setCurrentFrameIndex(36);
    }

    // Getters & Setters
    public int getPlayerNo() {
        return playerNo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (this.health > 0) {
            this.health = health;
        } else {
            gameOverSound.rewind();
            gameOverSound.play();

            world.deleteGameObject(this);
        }
    }
}

