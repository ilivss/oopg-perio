package perio.NPCs;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NPC extends AnimatedSpriteObject implements ICollidableWithGameObjects {
    private PerioWorld world;
    private Sound NPCSound;
    private float leftBoundary;
    private float rightBoundary;
    private float speed;
    private boolean alive;

    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     * @param sprite The Sprite to be used
     */
    public NPC(Sprite sprite, PerioWorld world, Sound NPCSound, float leftBoundary, float rightBoundary, float speed) {
        super(sprite, 3);
        this.world = world;
        this.NPCSound = NPCSound;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.speed = speed;
        this.alive = true;
    }

    protected void handleFight(Player player) {
        // Player word terug gezet als hij de ghost raakt
        player.setY(player.getY() + 15);

        if (getX() > getPrevX()) {
            player.setX(player.getX() + 50);
        } else {
            player.setX(player.getX() - 50);
        }
    }

    @Override
    public void update() {
        if (super.x <= leftBoundary) {
            setCurrentFrameIndex(1);
            setDirectionSpeed(90, speed);

        } else if (x >= rightBoundary && alive) {
            setCurrentFrameIndex(0);
            setDirectionSpeed(270, speed);
        }
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (alive) {
                if (go instanceof Player) {
                    handleFight((Player) go);
                }
            }
        }
    }

    protected void kill() {
        NPCSound.rewind();
        NPCSound.play();

        alive = false;
        setCurrentFrameIndex(2);
        setSpeed(0);
    }
}
