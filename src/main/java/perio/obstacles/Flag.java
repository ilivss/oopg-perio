package perio.obstacles;

import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;

import java.util.Vector;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * Een Flag is een spelobject dat zich bevindt aan het einde van de speelwereld.
 * Wanneer de spelers allebei bij de Flag zijn kan deze opgehangen worden om het spel te winnen.
 */
public class Flag extends AnimatedSpriteObject implements IObstacle {
    private PerioWorld world;
    private Sound flagSound;
    private boolean up;

    /**
     * Constructor
     *
     * @param world         Referentie naar de wereld.
     * @param flagSound     Geluid dat moet klinken wanneer de Flag opgehangen wordt.
     */
    public Flag(PerioWorld world, Sound flagSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/flagSprite.png")), 2);
        this.world = world;
        this.flagSound = flagSound;
        up = false;

        setCurrentFrameIndex(0);
    }

    /**
     * Checkt of beide Player objecten in het spel op dezelfde hoogte zijn als deze Flag object.
     * Als dat het geval is wordt flagSound afgespeeld en wordt de Flag opgehangen.
     */
    @Override
    public void handleTarget() {
        Vector<GameObject> go = world.getGameObjectItems();
        float averageY = (go.get(0).getY() + go.get(1).getY()) / 2; // Gemiddelde hoogte van beide spelers.

        if (averageY <= getY() + getHeight()) {
            flagSound.rewind();
            flagSound.play();
            up = true;
        }
    }

    @Override
    public void update() {
        if(up) {
            setCurrentFrameIndex(1);
        } else {
            setCurrentFrameIndex(0);
        }
    }
}
