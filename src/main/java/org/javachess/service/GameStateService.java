package org.javachess.service;

import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.enums.Color;

public class GameStateService {
    private final MoveService moveService = new MoveService();
    private final AttackService attackService = new AttackService();

    public boolean isCheckmate(Chessboard chessboard, Color color) {
        return attackService.isKingAttacked(chessboard, color) && hasNoLegalMoveForColor(chessboard, color);
    }

    public boolean isStalemate(Chessboard chessboard, Color color) {
        return !attackService.isKingAttacked(chessboard, color) && hasNoLegalMoveForColor(chessboard, color);
    }

    private boolean hasNoLegalMoveForColor(Chessboard chessboard, Color color) {
        for (Piece piece : chessboard.getPieces()) {
            if (color == piece.getColor()) {
                for (Position position : piece.getMovePattern()) {
                    if (moveService.checkMovement(piece, chessboard, position)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
