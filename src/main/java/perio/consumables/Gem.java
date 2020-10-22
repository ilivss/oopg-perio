package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Een Gem object kan worden 'geconsumeerd' door objecten van de spelerklasse waavan het attribuut playerNo 2 is.
 * Wanneer een speler een object van deze klasse consumeert ontvangt het 1 punt. Dit object zal zichzelf vervolgens verwijderen uit het spel.
 */
public class Gem extends Consumable {
    /**
     * Constructor
     *
     * @param world     Referentie naar de wereld.
     * @param gemSound  Geluid dat moet klinken wanneer een Gem object geconsumeerd wordt.
     */
    public Gem(PerioWorld world, Sound gemSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/gemSprite.png")), world, gemSound);
    }

    /**
     * Functie die uitgevoert wordt wanneer een object van de spelersklasse collide met een object van deze klasse.
     * <p>
     *     Speelt gemSound af, keert de speler 1 punt uit en verwijdert zichzelf uit het spel.
     * </p>
     * @param player    Speler die de consumable consumeert.
     */
    @Override
    protected void handleConsume(Player player) {
        if (player.getPlayerNo() == 2) {
            // Speel geluid af
            super.consumableSound.rewind();
            super.consumableSound.play();

            // Laat speler deze consumable verzamelen
            player.setPoints(player.getPoints() + 1);

            // Verwijder deze consumable uit de wereld
            super.world.deleteGameObject(this);
        }
    }
}
