package perio.consumables;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public abstract class Consumable extends SpriteObject implements ICollidableWithGameObjects {

    protected PerioWorld world;
    protected Sound consumableSound;


    public Consumable(Sprite sprite, PerioWorld world, Sound consumableSound) {
        super(sprite);
        this.world = world;
        this.consumableSound = consumableSound;
    }

    protected abstract void handleConsume(Player player);

    @Override
    public void update() {
        // Leeg
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                handleConsume((Player) go);
            }
        }
    }
}
