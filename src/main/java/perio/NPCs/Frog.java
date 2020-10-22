package perio.NPCs;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;
/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Een Frog is een spelobject dat zelfstanding door de wereld beweegt.
 * De beweging is horizontaal: van links naar recht of visa versa.
 */
public class Frog extends NPC {
    /**
     * Constructor
     *
     * @param frogSound         Geluid dat moet klinken wanneer een frog gedood wordt
     * @param leftBoundary      Float die de linkergrens waarin dit object kan bewegen aangeeft.
     * @param rightBoundary     Float die de rechtergrens waarin dit object kan bewegen aangeeft.
     */
    public Frog(Sound frogSound, float leftBoundary, float rightBoundary) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("NPCs/frogSprite1.png")), frogSound, leftBoundary, rightBoundary, 2);
    }

    /**
     * Functie voert eerst handleFight() van NPC klasse uit.
     * Als een object van de Spelerklasse bovenop de frog springt zal deze dood gaan en verdient de speler 10 punten.
     * Als een frog object de speler aanraakt zal de speler 2 levens verliezen.
     *
     * @param player    Speler object die met het NPC object collide.
     */
    @Override
    protected void handleFight(Player player) {
        super.handleFight(player);

        if (player.getY() + player.getHeight() < getCenterY()) {
            // NPC gaat dood en speler verdient 10 punten
            super.kill();
            player.setPoints(player.getPoints() + 10);
        } else {
            // Player verliest twee levens.
            player.setHealth(player.getHealth() - 2);
        }
    }
}
