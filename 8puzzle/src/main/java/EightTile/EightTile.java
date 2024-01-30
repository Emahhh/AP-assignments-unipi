package EightTile;

import javax.swing.JButton;

import events.TileChangeEvent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.beans.PropertyVetoException;

public class EightTile extends JButton implements ActionListener {

    private final int position;
    private int tileLabel; //TODO: both bound and costrained
    private final VetoableChangeSupport vetos = new VetoableChangeSupport(this); // so that the controller can veto changes to the label


    public EightTile(int position, int label) {
        this.position = position;
        this.tileLabel = label;
        try {
            setTileLabel(label); // to update appearance
        } catch (Exception e) {
            // TODO: handle exception
        }
        addActionListener(this); // Register as a listener for button click events
    }


    @Override
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetos.addVetoableChangeListener(listener);
    }

    @Override
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetos.removeVetoableChangeListener(listener);
    }


    private void setTileLabel(int newLabel){
        
        int oldLabel = tileLabel;
        tileLabel = newLabel;

        if (newLabel == 9) {
            setBackground(Color.GRAY);  // Current hole
            setText("");
            return;
        } else if (position == newLabel) {
            setBackground(Color.GREEN);
            setText(Integer.toString(newLabel));
        } else {
            setBackground(Color.YELLOW);
            setText(Integer.toString(newLabel));
        }
    }

    /**
     * Sets the new label value and updates the appearance.
     * To be used when resetting the board. 
     * @param newLab new label value to be assigned to the tile
     */
    public void reset(int newLab){
        setTileLabel(newLab);
    }


    /**
     * Handles tile click event. The tile is going to be changed so that its label is set to 9 (current hole).
     * The controller is going to be notified of the change, and it can veto the change if not valid.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int oldLabel = getTileLabel();
        int newLabel = 9;

        System.out.println("oldLabel: " + oldLabel);
        System.out.println("newLabel: " + newLabel);

        if (newLabel == oldLabel) {
            return;
        }

        try {
            TileChangeEvent tileChangeEvent = new TileChangeEvent(this, "label", oldLabel, newLabel, position);
            vetos.fireVetoableChange(tileChangeEvent);

            setTileLabel(9);
            // TODO: pass, in some way, its previous label value to the current hole
        } catch (PropertyVetoException e1) {
            // TODO: flash color to red for half a second
        }
    }


    public int getPosition() {
        return this.position;
    }

    public int getTileLabel() {
        return this.tileLabel;
    }

    //TODO: reset

}
