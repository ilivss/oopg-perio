package perio;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.Sprite;
import perio.tiles.FloorTile;
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
    private int playerNo;
    private Direction direction;
    private int collected;


    public Player(PerioWorld world, int playerNo) {
        super(new Sprite(playerNo == 1 ? PerioWorld.MEDIA_PATH.concat("characters/marioSprite.png") : PerioWorld.MEDIA_PATH.concat("characters/peachSprite.png")), 45);
        this.world = world;
        this.playerNo = playerNo;
        this.collected = 0;

        setCurrentFrameIndex(0);
        setFriction(0.10f);
        setGravity(0.5f);
    }

    @Override
    public void update() {
        float offset = 10f;

        // Update horizontal direction for animation methods
        if (direction == Direction.UP) {
            jumpAnimation();

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

//      Boundaries
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

    @Override
    public void keyPressed(int keyCode, char key) {
        if (this.playerNo == 1) {
            // Controls Player 1
            if (keyCode == world.UP) {
                direction = Direction.UP;
            } else if (keyCode == world.RIGHT) {
                direction = Direction.RIGHT;
            } else if (keyCode == world.DOWN) {
                setFriction(0.02f);
                direction = Direction.DOWN;
            } else if (keyCode == world.LEFT) {
                direction = Direction.LEFT;
            }
        } else if (this.playerNo == 2) {
            // Controls Player 2
            if (keyCode == 87) { // W
                if (Math.floor(getY()) == Math.floor(getPrevY())) {
                    direction = Direction.UP;
                }
            }
            if (keyCode == 68) { // D
                direction = Direction.RIGHT;
            } else if (keyCode == 83) { // S
                setFriction(0.02f);
                direction = Direction.DOWN;
            } else if (keyCode == 65) { // A
                direction = Direction.LEFT;
            }
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

                    if (ct.getCollisionSide() == CollisionSide.BOTTOM){
                        setY(vector.y + world.getTileMap().getTileSize());
                    } else {
                        setY(vector.y - getHeight());
                    }
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Collect
    public void collect() {
        collected++;
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
}

