package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.CastlingService;
import org.javachess.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CastlingServiceTest {
    private PositionValidator positionValidator;
    private Chessboard chessboard;
    private MoveService moveService;
    private CastlingService castlingService;

    @BeforeEach
    void setUp() {
        moveService = new MoveService();
        chessboard = new Chessboard();
        castlingService = new CastlingService();
        positionValidator = chessboard;
    }

    @Test
    void shouldReturnFalseWhenCastlingAfterMovingKingSinceStart() {
        // Given
        Position startingPositionKing = new Position(4, 0);
        King king = new King(startingPositionKing, positionValidator, Color.WHITE);
        Position kingNextPosition = new Position(4, 1);


        Position startingPositionRook = new Position(7, 0);
        Rook rook = new Rook(startingPositionRook, positionValidator, Color.WHITE);

        chessboard.add(king, rook);

        // When
        moveService.makeMovement(king, chessboard, kingNextPosition);
        boolean canCastle = castlingService.canCastle(king, rook, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnTrueWhenCastlingAfterNoMovingKingSinceStart() {
        // Given
        Position startingPositionKing = new Position(4, 0);
        King king = new King(startingPositionKing, positionValidator, Color.WHITE);

        Position startingPositionRook = new Position(7, 0);
        Rook rook = new Rook(startingPositionRook, positionValidator, Color.WHITE);

        chessboard.add(king, rook);

        // When
        boolean canCastle = castlingService.canCastle(king, rook, chessboard);

        // Then
        assertTrue(canCastle);
    }

    @Test
    void shouldReturnFalseIfTheConcernedTowerHasAlreadyMoved() {
        // Given
        Position startingPositionKing = new Position(4, 0);
        King king = new King(startingPositionKing, positionValidator, Color.WHITE);

        Position startingPositionRook = new Position(7, 0);
        Rook rook = new Rook(startingPositionRook, positionValidator, Color.WHITE);
        Position nextPositionRook = new Position(7, 4);

        chessboard.add(king, rook);

        // When
        moveService.makeMovement(rook, chessboard, nextPositionRook);
        boolean canCastle = castlingService.canCastle(king, rook, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnTrueIfTheConcernedTowerHasNotAlreadyMoved() {
        // Given
        Position startingPositionKing = new Position(4, 0);
        King king = new King(startingPositionKing, positionValidator, Color.WHITE);

        Position startingPositionRook = new Position(7, 0);
        Rook rook = new Rook(startingPositionRook, positionValidator, Color.WHITE);

        chessboard.add(king, rook);

        // When
        boolean canCastle = castlingService.canCastle(king, rook, chessboard);

        // Then
        assertTrue(canCastle);
    }

    @Test
    void shouldReturnFalseIfKingIsCheckedAfterCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionRookEnemy = new Position(6, 4);
        Rook rookEnemy = new Rook(positionRookEnemy, positionValidator, Color.BLACK);

        chessboard.add(kingAlly, rookAlly, rookEnemy);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseIfThereIsAPieceOnPathWhenCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionKnightAlly = new Position(6,0);
        Knight knightAlly = new Knight(positionKnightAlly, positionValidator, Color.WHITE);


        chessboard.add(kingAlly, rookAlly, knightAlly);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseIfAPieceCanAttackKingOnPathWhenCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionRookEnemy = new Position(5, 3);
        Rook rookEnemy = new Rook(positionRookEnemy, positionValidator, Color.BLACK);

        chessboard.add(kingAlly, rookAlly, rookEnemy);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseIfAPieceCanAttackKingBeforeCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionRookEnemy = new Position(  4, 2);
        Rook rookEnemy = new Rook(positionRookEnemy, positionValidator, Color.BLACK);

        chessboard.add(kingAlly, rookAlly, rookEnemy);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseIfPieceIsOnPathWhenQueensideCastling() {
        // Given
        Position positionKingAlly = new Position(4, 7);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.BLACK);

        Position positionRookAlly = new Position(7, 7);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.BLACK);

        Position positionRookEnemy = new Position(  4, 5);
        Rook rookEnemy = new Rook(positionRookEnemy, positionValidator, Color.WHITE);

        chessboard.add(kingAlly, rookAlly, rookEnemy);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseIfPiecesOnPathWhenBigCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(0, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionKnightAlly = new Position(  2, 0);
        Knight knightAlly = new Knight(positionKnightAlly, positionValidator, Color.WHITE);

        chessboard.add(kingAlly, rookAlly, knightAlly);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnFalseWhenKingCrossesAttackedPositionWhileCastling() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(0, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        Position positionRookEnemy = new Position(  3, 4);
        Rook rookEnemy = new Rook(positionRookEnemy, positionValidator, Color.BLACK);

        chessboard.add(kingAlly, rookAlly, rookEnemy);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertFalse(canCastle);
    }

    @Test
    void shouldReturnTrueWhenKingsideCastlingIsValid() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);


        chessboard.add(kingAlly, rookAlly);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertTrue(canCastle);
    }

    @Test
    void shouldReturnTrueWhenQueensideCastlingIsValid() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(0, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);


        chessboard.add(kingAlly, rookAlly);

        // When
        boolean canCastle = castlingService.canCastle(kingAlly, rookAlly, chessboard);

        // Then
        assertTrue(canCastle);
    }

    @Test
    void shouldReturnNewPositionForKingAndRookWhenQueensideCastlingIsValid() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(0, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        chessboard.add(kingAlly, rookAlly);

        // When

        castlingService.castle(kingAlly, rookAlly, chessboard);
        Position kingsNewPosition = new Position(2, 0);
        Position rooksNewPosition = new Position(3, 0);

        // Then
        assertEquals(kingsNewPosition, kingAlly.getPosition());
        assertEquals(rooksNewPosition, rookAlly.getPosition());

    }

    @Test
    void shouldReturnNewPositionForKingAndRookWhenKingsideCastlingIsValid() {
        // Given
        Position positionKingAlly = new Position(4, 0);
        King kingAlly = new King(positionKingAlly, positionValidator, Color.WHITE);

        Position positionRookAlly = new Position(7, 0);
        Rook rookAlly = new Rook(positionRookAlly, positionValidator, Color.WHITE);

        chessboard.add(kingAlly, rookAlly);

        // When

        castlingService.castle(kingAlly, rookAlly, chessboard);
        Position kingsNewPosition = new Position(6, 0);
        Position rooksNewPosition = new Position(5, 0);

        // Then
        assertEquals(kingsNewPosition, kingAlly.getPosition());
        assertEquals(rooksNewPosition, rookAlly.getPosition());
    }

}
