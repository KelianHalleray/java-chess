package service;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.GameStateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStateServiceTest {
    private PositionValidator positionValidator;
    private GameStateService gameStateService;
    private Chessboard chessboard;
    private Color blackColor;
    private Color whiteColor;

    @BeforeEach
    void setUp() {
        chessboard = new Chessboard();
        positionValidator = chessboard;
        gameStateService = new GameStateService();
        blackColor = Color.BLACK;
        whiteColor = Color.WHITE;
    }

    @Test
    void shouldReturnTrueWhenKingIsCheckmated() {
        // Given
        Position positionKing = new Position(3 , 0);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionQueenEnemy = new Position(3, 1);
        Piece queenEnemy = new Queen(positionQueenEnemy, positionValidator, blackColor);

        Position positionKingEnemy = new Position(4, 2);
        Piece kingEnemy = new King(positionKingEnemy, positionValidator, blackColor);

        chessboard.add(king, queenEnemy, kingEnemy);

        // When
        boolean isCheckmate = gameStateService.isCheckmate(chessboard, whiteColor);

        // Then
        assertTrue(isCheckmate);
    }

    @Test
    void shouldReturnFalseWhenKingIsCheckedButAPieceCanSave() {
        Position positionKing = new Position(3 , 0);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionBishop = new Position(2, 2);
        Piece bishopAlly = new Bishop(positionBishop, positionValidator, whiteColor);

        Position positionQueenEnemy = new Position(3, 1);
        Piece queenEnemy = new Queen(positionQueenEnemy, positionValidator, blackColor);

        Position positionKingEnemy = new Position(4, 2);
        Piece kingEnemy = new King(positionKingEnemy, positionValidator, blackColor);

        chessboard.add(king, bishopAlly, queenEnemy, kingEnemy);

        // When
        boolean isCheckmate = gameStateService.isCheckmate(chessboard, whiteColor);

        // Then
        assertFalse(isCheckmate);
    }

    @Test
    void shouldReturnFalseWhenAllyPieceCanBlockCheck() {
        Position positionKing = new Position(3 , 0);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionRook = new Position(2, 0);
        Piece rookAlly = new Rook(positionRook, positionValidator, whiteColor);

        Position positionQueenEnemy = new Position(1, 2);
        Piece queenEnemy = new Queen(positionQueenEnemy, positionValidator, blackColor);

        Position positionRookEnemy = new Position(4, 2);
        Piece rookEnemy = new King(positionRookEnemy, positionValidator, blackColor);

        Position positionKnightEnemy = new Position(5, 2);
        Piece knightEnemy = new Knight(positionKnightEnemy, positionValidator, blackColor);

        chessboard.add(king, rookAlly, queenEnemy, rookEnemy, knightEnemy);

        // When
        boolean isCheckmate = gameStateService.isCheckmate(chessboard, whiteColor);

        // Then
        assertFalse(isCheckmate);
    }

    @Test
    void shouldReturnTrueWhenKingIsSafeButBlocked() {
        // Given
        Position positionKing = new Position(3, 0);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionQueenEnemy = new Position(2, 2);
        Piece queenEnemy = new Queen(positionQueenEnemy, positionValidator, blackColor);

        Position positionRookEnemy = new Position(4, 2);
        Piece rookEnemy = new Rook(positionRookEnemy, positionValidator, blackColor);

        chessboard.add(king, queenEnemy, rookEnemy);

        // When
        boolean isStalemate = gameStateService.isStalemate(chessboard, whiteColor);

        // Then
        assertTrue(isStalemate);
    }

    @Test
    void shouldReturnFalseWhenKingIsBlockedButAllyPieceCanMove() {
        // Given
        Position positionKing = new Position(3, 0);
        Piece king = new King(positionKing, positionValidator, whiteColor);

        Position positionRookAlly = new Position(5, 3);
        Piece rookAlly = new Rook(positionRookAlly, positionValidator, whiteColor);

        Position positionQueenEnemy = new Position(2, 2);
        Piece queenEnemy = new Queen(positionQueenEnemy, positionValidator, blackColor);

        Position positionRookEnemy = new Position(4, 2);
        Piece rookEnemy = new Rook(positionRookEnemy, positionValidator, blackColor);


        chessboard.add(king, rookAlly, queenEnemy, rookEnemy);

        // When
        boolean isStalemate = gameStateService.isStalemate(chessboard, whiteColor);

        // Then
        assertFalse(isStalemate);
    }
}
