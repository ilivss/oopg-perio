package perio.obstacles;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * Obstakel interface om het implementeren van obstakels te vergemakkelijken. Objecten die deze interface implementeren kunnen
 * aan objecten van de Buttonklasse worden toegevoegd.
 */
public interface IObstacle {

    /**
     * Schakelfunctie die opgeroepen wordt in een Button object.
     */
    void handleTarget();
}
