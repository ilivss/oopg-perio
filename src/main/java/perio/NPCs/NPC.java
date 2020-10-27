package perio.NPCs;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 * <p>
 * Een NPC is een spelobject dat zelfstanding door de wereld beweegt.
 * De beweging is horizontaal: van links naar recht of visa versa.
 */
public class NPC extends AnimatedSpriteObject implements ICollidableWithGameObjects {
    private Sound NPCSound;
    private float leftBoundary;
    private float rightBoundary;
    private float speed;
    private boolean isAlive;

    /**
     * Constructor
     *
     * @param sprite        Sprite die gebruikt moet worden
     * @param NPCSound      Geluid dat moet klinken wanneer een NPC object 'gedood' wordt.
     * @param leftBoundary  Float die de linkergrens waarin dit object kan bewegen aangeeft.
     * @param rightBoundary Float die de rechtergrens waarin dit object kan bewegen aangeeft.
     * @param speed         Snelheid waarmee de NPC zich voorbeweegt.
     */
    public NPC(Sprite sprite, Sound NPCSound, float leftBoundary, float rightBoundary, float speed) {
        super(sprite, 3);
        this.NPCSound = NPCSound;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.speed = speed;
        this.isAlive = true;
    }

    /**
     * Speler object wordt afhankelijk van de beweeg richting van de NPC links of rechts verschoven.
     * Deze functie wordt opgeroepen wanneer een Speler object collide met een object van deze klasse.
     *
     * @param player Speler die met het NPC object collide.
     */
    protected void handleFight(Player player) {
        // Player word terug gezet als hij de ghost raakt
        player.setY(player.getY() + 15);

        if (getX() > getPrevX()) {
            player.setX(player.getX() + 50);
        } else {
            player.setX(player.getX() - 50);
        }
    }

    /**
     * Speelt NPCSound af, verandert sprite en stopt het bewegen van de NPC met andere woorden: deze functie 'dood' deze NPC.
     */
    protected void kill() {
        NPCSound.rewind();
        NPCSound.play();

        isAlive = false;
        setCurrentFrameIndex(2);
        setSpeed(0);
    }
    
    @Override
    public void update() {
        if (super.x <= leftBoundary && isAlive) {
            setCurrentFrameIndex(1);
            setDirectionSpeed(90, speed);

        } else if (x >= rightBoundary && isAlive) {
            setCurrentFrameIndex(0);
            setDirectionSpeed(270, speed);
        }
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (isAlive) {
                if (go instanceof Player) {
                    handleFight((Player) go);
                }
            }
        }
    }
}
