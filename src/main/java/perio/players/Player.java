package perio.players;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;
import perio.tiles.FloorTile;
import processing.core.PVector;

import java.util.List;

public class Player extends AnimatedSpriteObject implements ICollidableWithTiles {

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
    }

    private final PerioWorld world;
    private String name;
    Direction dirrr;

    public Player(PerioWorld world) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("characters/mario.png")), 45);
        this.world = world;

        setCurrentFrameIndex(0);
        setFriction(0.10f);
        setGravity(0.3f);
    }

    @Override
    public void update() {
        if (getX() > getPrevX()) {
            dirrr = Direction.RIGHT;
        } else {
            dirrr = Direction.LEFT;
        }
    }

    @Override
    public void keyPressed(int keyCode, char key) {
        int playerSpeed = 5;

        if (keyCode == world.RIGHT && this.x + this.width < world.width) {
            setDirectionSpeed(90, playerSpeed);
            walkingAnimation(Direction.RIGHT);
        } else if (keyCode == world.DOWN) {
            setFriction(0.02f);
            crouchAnimation();
        } else if (keyCode == world.LEFT && this.x > 0) {
            setDirectionSpeed(270, playerSpeed);
            walkingAnimation(Direction.RIGHT);
        } else if (key == ' ') {
            System.out.println("prevY:" + this.getPrevY());
            System.out.println("Y: " + this.getY());
        }
    }


    @Override
    public void keyReleased(int keyCode, char key) {

        if (keyCode == world.UP) {
            // Wat is beter? Gewoon x aanroepen of getX()?
            if (getY() == getPrevY()) {
                setDirectionSpeed(0, 20);
            }
            
        } else if (keyCode == world.DOWN) {
            setFriction(0.10f);
        }

        setCurrentFrameIndex(0);

        // Reset Sprite
//        if (keyCode == world.RIGHT || keyCode == world.LEFT) {
//            setCurrentFrameIndex(0);
//        }
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
    private void walkingAnimation(Direction direction) {
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
        if (dirrr == Direction.RIGHT) {
            setCurrentFrameIndex(31);
        } else if (dirrr == Direction.LEFT) {
            setCurrentFrameIndex(31);
        }
    }
}
