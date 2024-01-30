package EightBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import EightTile.EightTile;
import EightController.EightController;

import events.*;

public class EightBoard extends JFrame {

    private List<EightTile> tiles;
    private EightController controller;

    private JButton restartButton;
    private JButton flipButton;

    public EightBoard() {
        controller = new EightController();
        
    

        // Initializing tiles -----------------------------
        tiles = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            EightTile tile = new EightTile(i, i);
            tiles.add(tile);
            controller.registerTile(tile);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleTileClick((EightTile) e.getSource());
                }
            });
        }





        // BUTTONS INITIALIZATION -------------------------------
        this.restartButton = new JButton("RESTART");
        this.flipButton = new JButton("FLIP");
    
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    
        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipTiles();
            }
        });
    
        setLayout(new BorderLayout());
    
        // Check if tiles are initialized
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalStateException("Tiles are not initialized");
        }

        if (flipButton == null || restartButton == null) {
            throw new IllegalStateException("Buttons are not initialized");
        }

        if (controller == null) {
            throw new IllegalStateException("Controller is not initialized");
        }
    
        // Create a panel for the tiles
        JPanel tilePanel = new JPanel();
        tilePanel.setLayout(new GridLayout(3, 3));
        tilePanel.setBackground(Color.lightGray); // TODO: style
        tilePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        for (EightTile tile : tiles) {
            tilePanel.add(tile);
        }
    
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.restartButton);
        buttonPanel.add(this.controller);
        buttonPanel.add(this.flipButton);
    
        // Add the panels to the frame
        this.add(tilePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);


        // I call restart so that it shuffles the tiles
        restartGame();
    }



    /**
     * Runs when the RESTART button is clicked.
     * Restarts the game by shuffling the tiles and resetting their labels
     */
    private void restartGame() {
        // make a copy of this.tiles so that we can shuffle it without changing this.tiles
        List<EightTile> shuffledTiles = new ArrayList<>(tiles);
        Collections.shuffle(shuffledTiles);
        int label =1;
        for (EightTile tile : shuffledTiles) {
            RestartEvent event = new RestartEvent(label);
            tile.onRestart(event);
            if (label == 9) {
                controller.setHoleTile(tile);
            }
            label++;
        }
        
    }


    
    /**
     * Runs when the FLIP button is clicked.
     * Switches the labels of tiles in position 1 and 2, but only if the hole is in position 9, otherwise it has no effect
     */
    private void flipTiles() {
        EightTile tile1 = tiles.get(0);
        EightTile tile2 = tiles.get(1);

        if (tile1.getPosition() != 1 || tile2.getPosition() != 2) {
            System.err.println("Error. Tiles positions are: " + tile1.getPosition() + " and " + tile2.getPosition());
            return;
        }

        try{
            // ask the controller to check if the hole is at position 9
            if (!controller.isHoleAtPositionNine()) {
                throw new IllegalStateException("Hole is not at position 9.");
            }

            // do the flipping
            int temp = tile1.getTileLabel();
            tile1.setTileLabel(tile2.getTileLabel());
            tile2.setTileLabel(temp);
        } catch(Exception e){
            e.printStackTrace();
            // alert the user
            JOptionPane.showMessageDialog(this, "Flipping is only permitted when the hole is at position 9.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void handleTileClick(EightTile clickedTile) {
        // Handle tile click event
        // You can implement the logic based on the clicked tile
    }


    // entry point for the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EightBoard board = new EightBoard();
            board.setSize(400, 400);
            board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            board.setVisible(true);
        });
    }
}
