package perio;

import nl.han.ica.oopg.objects.GameObject;
import processing.core.PGraphics;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 *  Een FollowObject is een object dat wordt gevolgd door de CenterFollowingViewPort.
 *  FollowObject volgt zelf allebei de Player objecten van het spel.
 *  Dit object calculeert de gemiddelde y positie van beide Player objecten en houdt
 *  rekening met de grenzen van de speelwereld.
 */
public class FollowObject extends GameObject {
    private final Player playerOne;
    private final Player playerTwo;

    /**
     * Constructor
     *
     * @param playerOne     Player 1
     * @param playerTwo     Player 2
     */
    public FollowObject(Player playerOne, Player playerTwo) {
        super();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        // Zet X Coordinaat van dit object.
        setX((float) PerioWorld.ZOOMWIDTH / 2);

        // Zet Y Coordinaat van dit object.
        setY((playerOne.getY() + playerTwo.getY()) / 2 );
    }

    @Override
    public void update() {
        // Dit stukje code zorgt ervoor dat de view port binnen de grenzen van de speelwereld blijft.
        if ((playerOne.getY() + playerTwo.getY())/ 2 < (float) PerioWorld.ZOOMHEIGHT / 2 ){
            setY((float) PerioWorld.ZOOMHEIGHT / 2);
        } else if ((playerOne.getY() + playerTwo.getY())/ 2  > PerioWorld.WORLDHEIGHT - ((float) PerioWorld.ZOOMHEIGHT / 2)) {
            setY(PerioWorld.WORLDHEIGHT - ((float) PerioWorld.ZOOMHEIGHT / 2));
        } else {
            setY((playerOne.getY() + playerTwo.getY())/ 2);
        }
    }

    @Override
    public void draw(PGraphics g) {
        // Leeg
    }
}
