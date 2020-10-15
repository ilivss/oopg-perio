package perio;

import nl.han.ica.oopg.collision.CollidedTile;
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
    private String name;
    private int player;
    private Direction direction;

    public Player(PerioWorld world, int player) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("characters/mario.png")), 45);
        this.world = world;
        this.name = "Mario";
        this.player = player;

        setCurrentFrameIndex(0);
        setFriction(0.10f);
        setGravity(0.3f);
    }

    @Override
    public void update() {
        // Update horizontal direction for animation methods
        if (direction == Direction.UP) {
            if (Math.floor(getY()) == Math.floor(getPrevY()+2)) {
                jumpAnimation();
                setDirectionSpeed(0, 20);
            }
        } else if (direction == Direction.RIGHT) {
            walkAnimation();
            setDirectionSpeed(90, 5);
        } else if (direction == Direction.DOWN) {
//            crouchAnimation();
//            setFriction(0.05f);
            System.out.println(Math.floor(getY()) + " " + Math.floor(getPrevY() ));

        } else if (direction == Direction.LEFT) {
            walkAnimation();
            setDirectionSpeed(270, 5);
        }

//        // Boundaries
        if (getX() < 0) {
            setxSpeed(0);
            setX(0);
        }
        if (getY() < 0) {
            setySpeed(0);
            setY(0);
        }
        if (getX() > world.width - this.width) {
            setxSpeed(0);
            setX(world.width - this.width);
        }
        if (getY() > world.getView().getWorldHeight() - this.height) {
            // Moet world.getView().getWorldHeight() gebruiken ipv world.height
            // omdat world.height de hoogte van de view port is en niet de van de wereld
            setySpeed(0);
            setY(world.getView().getWorldHeight() - this.height);
        }
    }

    @Override
    public void keyPressed(int keyCode, char key) {
        if (this.player == 1) {
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
        } else if (this.player == 2) {
            // Controls Player 2
            if (keyCode == 87) { // W
                direction = Direction.UP;
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
        setCurrentFrameIndex(37);
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;
        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof FloorTile) {
                try {
                    vector = world.getTileMap().getTilePixelLocation(ct.getTile());
                    setY(vector.y - getHeight());
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            }
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
        setCurrentFrameIndex(44);
    }
}
