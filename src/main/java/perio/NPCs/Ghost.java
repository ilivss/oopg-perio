package perio.NPCs;

import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;
import perio.Player;

public class Ghost extends NPC {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     * @param leftBoundary
     * @param rightBoundary
     */
    public Ghost(PerioWorld world, float leftBoundary, float rightBoundary) {
        super(world, new Sprite(PerioWorld.MEDIA_PATH.concat("NPCs/ghostSprite.png")), leftBoundary, rightBoundary, 2);
    }

    @Override
    protected void handleFight(Player player) {
        if (player.getY() + player.getHeight() < getCenterY()) {
            // NPC gaat dood
            super.kill();
            player.setPoints(player.getPoints() + 2);
        } else {
            // TODO: Player  gaat gelijk dood! er moet maar 1 leven afgaan!
            // Player verliest een leven.
            player.setHealth(player.getHealth() - 1);
        }
    }
}
