package ai.mate.chess.ui.gui.board;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.ui.gui.GUIUtils;
import ai.mate.chess.ui.gui.component.TilePanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// See interface (BoardGUIContract) for comments

public class BoardView extends MouseAdapter implements BoardGUIContract.View  {

    private final Dimension WINDOW_DIMENSIONS = new Dimension(650, 650);
    private final JFrame frame;
    private JPanel tilePanels;
    private JPanel overlay;

    private BoardGUIContract.Presenter presenter;

    private final TilePanel[][] tiles;
    private final GUIUtils utils;

    public BoardView() {
        this.frame = new JFrame("ChessMateAI - v2.0");
        this.tilePanels = new JPanel(new GridLayout(8, 8));

        this.overlay = new JPanel() {
            // Override because of transparency issue with Swing
            // @see https://tips4java.wordpress.com/2009/05/31/backgrounds-with-transparency/
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };

        overlay.setPreferredSize(WINDOW_DIMENSIONS);
        overlay.setOpaque(false);

        frame.setGlassPane(overlay);
        this.tiles = new TilePanel[8][8];
        utils = new GUIUtils();
    }

    @Override
    public void setPresenter(BoardGUIContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.start();
    }

    @Override
    public void showView() {
        frame.getContentPane().add(tilePanels);
        frame.setPreferredSize(WINDOW_DIMENSIONS);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void updateBoard(Board newBoard) {
        frame.getContentPane().removeAll();
        tilePanels = new JPanel(new GridLayout(8, 8));
        setBoard(newBoard);
        frame.getContentPane().add(tilePanels);
        frame.pack();
    }

    @Override
    public void setEnabled(boolean enabled) {
        frame.setEnabled(enabled);
    }

    @Override
    public void showOverlay() {
        frame.getGlassPane().setVisible(true);
    }

    @Override
    public void hideOverlay() {
        frame.getGlassPane().setVisible(false);
    }

    @Override
    public void setBoard(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tile = board.getTile(new Point(j, i));
                tiles[i][j] = new TilePanel(tile);
                tiles[i][j].addMouseListener(this);
                tilePanels.add(tiles[i][j]);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Tile tileClicked = utils.getRelevantTile(tiles, e);
        presenter.handleClickedTile(tileClicked);
    }

}