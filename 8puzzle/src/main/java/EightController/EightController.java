package EightController;

import javax.swing.JLabel;

import EightTile.EightTile;
import events.TileChangeEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.HashMap;
import java.util.Map;

public class EightController extends JLabel implements VetoableChangeListener {

     private Map<Integer, Integer> tileInformation; // Map to store information about each tile, since we cannot access tiles' information directly


    public EightController() {
        tileInformation = new HashMap<>();
        setText("START"); // initial message
    }

    // Register a tile with the controller
    public void registerTile(int tilePosition, int tileLabel) {
        tileInformation.put(tilePosition, tileLabel);
    }
    

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if ("label".equals(propertyName)) {
            int oldLabel = (int) evt.getOldValue();
            int newLabel = (int) evt.getNewValue();
            int tilePosition = ((TileChangeEvent) evt).getTilePosition();

            // Check the veto policy
            if (!isValidMove(tilePosition, newLabel)) {
                setText("KO");
                throw new PropertyVetoException("Illegal move", evt);
            } else {
                setText("OK");
            }
        }
    }




    private boolean isValidMove(int tilePosition, int newLabel) {

        Integer currentHoleLabel = tileInformation.get(9);
        if (tilePosition == 9 || !isAdjacent(tilePosition, 9) || newLabel == currentHoleLabel) {
            return false;
        }

        return true;
    }

    private boolean isAdjacent(int position1, int position2) {

        int row1 = (position1 - 1) / 3;
        int col1 = (position1 - 1) % 3;
        int row2 = (position2 - 1) / 3;
        int col2 = (position2 - 1) % 3;

        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }


    private void restart () {
        // TODO: Implement the logic to restart the game
    }

}
