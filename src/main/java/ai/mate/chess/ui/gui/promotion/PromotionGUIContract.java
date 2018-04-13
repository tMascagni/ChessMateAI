package ai.mate.chess.ui.gui.promotion;

import ai.mate.chess.model.piece.Piece;

public interface PromotionGUIContract {

    interface Presenter {
        /**
         * Handles promotion after user has selected what piece they want to promote to.
         *
         * @param selectedPiece The selected piece to promote to.
         */
        void handlePromotion(Piece selectedPiece);

        Piece.PlayerColor getTeam();
    }

    interface View {
        void setPresenter(Presenter presenter);

        /**
         * Shows the view
         */
        void showView();

        /**
         * Hides the view
         */
        void hideView();
    }

}