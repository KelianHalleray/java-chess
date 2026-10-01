package org.javachess.service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.enums.PromotingPieceOptions;

public class PromotionService {

    public void promote(Pawn pawn, PromotingPieceOptions promotingPieceOptions, Chessboard chessboard) {

        if (canPromote(pawn, chessboard)) {
                Piece piece = promotingPieceOptions.create(pawn);
                chessboard.remove(pawn);
                chessboard.add(piece);
        }
    }

    public boolean canPromote(Pawn pawn, Chessboard chessboard) {
        int pawnY = pawn.getPosition().getPosition_y();
        int promotionRank = pawn.getColor() == Color.WHITE ? chessboard.getMaxCoordinate() : chessboard.getMinCoordinate();

        return pawnY == promotionRank;
    }

}
