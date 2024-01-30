package EightController;

import javax.swing.JLabel;

import EightTile.EightTile;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.HashMap;
import java.util.Map;

public class EightController extends JLabel implements VetoableChangeListener {

    private EightTile holeTile = null;

    public EightController() {
        setText("START"); // initial message
    }

    public void setHoleTile(EightTile tile) {
        this.holeTile = tile;
    }


    // Register a tile with the controller
    public void registerTile(EightTile tile) {
        tile.addVetoableChangeListener(this);
    }
    

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (!"label".equals(propertyName)){
            return;
        }
        
        int oldLabel = (int) evt.getOldValue();
        int newLabel = (int) evt.getNewValue();


        EightTile tile = (EightTile) evt.getSource();
        int tilePosition = tile.getPosition();
        int currentHolePosition = this.holeTile.getPosition();

        // Check the veto policy
        if (!isValidMove(tilePosition, oldLabel, newLabel, currentHolePosition)) {
            setText("KO");
            throw new PropertyVetoException("Illegal move", evt);
        } else {
            setText("OK");

            // the tile that is currently a hole, changes its label
            this.holeTile.setTileLabel(oldLabel);

            // the tile that has just been clicked become the new hole
            setHoleTile(tile);
        }
    
    }


    private boolean isValidMove(int tilePosition, int oldLabel, int newLabel, int currentHolePosition) {

        if (oldLabel == 9 || !areAdjacent(tilePosition, currentHolePosition)) {
            return false;
        }

        return true;
    }

    /**
     * @param pos1 The position of the first tile.
     * @param pos2 The position of the second tile.
     * @return True if the positions are adjacent, false otherwise.
     */
    private boolean areAdjacent(int position1, int position2) {
        int riga1 = (position1 - 1) / 3;
        int colonna1 = (position1 - 1) % 3;
        int riga2 = (position2 - 1) / 3;
        int colonna2 = (position2 - 1) % 3;

        boolean adjacent = Math.abs(riga1 - riga2) + Math.abs(colonna1 - colonna2) == 1;
        if (adjacent){
            System.out.println("positions " +position1+" and "+position2+" are adjacent");
        } else {
            System.out.println("positions " +position1+" and "+position2+" are not adjacent");
        }
        return adjacent;
    }


    private void restart () {
        // TODO: Implement the logic to restart the game
    }

}
