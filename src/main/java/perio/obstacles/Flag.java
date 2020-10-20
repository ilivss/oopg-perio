package perio.obstacles;

import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;

import java.util.Vector;

public class Flag extends AnimatedSpriteObject implements IObstacle {
    private PerioWorld world;
    private Sound flagSound;
    private boolean up;

    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     */
    public Flag(PerioWorld world, Sound flagSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/flagSprite.png")), 2);
        this.world = world;
        this.flagSound = flagSound;
        up = false;

        setCurrentFrameIndex(0);
    }

    @Override
    public void update() {

    }

    @Override
    public void handleTarget() {
        Vector<GameObject> tests = world.getGameObjectItems();
        float averageY = (tests.get(0).getY() + tests.get(1).getY()) / 2;


        if (averageY <= getY() + getHeight()) {
            flagSound.rewind();
            flagSound.play();
            up = true;
            setCurrentFrameIndex(1);
        }
    }
}
