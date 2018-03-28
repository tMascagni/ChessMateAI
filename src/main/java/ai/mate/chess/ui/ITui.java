package ai.mate.chess.ui;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.interfaces.IPiece;

public interface ITui {
    /* Input */
    char getUserInput();
    char getPromotionSelection();
    BoardPosition getBoardPositionInput(String msg);
    IPiece.Color getPlayerColorInput();

    /* Print */
    String getPossibleMoveCountText(IPiece[][] board);
    String getLossPieceText(IPiece[] whiteLossList, IPiece[] blackLossList, IPiece.Color color, int index);
    String getPieceCountText(int whitePieceCount, int blackPieceCount, IPiece.Color playerColor);
    String getLatestMoveText(IPiece.Color playerColor, BoardPosition from, BoardPosition to);
    void printPressEnter();
    void printArrow();
    void printArrow(String postfix);
    void printPromotionSuccess(char selection, IPiece.Color playerColor);
    void printStartScreen();
    void printMenu();
    void printPromotion();
    void printHumanPlayer(IPiece.Color playerColor);
    void printAIPlayer(IPiece.Color aIColor);
    void printCheck(IPiece.Color playerColor);
    void printChoosePlayer();
    void printIllegalAction(String msg);
    void printUnrecognizedCommand();
    void printWin();
    void printLoss();
    void printExit();
    void printBoard(Board board);
}