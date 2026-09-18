package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveServiceTest {
    private MoveService moveService;
    private PositionValidator positionValidator;
    private Chessboard chessboard;
    private Color blackColor;
    private Color whiteColor;

    @BeforeEach
    void setUp() {
        moveService = new MoveService();
        chessboard = new Chessboard();
        positionValidator = chessboard;
        blackColor = Color.BLACK;
        whiteColor = Color.WHITE;
    }

    @Test
    void shouldReturnFalseIfPositionIsNotInPiecesMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator, whiteColor);
        Position badPosition = new Position(3, 4);

        // When
        boolean isInMovePattern = moveService.isInMovePattern(bishop, badPosition);

        // Then
        assertFalse(isInMovePattern);
    }

    @Test
    void shouldReturnTrueIfPositionIsInPiecesMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator, whiteColor);
        Position goodPosition = new Position(4, 4);

        // When
        boolean isInMovePattern = moveService.isInMovePattern(bishop, goodPosition);

        // Then
        assertTrue(isInMovePattern);
    }

    @Test
    void shouldReturnFalseIfPositionIsOccupiedByAllyPiece() {
        // Given
        Position queenPosition = new Position(3, 3);
        Piece queen = new Queen(queenPosition, positionValidator, whiteColor);
        Position nextChosenPosition = new Position(3, 4);
        Position pawnPosition = new Position(3, 4);
        Piece pawnAlly = new Pawn(pawnPosition, positionValidator, whiteColor);

        chessboard.add(queen, pawnAlly);

        // When
        boolean canGoToPosition = moveService.checkMovement(queen, chessboard, nextChosenPosition);

        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldReturnTrueIfPositionIsNotAlreadyOccupied() {
        // Given
        Position queenPosition = new Position(3, 3);
        Piece queen = new Queen(queenPosition, positionValidator, whiteColor);
        Position nextChosenPosition = new Position(3, 4);

        chessboard.add(queen);

        // When
        boolean canGoToPosition = moveService.checkMovement(queen, chessboard, nextChosenPosition);


        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldReturnTrueIfPositionIsOccupiedByEnemyPiece() {
        // Given
        Position position = new Position(3, 3);
        Piece queen = new Queen(position, positionValidator, whiteColor);
        Position nextChosenPosition = new Position(3, 4);
        Position enemyPawnPosition = new Position(3, 4);
        Piece enemyPawn = new Pawn(enemyPawnPosition, positionValidator, blackColor);

        chessboard.add(queen, enemyPawn);

        // When
        boolean canGoToPosition = moveService.checkMovement(queen, chessboard, nextChosenPosition);

        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldReturnFalseWhenPositionIsNotOccupiedButIsNotInMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator, whiteColor);
        Position nextPosition = new Position(3, 4);


        // When
        boolean canGoToPosition = moveService.checkMovement(bishop, chessboard, nextPosition);


        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldReturnTrueWhenPositionIsNotOccupiedAndIsInMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator, whiteColor);
        Position nextPosition = new Position(4, 4);

        // When
        boolean canGoToPosition = moveService.checkMovement(bishop, chessboard, nextPosition);

        // Then
        assertTrue(canGoToPosition);
    }

}
