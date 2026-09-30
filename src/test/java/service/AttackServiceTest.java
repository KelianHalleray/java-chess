package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.AttackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackServiceTest {
    private Chessboard chessboard;
    private AttackService attackService;
    private PositionValidator positionValidator;
    private Color whiteColor;
    private Color blackColor;

    @BeforeEach
    void setUp() {
        chessboard = new Chessboard();
        attackService = new AttackService();
        positionValidator = chessboard;
        blackColor = Color.BLACK;
        whiteColor = Color.WHITE;
    }

    @Test
    void shouldReturnTrueWhenKingIsAttacked() {
        // Given
        Position positionKing = new Position(3, 3);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionBishop = new Position(5, 5);
        Piece bishop = new Bishop(positionBishop, positionValidator, blackColor);

        chessboard.add(bishop, king);

        // When
        boolean isAttacked = attackService.isKingAttacked(chessboard, king.getColor());

        // Then
        assertTrue(isAttacked);
    }

    @Test
    void shouldReturnFalseWhenKingIsProtectedByBlockingPiece() {
        // Given
        Position positionKing = new Position(3, 3);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionBishop = new Position(5, 5);
        Piece bishop = new Bishop(positionBishop, positionValidator, blackColor);

        Position positionAllyPawn = new Position(4, 4);
        Piece pawn = new Pawn(positionAllyPawn, positionValidator, whiteColor);

        chessboard.add(king, bishop, pawn);

        // When
        boolean isAttacked = attackService.isKingAttacked(chessboard, king.getColor());

        // Then
        assertFalse(isAttacked);
    }

    @Test
    void shouldReturnTrueWhenKingIsAttackedByPawn() {
        // Given
        Position positionKing = new Position(3, 3);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionPawn = new Position( 4, 4);
        Piece pawn = new Pawn(positionPawn, positionValidator, blackColor);

        chessboard.add(king, pawn);

        // When
        boolean isAttacked = attackService.isKingAttacked(chessboard, king.getColor());

        // Then
        assertTrue(isAttacked);

    }
}
