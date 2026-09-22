package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shouldReturnFalseWhenPieceIsBlockingPath() {
        // Given
        Position positionBishop = new Position(1, 1);
        Position positionPawn = new Position(3, 3);
        Piece pawn = new Pawn(positionPawn, positionValidator, whiteColor);
        Piece bishop = new Bishop(positionBishop, positionValidator, whiteColor);
        Position nextBishopPosition = new Position(5, 5);

        chessboard.add(pawn, bishop);

        // When
        boolean canGoToPosition = moveService.checkMovement(bishop, chessboard, nextBishopPosition);

        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldReturnTrueWhenKnightMovesToValidEmptyPosition() {
        // Given
        Position positionKnight = new Position(3, 3);
        Piece knight = new Knight(positionKnight, positionValidator, whiteColor);
        Position nextPosition = new Position(1, 4);

        chessboard.add(knight);

        // When
        boolean canGoToPosition = moveService.checkMovement(knight, chessboard, nextPosition);

        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldMovePieceToValidDestination() {
        // Given
        Position positionBishop = new Position(3, 3);
        Piece bishop = new Bishop(positionBishop, positionValidator, whiteColor);
        Position nextPosition = new Position(5, 5);
        chessboard.add(bishop);

        // When
        moveService.makeMovement(bishop,chessboard, nextPosition);
        Position newBishopPosition = bishop.getPosition();

        // Then
        assertEquals(nextPosition, newBishopPosition);
    }

    @Test
    void shouldNotMovePieceToInvalidDestination() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator, whiteColor);
        Position nextPosition = new Position(4, 4);
        Position pawnPosition = new Position(4, 4);
        Piece pawn = new Pawn(pawnPosition, positionValidator, whiteColor);

        chessboard.add(bishop, pawn);

        // When
        moveService.makeMovement(bishop, chessboard, nextPosition);
        Position newPosition = bishop.getPosition();

        // Then
        assertEquals(position, newPosition);

    }

    @Test
    void shouldCaptureEnemyPieceOnDestination() {
        // Given
        Position positionBishop = new Position(3, 3);
        Piece bishop = new Bishop(positionBishop, positionValidator, whiteColor);
        Position positionPawnEnemy = new Position(5, 5);
        Piece pawnEnemy = new Pawn(positionPawnEnemy, positionValidator, blackColor);
        Position nextBishopPosition = new Position(5, 5);

        chessboard.add(bishop, pawnEnemy);

        // When
        moveService.makeMovement(bishop, chessboard, nextBishopPosition);
        Position newBishopPosition = bishop.getPosition();
        boolean pawnEnemyExists = chessboard.exists(pawnEnemy);

        // Then
        assertEquals(nextBishopPosition, newBishopPosition);
        assertFalse(pawnEnemyExists);
    }

    @Test
    void shouldNotAllowPawnToCaptureForward() {
        // Given
        Position position = new Position(3, 3);
        Piece pawn = new Pawn(position, positionValidator, whiteColor);
        Position pawnEnemyPosition = new Position(3, 4);
        Piece pawnEnemy = new Pawn(pawnEnemyPosition, positionValidator, blackColor);
        Position pawnNextPosition = new Position(3, 4);

        chessboard.add(pawn, pawnEnemy);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, pawnNextPosition);

        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldAllowPawnToCaptureOnDiagonal() {
        // Given
        Position positionPawn = new Position(3, 3);
        Piece pawn = new Pawn(positionPawn, positionValidator, whiteColor);
        Position pawnEnemyPosition = new Position(4, 4);
        Piece pawnEnemy = new Pawn(pawnEnemyPosition, positionValidator, blackColor);
        Position nextPosition = new Position(4, 4);

        chessboard.add(pawn, pawnEnemy);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPosition);

        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldAllowPawnToGoForwardIfEmpty() {
        // Given
        Position positionPawn = new Position(3, 3);
        Piece pawn = new Pawn(positionPawn, positionValidator, whiteColor);
        Position nextPosition = new Position(3, 4);

        chessboard.add(pawn);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPosition);

        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldNotAllowPawnToGoDiagonalIfEmpty() {
        // Given
        Position positionPawn = new Position(3, 3);
        Piece pawn = new Pawn(positionPawn, positionValidator, whiteColor);
        Position nextPosition = new Position(4, 4);

        chessboard.add(pawn);
        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPosition);

        // Then
        assertFalse(canGoToPosition);
    }

}
