package org.javachess.service;

import org.javachess.entity.Chessboard;
import org.javachess.entity.King;
import org.javachess.entity.Position;
import org.javachess.entity.Rook;
import org.javachess.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class CastlingService {
    private final MoveService moveService = new MoveService();

    public void castle(King king, Rook rook, Chessboard chessboard) {
        Direction direction = getCastleDirectionFromKing(king, rook);
        int kingX = king.getPosition().getPosition_x();
        int kingY = king.getPosition().getPosition_y();
        int directionX = direction.getDeltaX();

        if (canCastle(king, rook, chessboard)) {
            int newKingX = kingX + 2 * directionX;
            int newRookX = kingX + 1 * directionX;
            king.setPosition(new Position(newKingX, kingY));
            rook.setPosition(new Position(newRookX, kingY));
        }


    }

    public boolean canCastle(King king, Rook rook, Chessboard chessboard) {

        Direction direction = getCastleDirectionFromKing(king, rook);
        boolean isKingSafeDuringCastle = checkKingIsSafeDuringCastle(direction, king, chessboard);


        for (Position position : getPositionsBetween(king, rook)) {
            if (chessboard.getPieceFromPosition(position) != null) {
                return false;
            }
        }

        return rookHasNotAlreadyMoved(rook) && kingHasNotAlreadyMoved(king) && isKingSafeDuringCastle;
    }


    private boolean rookHasNotAlreadyMoved(Rook rook) {
        return !rook.hasMoved();
    }

    private boolean kingHasNotAlreadyMoved(King king) {
        return !king.hasMoved();
    }

    private boolean checkKingIsSafeDuringCastle(Direction direction, King king, Chessboard chessboard) {
        if (direction != null) {
            int kingX = king.getPosition().getPosition_x();
            int kingCastleMoveDistance = 2;

            if (moveService.isKingAttacked(chessboard, king.getColor())) {
                return false;
            }


            for (int step = 0; step < kingCastleMoveDistance; step++) {
                kingX += direction.getDeltaX();

                Position kingAfterCastle = new Position(
                        kingX,
                        king.getPosition().getPosition_y()
                );

                if (!moveService.checkKingSafeRules(
                        king,
                        chessboard,
                        kingAfterCastle
                )) {
                    return false;
                }

            }
        }

        return true;
    }

    private Direction getCastleDirectionFromKing(King king , Rook rook) {
        if (king.getPosition().getPosition_x() < rook.getPosition().getPosition_x()) {
            return Direction.RIGHT;
        }

        return Direction.LEFT;
    }

    private List<Position> getPositionsBetween(King king, Rook rook) {
        List<Position> positionsBetween = new ArrayList<>();
        int kingX = king.getPosition().getPosition_x();
        int rookX = rook.getPosition().getPosition_x();
        int kingY = king.getPosition().getPosition_y();

        int startX = Math.min(kingX, rookX) + 1;
        int endX = Math.max(kingX, rookX);

        for (int x = startX; x < endX; x++) {
            positionsBetween.add(new Position(x, kingY));
        }

        return positionsBetween;

    }
}
