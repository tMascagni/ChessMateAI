package ai.mate.chess.model.board;

import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.*;
import ai.mate.chess.model.player.Player;

import java.awt.*;
import java.util.List;

import static ai.mate.chess.model.piece.Piece.PieceType.KING;
import static ai.mate.chess.model.piece.Piece.PieceType.PAWN;

public final class Board {

    private static final int BOARD_SIZE = 8;

    private Tile[][] board;

    private Player white, black;

    public Board(Player white, Player black) {
        this.white = white;
        this.black = black;
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        initBoardPieces();
        initPlayerPieces(white, black);
    }

    public Board(Board board) {
        this.board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++)
                this.board[row][col] = board.getBoard()[row][col].copy();
    }

    private void initBoardPieces() {
        // REAL CHESS BOARD
        board = new Tile[][]{
                {new Tile(new Rook(Piece.PlayerColor.BLACK, new Point(0, 0))), new Tile(new Knight(Piece.PlayerColor.BLACK, new Point(1, 0))), new Tile(new Bishop(Piece.PlayerColor.BLACK, new Point(2, 0))), new Tile(new Queen(Piece.PlayerColor.BLACK, new Point(3, 0))), new Tile(new King(Piece.PlayerColor.BLACK, new Point(4, 0))), new Tile(new Bishop(Piece.PlayerColor.BLACK, new Point(5, 0))), new Tile(new Knight(Piece.PlayerColor.BLACK, new Point(6, 0))), new Tile(new Rook(Piece.PlayerColor.BLACK, new Point(7, 0)))},
                {new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(0, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(1, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(2, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(3, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(4, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(5, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(6, 1))), new Tile(new Pawn(Piece.PlayerColor.BLACK, new Point(7, 1)))},
                {new Tile(new Point(0, 2)), new Tile(new Point(1, 2)), new Tile(new Point(2, 2)), new Tile(new Point(3, 2)), new Tile(new Point(4, 2)), new Tile(new Point(5, 2)), new Tile(new Point(6, 2)), new Tile(new Point(7, 2))},
                {new Tile(new Point(0, 3)), new Tile(new Point(1, 3)), new Tile(new Point(2, 3)), new Tile(new Point(3, 3)), new Tile(new Point(4, 3)), new Tile(new Point(5, 3)), new Tile(new Point(6, 3)), new Tile(new Point(7, 3))},
                {new Tile(new Point(0, 4)), new Tile(new Point(1, 4)), new Tile(new Point(2, 4)), new Tile(new Point(3, 4)), new Tile(new Point(4, 4)), new Tile(new Point(5, 4)), new Tile(new Point(6, 4)), new Tile(new Point(7, 4))},
                {new Tile(new Point(0, 5)), new Tile(new Point(1, 5)), new Tile(new Point(2, 5)), new Tile(new Point(3, 5)), new Tile(new Point(4, 5)), new Tile(new Point(5, 5)), new Tile(new Point(6, 5)), new Tile(new Point(7, 5))},
                {new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(0, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(1, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(2, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(3, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(4, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(5, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(6, 6))), new Tile(new Pawn(Piece.PlayerColor.WHITE, new Point(7, 6)))},
                {new Tile(new Rook(Piece.PlayerColor.WHITE, new Point(0, 7))), new Tile(new Knight(Piece.PlayerColor.WHITE, new Point(1, 7))), new Tile(new Bishop(Piece.PlayerColor.WHITE, new Point(2, 7))), new Tile(new Queen(Piece.PlayerColor.WHITE, new Point(3, 7))), new Tile(new King(Piece.PlayerColor.WHITE, new Point(4, 7))), new Tile(new Bishop(Piece.PlayerColor.WHITE, new Point(5, 7))), new Tile(new Knight(Piece.PlayerColor.WHITE, new Point(6, 7))), new Tile(new Rook(Piece.PlayerColor.WHITE, new Point(7, 7)))},
        };
    }

    private void initPlayerPieces(Player white, Player black) {
        int[] rows = {0, 1, 6, 7};

        for (int row : rows) {
            for (int i = 0; i < 8; i++) {
                Piece piece = board[row][i].getPiece();
                if (piece != null) {
                    if (piece.getPlayerColor() == Piece.PlayerColor.BLACK)
                        black.addPiece(piece);
                    else
                        white.addPiece(piece);
                }
            }
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(Point point) {
        return board[point.y][point.x];
    }

    public void setTile(Point position, Tile tile) {
        this.board[position.y][position.x] = tile;
    }

    public void unhighlightBoard() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j].getTileHighlight() != Tile.TileHighlight.NONE)
                    board[i][j].setTileHighlight(Tile.TileHighlight.NONE);
    }

    public void handleMove(Move move) {
        Tile start = getTile(move.getFrom());
        Tile end = getTile(move.getTo());
        Piece pieceToMove = start.getPiece();
        pieceToMove.updatePiece(move);
        start.setPiece(null);
        end.setPiece(pieceToMove);
    }

    public boolean isValidPosition(Point position) {
        return (position.x >= 0 && position.x <= 7 && position.y >= 0 && position.y <= 7);
    }

    public Tile getTile(int x, int y) {
        return board[y][x];
    }

    public void clearTile(Point start) {
        getTile(start).setPiece(null);
    }

    public boolean tileIsThreatened(Piece.PlayerColor playerColor, Tile tile) {
        return tileAtPointIsThreatened(playerColor, tile.getPosition());
    }

    public boolean tileAtPointIsThreatened(Piece.PlayerColor goodPlayerColor, Point tilePos) {
        if (tilePos == null) return false;

        int threatenedRow = tilePos.x;
        int threatenedCol = tilePos.y;

        int[] rowDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int direction = 0; direction < 8; direction++) {
            int row = threatenedRow;
            int col = threatenedCol;
            int rowIncrement = rowDirections[direction];
            int colIncrement = colDirections[direction];

            for (int step = 0; step < 8; step++) {
                row = row + rowIncrement;
                col = col + colIncrement;

                if (isOutOfBounds(row, col)) {
                    break;

                } else {
                    Tile t = getTile(row, col);
                    if (!t.isEmpty()) {
                        Piece piece = t.getPiece();
                        if (piece.getPlayerColor() != goodPlayerColor) {
                            if (piece instanceof Knight) {
                                // Handle knights differently. Just compute the moves and check if the tile is there
                                List<Move> moves = piece.getAvailableMoves(this);
                                for (Move move : moves) {
                                    if (move.getMoveType() == Move.MoveType.ATTACK) {
                                        Piece potentialKing = getTile(move.getTo()).getPiece();
                                        if (potentialKing.getPieceType() == KING && potentialKing.getPlayerColor() == goodPlayerColor) {
                                            System.out.println("Knight is able to attack " + goodPlayerColor + "'s KING!");
                                            return true;
                                        }
                                    }
                                }
                            } else if (step > 0 && (piece.getPieceType() != PAWN && piece.getPieceType() != KING)) {
                                if (piece.getPositionThreats()[direction])
                                    return true;
                            } else {
                                if (step == 0)
                                    if (piece.getPositionThreats()[direction])
                                        return true;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row > 7 || col < 0 || col > 7;
    }

}