package ai.mate.chess.ui.gui.board;


import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;

public interface BoardGUIContract {
    interface Presenter {
        /**
         * This will start to load the initial board. Called by the view.
         */
        void start();

        /**
         * This will load the initial board.
         */
        void loadBoard();

        /**
         * Pauses the game
         */
        void pause();

        /**
         * Unpauses the game
         */
        void unpause();

        /**
         * This will handle clicking a tile.
         * @param tileClicked   tile that was clicked.
         */
        void handleClickedTile(Tile tileClicked);
    }

    interface View {
        /**
         * This attaches the presenter to the view.
         * Called by the presenter, once it's done initializing.
         * @param presenter     presenter to attach.
         */
        void setPresenter(Presenter presenter);

        /**
         * This will set the board to display.
         * @param board         board to display.
         */
        void setBoard(Board board);

        /**
         * Sets up the JFrame and shows the view.
         */
        void showView();

        /**
         * Updates and redraws the board.
         * @param board         new board.
         */
        void updateBoard(Board board);

        /**
         * Enables or disables the view
         * @param enabled       whether to enable or disable the view
         */
        void setEnabled(boolean enabled);

        /**
         * Show dark overlay over the view (To draw attention to other views, considered this paused)
         */
        void showOverlay();

        /**
         * Hides dark overlay over the view
         */
        void hideOverlay();
    }
}
