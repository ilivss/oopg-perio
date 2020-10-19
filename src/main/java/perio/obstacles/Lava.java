package perio.obstacles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class Lava extends SpriteObject implements IObstacle, ICollidableWithGameObjects {
    private Sound lavaSound;

    public Lava(Sound lavaSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/lavaSprite.png")));
        this.lavaSound = lavaSound;
    }

    @Override
    public void handleTarget() {
        lavaSound.rewind();
        lavaSound.play();
    }

    @Override
    public void update() {
        // Leeg
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player){
                if (go.getCenterX() > this.getCenterX()) {
                    go.setX(go.getX() - 100);
                } else {
                    go.setX(go.getX() + 100);
                }

                ((Player) go).setHealth(((Player) go).getHealth() - 1);
                handleTarget();
            }
        }
    }



}
