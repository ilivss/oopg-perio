package perio.NPCs;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Een Ghost is een spelobject dat zelfstanding door de wereld beweegt.
 * De beweging is horizontaal: van links naar recht of visa versa.
 */
public class Ghost extends NPC {

    /**
     * Constructor
     *
     * @param ghostSound        Geluid dat moet klinken wanneer een ghost gedood wordt
     * @param leftBoundary      Float die de linkergrens waarin dit object kan bewegen aangeeft.
     * @param rightBoundary     Float die de rechtergrens waarin dit object kan bewegen aangeeft.
     */
    public Ghost(Sound ghostSound, float leftBoundary, float rightBoundary) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("NPCs/ghostSprite.png")), ghostSound, leftBoundary, rightBoundary, 2);
    }

    /**
     * Functie voert eerst handleFight() van NPC klasse uit.
     * Als een object van de Spelerklasse bovenop de ghost springt zal deze dood gaan en verdient de speler 2 punten.
     * Als een ghost object de speler aanraakt zal de speler 1 leven verliezen.
     *
     * @param player    Speler object die met het NPC object collide.
     */
    @Override
    protected void handleFight(Player player) {
        super.handleFight(player);

        if (player.getY() + player.getHeight() < getCenterY()) {
            // NPC gaat dood
            super.kill();
            player.setPoints(player.getPoints() + 2);
        } else {
            // Player verliest een leven.
            player.setX(player.getX() + 100);
            player.setHealth(player.getHealth() - 1);
        }
    }
}
