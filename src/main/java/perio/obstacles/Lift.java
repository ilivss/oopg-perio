package perio.obstacles;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import perio.PerioWorld;
import perio.tiles.FloorTile;
import processing.core.PVector;

import java.util.List;

public class Lift extends SpriteObject implements ICollidableWithTiles, ITarget {
    private PerioWorld world;
    private float maxHeight;

    /**
     * Create a new SpriteObject with a Sprite object.
     */
    public Lift(PerioWorld world, float maxHeight) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/liftSprite.png")));
        this.world = world;
        this.maxHeight = maxHeight;
        setGravity(0.5f);
    }

    @Override
    public void update() {
        if (getY() < maxHeight) {
            setSpeed(0);
            setY(maxHeight);
        }
    }

    @Override
    public void handleTarget() {
        setDirectionSpeed(0, 5);
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
}
