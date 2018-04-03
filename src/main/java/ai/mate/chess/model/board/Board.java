package ai.mate.chess.model.board;

import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Knight;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.model.player.Player;

import java.awt.*;

public final class Board {

    public final static int BOARD_SIZE = 8;

    private Tile[][] board;

    public Board(Player white, Player black) {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
    }

    private void initBoardTiles() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                board[row][col] = new Tile(new Point(row, col));
    }

    private void initBoardPieces() {
        /* First row: Black */
        // Do this when pieces are created
    }

    private void initPlayerPieces(Player white, Player black) {
        int[] rows = {0, 1, 6, 7};

        for (int row : rows)
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col].getPiece();
                if (piece != null) {
                    if (piece.getColor() == Piece.PlayerColor.BLACK)
                        black.addPiece(piece);
                    else
                        white.addPiece(piece);
                }
            }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(Point position) {
        /*
         * x and y coordinates are switched, so
         * we can achieve real coordinates.
         */
        return board[position.y][position.x];
    }

    public void setTile(Point position, Tile tile) {
        board[position.y][position.x] = tile;
    }

    public void handleMove(Move move) {
        Tile start = getTile(move.getStart());
        Tile end = getTile(move.getEnd());

        Piece pieceToMove = start.getPiece();
        pieceToMove.move(move);

        start.setPiece(null);
        end.setPiece(pieceToMove);
    }

    public boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x <= 7 && position.y >= 0 && position.y <= 7;
    }

    public Tile getTile(int col, int row) {
        return board[row][col];
    }

    public void clearTile(Point start) {
        getTile(start).setPiece(null);
    }

    public boolean isTileThreatened(Piece.PlayerColor color, Tile tile) {
        return isTileAtPointThreatened(color, tile.getPosition());
    }

    public boolean isTileAtPointThreatened(Piece.PlayerColor goodColor, Point tilePosition) {
        int threatenedRow = tilePosition.x;
        int threatenedCol = tilePosition.y;

        int[] rowDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int direction = 0; direction < BOARD_SIZE; direction++) {
            int row = threatenedRow;
            int col = threatenedCol;

            int rowIncrement = rowDirections[direction];
            int colIncrement = colDirections[direction];

            for (int step = 0; step < BOARD_SIZE; step++) {
                row += rowIncrement;
                col += colIncrement;

                if (isOutOfBounds(row, col)) {
                    break;
                } else {
                    Tile tile = getTile(row, col);

                    if (!tile.isEmpty()) {
                        Piece piece = tile.getPiece();
                        if (piece.getColor() != goodColor) {
                            if (Piece instanceof Knight) {
                                // Handle knights differently, just compute the moves and chec if the tile is there
                                List<Move> availableMoves = piece.getAvailableMoves(this);

                                for (Move move : availableMoves) {
                                    if (move.getType() == Move.MoveType.ATTACK) {
                                        Piece potentialKing = getTile(move.getEnd()).getPiece();
                                        if (potentialKing.getPieceType() == PieceType.KING &&
                                                potentialKing.getColor() == goodColor) {
                                            System.out.println("Knight can attack your King!");
                                            return true;
                                        }
                                    }
                                }
                            } else if (step > 0 && (piece.getType() != PieceType.PAWN && piece.getType() != PieceType.KING)) {
                                if (piece.getPositionThreats()[direction]) {
                                    return true;
                                } else {
                                    if (step == 0) {
                                        if (piece.getPositionThreats()[direction]) {
                                            return true;
                                        }
                                    }
                                }
                            }
                            break;
                        }
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