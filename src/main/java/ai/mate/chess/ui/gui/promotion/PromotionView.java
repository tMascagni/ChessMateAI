package ai.mate.chess.ui.gui.promotion;

import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.*;
import ai.mate.chess.ui.gui.GUIUtils;
import ai.mate.chess.ui.gui.component.TilePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PromotionView extends MouseAdapter implements PromotionGUIContract.View {

    private final JFrame frame;
    private final Piece.PlayerColor playerColor;
    private JPanel panel;
    private PromotionGUIContract.Presenter presenter;
    private TilePanel[] selectableTiles;

    public PromotionView(Piece.PlayerColor playerColor) {
        this.frame = new JFrame("Choose a piece to promote to");
        this.panel = new JPanel(new GridLayout(1, 3));
        this.playerColor = playerColor;
        setupView();
    }

    @Override
    public void setPresenter(PromotionGUIContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showView() {
        frame.setVisible(true);
    }

    @Override
    public void hideView() {
        frame.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Tile tileSelected = GUIUtils.getRelevantTile(selectableTiles, e);
        presenter.handlePromotion(tileSelected.getPiece());
    }

    private void setupView() {
        this.selectableTiles = new TilePanel[]{
                new TilePanel(new Tile(new Queen(playerColor, new Point(0, 0)))),
                new TilePanel(new Tile(new Rook(playerColor, new Point(0, 1)))),
                new TilePanel(new Tile(new Bishop(playerColor, new Point(0, 0)))),
                new TilePanel(new Tile(new Knight(playerColor, new Point(0, 1))))
        };

        for (TilePanel tilePanel : selectableTiles) {
            tilePanel.addMouseListener(this);
            panel.add(tilePanel);
        }

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(300, 100));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
    }

}