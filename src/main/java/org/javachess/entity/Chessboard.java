package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Chessboard implements PositionValidator {

    private static final int BOARD_SIZE = 8;
    private final List<Piece> pieces = new ArrayList<>();

    public void add(Piece... pieces) {
        this.pieces.addAll(List.of(pieces));
    }

    public void remove(Piece piece) {
        this.pieces.remove(piece);
    }

    public Piece getPieceFromPosition(Position position) {

        for (Piece piece : pieces) {
            if (piece.getPosition().equals(position)) {
                return piece;
            }
        }

        return null;
    }

    public boolean exists(Piece piece) {
        return pieces.contains(piece);
    }

    @Override
    public boolean isValidPosition(Position position) {
        int x = position.getPosition_x();
        int y = position.getPosition_y();

        boolean isXValid = 0 <= x && BOARD_SIZE > x;
        boolean isYValid = 0 <= y && BOARD_SIZE > y;

        return isXValid && isYValid;
    }
    
    public King findKing(Color pieceColor) {
        
        for (Piece piece : pieces) {
            if (piece instanceof King k) {
                if (k.getColor() == pieceColor) {
                    return k;
                }
            }
        }

        return null;
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(pieces);
    }

}
