package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * Een Coin object kan worden 'geconsumeerd' door objecten van de spelerklasse waavan het attribuut playerNo 1 is.
 * Wanneer een speler een object van deze klasse consumeert ontvangt het 1 punt. Dit object zal zichzelf vervolgens verwijderen uit het spel.
 */
public class Coin extends Consumable {
    /**
     * Constructor
     *
     * @param world     Referentie naar de wereld.
     * @param coinSound Geluid dat moet klinken wanneer een Coin object geconsumeerd wordt.
     */
    public Coin(PerioWorld world, Sound coinSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/coinSprite.png")), world, coinSound);
    }

    /**
     * Functie die uitgevoert wordt wanneer een object van de spelersklasse collide met een object van deze klasse.
     * <p>
     *     Speelt coinSound af, keert de speler 1 punt uit en verwijdert zichzelf uit het spel.
     * </p>
     * @param player    Speler die de consumable consumeert.
     */
    @Override
    protected void handleConsume(Player player) {
        if (player.getPlayerNo() == 1) {
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
