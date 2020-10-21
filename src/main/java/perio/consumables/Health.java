package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * Een Health object kan worden 'geconsumeerd' door objecten van de spelerklasse.
 * Wanneer een speler een object van deze klasse consumeert ontvangt het 1 leven. Dit object zal zichzelf vervolgens verwijderen uit het spel.
 */
public class Health extends Consumable {
    /**
     * Constructor
     *
     * @param world         Referentie naar de wereld.
     * @param healthSound   Geluid dat moet klinken wanneer een Health object geconsumeerd wordt.
     */
    public Health(PerioWorld world, Sound healthSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/healthSprite.png")), world, healthSound);
    }

    /**
     * Functie die uitgevoert wordt wanneer een object van de spelersklasse collide met een object van deze klasse.
     * <p>
     *     Speelt healthSound af, geeft de speler 1 levenspunt en verwijdert zichzelf uit het spel.
     * </p>
     * @param player    Speler die de consumable consumeert.
     */
    @Override
    protected void handleConsume(Player player) {
        // Speel geluid af
        super.consumableSound.rewind();
        super.consumableSound.play();

        // Speler krijgt 1 health erbij.
        player.setHealth(player.getHealth() + 1);

        // Verwijder deze consumable uit de wereld
        super.world.deleteGameObject(this);
    }
}
