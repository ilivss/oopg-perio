package perio.tiles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;
import perio.PerioWorld;

import java.util.List;

public class SwitchTile extends Tile implements ICollidableWithGameObjects {

    private boolean value;

    /**
     * @param sprite The image which will be drawn whenever the draw method of the tile is called.
     */
    public SwitchTile(Sprite sprite) {
        super(sprite);
        value = false;
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject cgo : collidedGameObjects) {
            setSprite(new Sprite(PerioWorld.MEDIA_PATH.concat("tiles/switch/laserSwitchYellowOn.png")));
            System.out.println("else!");

        }
    }

}
