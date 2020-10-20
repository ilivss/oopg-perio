package perio.NPCs;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

public class Frog extends NPC {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     * @param leftBoundary
     * @param rightBoundary
     */
    public Frog(PerioWorld world, Sound ghostSound, float leftBoundary, float rightBoundary) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("NPCs/frogSprite1.png")), world, ghostSound, leftBoundary, rightBoundary, 2);
    }

    @Override
    protected void handleFight(Player player) {
        super.handleFight(player);

        if (player.getY() + player.getHeight() < getCenterY()) {
            // NPC gaat dood
            super.kill();
            player.setPoints(player.getPoints() + 2);
        } else {
            // Player verliest een leven.
            player.setHealth(player.getHealth() - 1);
        }
    }
}
