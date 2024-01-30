package EightTile;

import javax.swing.JButton;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.beans.PropertyVetoException;

import events.*;

public class EightTile extends JButton implements ActionListener, RestartEventListener {
    
    private final int position;
    private int tileLabel; //TODO: both bound and costrained
    private final VetoableChangeSupport vetos = new VetoableChangeSupport(this); // so that the controller can veto changes to the label


    public EightTile(int position, int label) {
        this.position = position;
        setTileLabel(label); // to update appearance
        addActionListener(this); // Register as a listener for button click events
    }

    @Override
    public void onRestart(RestartEvent event) {
        setTileLabel(event.getNewLabel());
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

        if (newLabel < 1 || newLabel > 9) {
            throw new IllegalArgumentException("Tile label must be between 1 and 9");
        }

        System.out.println("Setting label " + newLabel + " for tile " + position);
    
        this.tileLabel = newLabel;

        if (newLabel == 9) { // Current hole
            setBackground(Color.GRAY);  
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

        if (newLabel == oldLabel) {
            return;
        }

        try {
            vetos.fireVetoableChange("label", oldLabel, newLabel);

            setTileLabel(9);
            // TODO: pass, in some way, oldLabel to the currentHole
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


}
