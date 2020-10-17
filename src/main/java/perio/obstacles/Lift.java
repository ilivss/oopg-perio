package perio.obstacles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.userinput.IKeyInput;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class Lift extends SpriteObject implements ICollidableWithGameObjects, IObstacle {
    private PerioWorld world;
    private Sound liftSound;
    private float maxHeight;

    /**
     * Create a new SpriteObject with a Sprite object.
     */
    public Lift(PerioWorld world, float maxHeight, Sound liftSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/liftSprite.png")));
        this.world = world;
        this.liftSound = liftSound;
        this.maxHeight = maxHeight;
        setGravity(0.5f);
    }

    @Override
    public void update() {
        if (getY() < maxHeight) {
            setSpeed(0);
            setY(maxHeight);
        } else if (getY() > PerioWorld.WORLDHEIGHT - 3 * world.getTileMap().getTileSize()) {
            setY(PerioWorld.WORLDHEIGHT - 210);
        }
    }

    @Override
    public void handleTarget() {
        // TODO Fix geluid, hij wordt pas afgespeeld wanneer player van de knop afgaat, terwijl hij eigenlijk afgespeeld  moet worden wanneer hij erop staat
        liftSound.rewind();
        liftSound.play();
        setDirectionSpeed(0, 5);

    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                go.setY(this.y - go.getHeight());

            }
        }
    }
}
