package perio.collectibles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class Collectible extends SpriteObject implements ICollidableWithGameObjects {

    private PerioWorld world;
    private int playerNo;
    private Sound collectSound;


    public Collectible(PerioWorld world, Sprite sprite, int playerNo, String sound) {
        super(sprite);
        this.world = world;
        this.playerNo = playerNo;
        this.collectSound = new Sound(world, sound);
    }

    @Override
    public void update() {

    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                if (((Player) go).getPlayerNo() == playerNo) {
                    collectSound.rewind();
                    collectSound.play();
                    ((Player) go).collect();
                    world.deleteGameObject(this);
                }
            }
        }
    }
}
