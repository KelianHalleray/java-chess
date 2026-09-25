package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PipedOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class MoveServiceTest {
    private MoveService moveService;
    private PositionValidator positionValidator;
    private Chessboard chessboard;
    private King globalKing;
    private Color blackColor;
    private Color whiteColor;

    @BeforeEach
    void setUp() {
        moveService = new MoveService();
        chessboard = new Chessboard();
        positionValidator = chessboard;
        blackColor = Color.BLACK;
        whiteColor = Color.WHITE;
        globalKing = new King(new Position( 2, 2), positionValidator, whiteColor);
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

        chessboard.add(queen, globalKing);

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

        chessboard.add(queen, enemyPawn, globalKing);

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

        chessboard.add(bishop, globalKing);

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

        chessboard.add(bishop, globalKing);
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

        chessboard.add(pawn, bishop, globalKing);

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

        chessboard.add(knight, globalKing);

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
        chessboard.add(bishop, globalKing);

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

        chessboard.add(bishop, pawnEnemy, globalKing);

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

        chessboard.add(pawn, pawnEnemy, globalKing);

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

        chessboard.add(pawn, globalKing);

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

    @Test
    void shouldAllowPawnToMoveTwoSquaresWhenPathIsClear() {
        // Given
        Position position = new Position(1, 1);
        Piece pawn = new Pawn(position, positionValidator, whiteColor);
        Position nextPosition = new Position(1, 3);


        chessboard.add(pawn, globalKing);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPosition);

        // Then
        assertTrue(canGoToPosition);
    }

    @Test
    void shouldNotAllowWhitePawnToMoveTwoSquaresWhenPathIsBlocked() {
        // Given
        Position position = new Position(1, 1);
        Piece pawn = new Pawn(position, positionValidator, whiteColor);
        Position nextPawnPosition = new Position(1, 3);
        Position allyPawnPosition = new Position(1, 2);
        Piece allyPawn = new Pawn(allyPawnPosition, positionValidator, whiteColor);

        chessboard.add(pawn, allyPawn);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPawnPosition);

        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldNotAllowBlackPawnToMoveTwoSquaresWhenPathIsBlocked() {
        // Given
        Position position = new Position(6, 6);
        Piece pawn = new Pawn(position , positionValidator, blackColor);
        Position nextPosition = new Position(6, 4);
        Position allyPawnPosition = new Position(6, 5);
        Piece allyPawn = new Pawn(allyPawnPosition, positionValidator, blackColor);

        chessboard.add(pawn, allyPawn);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPosition);

        // Then
        assertFalse(canGoToPosition);
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
        boolean isAttacked = moveService.isKingAttacked(chessboard, king.getColor());

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
        boolean isAttacked = moveService.isKingAttacked(chessboard, king.getColor());

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
        boolean isAttacked = moveService.isKingAttacked(chessboard, king.getColor());

        // Then
        assertTrue(isAttacked);

    }

    @Test
    void shouldNotAllowMoveWhenPieceIsProtectingKing() {
        // Given
        Position positionKing = new Position(3, 3);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionPawn = new Position(4,4);
        Piece pawn = new Pawn(positionPawn, positionValidator, whiteColor);
        Position nextPawnPosition = new Position(4,5);

        Position positionBishop = new Position(6, 6);
        Piece bishopEnemy = new Bishop(positionBishop, positionValidator, blackColor);


        chessboard.add(pawn, king, bishopEnemy);

        // When
        boolean canGoToPosition = moveService.checkMovement(pawn, chessboard, nextPawnPosition);

        // Then
        assertFalse(canGoToPosition);
    }

    @Test
    void shouldNotAllowCaptureWhenItExposesKing() {
        // Given
        Position positionPawnAlly = new Position(4 ,4);
        Piece pawnAlly = new Pawn(positionPawnAlly, positionValidator, whiteColor);

        Position positionKingAlly = new Position(3, 3);
        Piece kingAlly = new King(positionKingAlly, positionValidator, whiteColor);

        Position positionBishopEnemy = new Position(6, 6);
        Piece bishopEnemy = new Bishop(positionBishopEnemy, positionValidator, blackColor);

        Position positionPawnEnemy = new Position(3, 5);
        Piece pawnEnemy = new Pawn(positionPawnEnemy, positionValidator, blackColor);

        chessboard.add(pawnAlly, kingAlly, bishopEnemy, pawnEnemy);

        boolean canGoToPosition = moveService.checkMovement(pawnAlly, chessboard, positionPawnEnemy);

        // Then
        assertFalse(canGoToPosition);
    }

}
