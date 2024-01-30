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

    /**
     * Map to store information about each tile, since we cannot access tiles' information directly
     * Maps tileLabel to tilePosition.
     */ 
    private Map<Integer, Integer> tileInformation; 
    

    public EightController() {
        tileInformation = new HashMap<>();
        setText("START"); // initial message
    }

    /**
     * Given a label, this method returns the position of the tile with that label.
     */
    private int getTilePosition(int tileLabel) {
        return tileInformation.get(tileLabel);
    }


    // Register a tile with the controller
    public void registerTile(int tilePosition, int tileLabel) {
        tileInformation.put(tilePosition, tileLabel);
    }
    

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (!"label".equals(propertyName)){
            return;
        }
        
        int oldLabel = (int) evt.getOldValue();
        int newLabel = (int) evt.getNewValue();
        int tilePosition = getTilePosition(oldLabel);
        int currentHolePosition = getTilePosition(9);

        // Check the veto policy
        if (!isValidMove(tilePosition, oldLabel, newLabel, currentHolePosition)) {
            setText("KO");
            throw new PropertyVetoException("Illegal move", evt);
        } else {
            setText("OK");
        }
    
    }


    private boolean isValidMove(int tilePosition, int oldLabel, int newLabel, int currentHolePosition) {

        // print for debug
        System.out.println("tilePosition: " + tilePosition);
        System.out.println("oldLabel: " + oldLabel);
        System.out.println("newLabel: " + newLabel);
        System.out.println("currentHolePosition: " + currentHolePosition);

        if (oldLabel == 9 || !isAdjacent(tilePosition, currentHolePosition)) {
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
