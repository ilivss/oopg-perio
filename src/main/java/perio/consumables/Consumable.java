package perio.consumables;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Abstracte klasse die een consumable (verbruiksartikel) representeert.
 * Objecten die deze klasse implementeren kunnen worden geconsumeerd door objecten van de spelerklasse.
 */
public abstract class Consumable extends SpriteObject implements ICollidableWithGameObjects {
    protected PerioWorld world;
    protected Sound consumableSound;

    /**
     * Constructor
     *
     * @param sprite            Sprite die gebruikt moet worden
     * @param world             Referentie naar de wereld
     * @param consumableSound   Geluid die moet klinken wanneer een consumable geconsumeerd wordt
     */
    public Consumable(Sprite sprite, PerioWorld world, Sound consumableSound) {
        super(sprite);
        this.world = world;
        this.consumableSound = consumableSound;
    }

    /**
     * Abstracte functie die het consumeren van de consumable uitvoert.
     * Deze functie wordt uitgevoerd wanneer een Spelers object collide met een object van deze klasse.
     *
     * @param player    Speler die de consumable consumeert.
     */
    protected abstract void handleConsume(Player player);

    @Override
    public void update() {
        // Leeg
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                handleConsume((Player) go);
            }
        }
    }
}
