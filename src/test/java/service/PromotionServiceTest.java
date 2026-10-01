package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.enums.PromotingPieceOptions;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.PromotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionServiceTest {
    private Chessboard chessboard;
    private PositionValidator positionValidator;
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        chessboard = new Chessboard();
        positionValidator = chessboard;
        promotionService = new PromotionService();
    }

    @Test
    void shouldReturnFalseWhenWhitePawnHasNotReachedLastRank() {
        // Given
        Position positionPawn = new Position(3, 3);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.WHITE);

        chessboard.add(pawn);

        // When
        boolean canPromote = promotionService.canPromote(pawn, chessboard);


        // Then
        assertFalse(canPromote);
    }

    @Test
    void shouldReturnTrueWhenWhitePawnReachesLastRank() {
        // Given
        Position positionPawn = new Position(3, 7);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.WHITE);

        chessboard.add(pawn);

        // When
        boolean canPromote = promotionService.canPromote(pawn, chessboard);


        // Then
        assertTrue(canPromote);
    }


    @Test
    void shouldReturnFalseWhenBlackPawnHasNotReachedLastRank() {
        // Given
        Position positionPawn = new Position(7, 7);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.BLACK);

        chessboard.add(pawn);

        // When
        boolean canPromote = promotionService.canPromote(pawn, chessboard);


        // Then
        assertFalse(canPromote);
    }

    @Test
    void shouldReturnTrueWhenBlackPawnReachesLastRank() {
        // Given
        Position positionPawn = new Position(3, 0);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.BLACK);

        chessboard.add(pawn);

        // When
        boolean canPromote = promotionService.canPromote(pawn, chessboard);


        // Then
        assertTrue(canPromote);
    }

    @Test
    void shouldNotPromotePawnIfCanPromoteIsFalse() {
        // Given
        Position positionPawn = new Position(3, 2);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.WHITE);

        chessboard.add(pawn);

        // When
        promotionService.promote(
                pawn,
                PromotingPieceOptions.BISHOP,
                chessboard
        );

        // Then
        assertTrue(chessboard.exists(pawn));
        assertSame(pawn, chessboard.getPieceFromPosition(positionPawn));
    }

    @Test
    void shouldPromotePawnIfCanPromoteIsTrue() {
        // Given
        Position positionPawn = new Position(3, 7);
        Pawn pawn = new Pawn(positionPawn, positionValidator, Color.WHITE);

        chessboard.add(pawn);

        // When
        promotionService.promote(
                pawn,
                PromotingPieceOptions.BISHOP,
                chessboard
        );

        Piece piece = chessboard.getPieceFromPosition(positionPawn);

        // Then
        assertFalse(chessboard.exists(pawn));
        assertTrue(piece instanceof Bishop);
        assertEquals(Color.WHITE, piece.getColor());
    }

}
