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

public class EightBoard extends JFrame {

    private List<EightTile> tiles;
    private EightController controller;

    private JButton restartButton;
    private JButton flipButton;

    public EightBoard() {
        controller = new EightController();
    
        initializeTiles();
    
        // Register tiles and controller as listeners for the Restart event
        // Implement the RESTART and FLIP button actions
    





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
    }

    private void initializeTiles() {
        tiles = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            EightTile tile = new EightTile(i, i);
            tiles.add(tile);
        }
        // Shuffle the tiles to create a random configuration
        Collections.shuffle(tiles);

        // Set up the grid layout and add tiles to the board
        for (EightTile tile : tiles) {

            controller.registerTile(tile);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleTileClick((EightTile) e.getSource());
                }
            });
        }
    }





    /**
     * Runs when the RESTART button is clicked.
     * Restarts the game by shuffling the tiles and resetting their labels
     */
    private void restartGame() {
        // TODO: Implement the logic to restart the game

        // shuffle

        // pass the new shuffled value to all the tiles registred to the Restart event


        Collections.shuffle(tiles);
        for (int i = 0; i < 9; i++) {
            tiles.get(i).reset(i + 1);
        }
    }

    
    /**
     * Runs when the FLIP button is clicked.
     * Switches the labels of tiles in position 1 and 2, but only if the hole is in position 9, otherwise it has no effect
     */
    private void flipTiles() {
        // TODO: implement
        // TODO: etting the Controller to check if the flipping is permitted
        // switches the labels of tiles in position 1 and 2, but only if the hole is in position 9, otherwise it has no effect
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
