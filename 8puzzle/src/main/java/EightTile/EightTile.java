package EightTile;

import javax.swing.JButton;
import javax.swing.Timer;

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

    // Colors
    private final Color holeColor = Color.lightGray;
    private final Color wrongPositionColor = Color.YELLOW;
    private final Color rightPositionColor = Color.GREEN;
    private final Color blinkColor = Color.RED;
    


    public EightTile(int position, int label) {
        this.position = position;
        setTileLabel(label); // to update appearance
        addActionListener(this); // Register as a listener for button click events

        setOpaque(true);
        setFont(new java.awt.Font("Monospaced", 1, 40));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMargin(new java.awt.Insets(0, 0, 0, 0));
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


    public void setTileLabel(int newLabel) {
        if (newLabel < 1 || newLabel > 9) {
            throw new IllegalArgumentException("Tile label must be between 1 and 9");
        }

        System.out.println("Setting label " + newLabel + " for tile " + position);

        this.tileLabel = newLabel;

        if (newLabel == 9) { // Current hole
            setBackground(holeColor);
            setText("");
            return;
        } else if (position == newLabel) {
            setBackground(rightPositionColor);
            setText(Integer.toString(newLabel));
        } else {
            setBackground(wrongPositionColor);
            setText(Integer.toString(newLabel));
        }

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
        } catch (PropertyVetoException e1) {
            // I change temporarly the color of the tile
            setBackground(blinkColor);
            
            // after half a second, revert the color to its right one
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // by doing this, the setter changes the color to its right one
                    // this solution avoids error due to the user swapping tiles before the timer ends
                    setTileLabel(getTileLabel()); 
                }
            });
            timer.setRepeats(false);
            timer.start();

        }
    }


    public int getPosition() {
        return this.position;
    }

    public int getTileLabel() {
        return this.tileLabel;
    }


}
