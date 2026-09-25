package entity;

import org.javachess.entity.*;
import org.javachess.enums.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessboardTest {

    @Test
    void shouldReturnTrueWhenIsInChessboard() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position position = new Position(3, 2);

        // When
        boolean isValidPosition = chessboard.isValidPosition(position);

        // Then
        assertTrue(isValidPosition);
    }

    @Test
    void shouldReturnFalseWhenIsNotInChessboard() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position position = new Position(148, 148);

        // When
        boolean isValidPosition = chessboard.isValidPosition(position);

        // Then
        assertFalse(isValidPosition);
    }

    @Test
    void shouldReturnFalseWhenXIsInChessboardButYIsNotInChessboard() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position position = new Position(7, 100);

        // When
        boolean isValidPosition = chessboard.isValidPosition(position);

        // Then
        assertFalse(isValidPosition);
    }

    @Test
    void shouldReturnFalseWhenXIsNotInChessboardButYIsInChessboard() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position position = new Position(10, 7);

        // When
        boolean isValidPosition = chessboard.isValidPosition(position);

        // Then
        assertFalse(isValidPosition);
    }

    @Test
    void shouldReturnBlackKing() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position blackKingPosition = new Position(5, 3);
        Position whiteKingPosition = new Position(3, 5);
        King blackKing = new King(blackKingPosition, chessboard, Color.BLACK);
        King whiteKing = new King(whiteKingPosition, chessboard, Color.WHITE);

        chessboard.add(blackKing, whiteKing);

        // When
        King foundKing = chessboard.findKing(blackKing.getColor());


        // Then
        assertEquals(blackKing, foundKing);
    }

    @Test
    void shouldReturnWhiteKing() {
        // Given
        Chessboard chessboard = new Chessboard();
        Position blackKingPosition = new Position(5, 3);
        Position whiteKingPosition = new Position(3, 5);
        King blackKing = new King(blackKingPosition, chessboard, Color.BLACK);
        King whiteKing = new King(whiteKingPosition, chessboard, Color.WHITE);

        chessboard.add(blackKing, whiteKing);

        // When
        King foundKing = chessboard.findKing(whiteKing.getColor());


        // Then
        assertEquals(whiteKing, foundKing);
    }

    @Test
    void shouldReturnChessboardCopy() {
        // Given
        Chessboard chessboard = new Chessboard();

        /*
        *  Initialisation des pièces du chessboard
         */
        Position pawnPosition = new Position(3, 2);
        Piece pawn = new Pawn(pawnPosition, chessboard, Color.WHITE);

        Position kingPosition = new Position(3, 4);
        Piece king = new King(kingPosition, chessboard, Color.WHITE);

        chessboard.add(pawn, king);

        // When
        Chessboard copiedChessboard = chessboard.copy();
        Piece copiedKing = copiedChessboard.getPieceFromPosition(kingPosition);
        Piece copiedPawn = copiedChessboard.getPieceFromPosition(pawnPosition);

        // Then
        assertNotSame(chessboard, copiedChessboard);
        assertEquals(chessboard.getPieces().size(), copiedChessboard.getPieces().size());
        assertNotNull(copiedKing);
        assertNotNull(copiedPawn);
        assertNotSame(king, copiedKing);
        assertNotSame(pawn, copiedPawn);
    }
}
