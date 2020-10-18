package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

public class Coin extends Consumable {

    public Coin(PerioWorld world, Sound coinSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/coinSprite.png")), world, coinSound);
    }

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
