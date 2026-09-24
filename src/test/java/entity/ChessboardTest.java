package entity;

import org.javachess.entity.Chessboard;
import org.javachess.entity.King;
import org.javachess.entity.Position;
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
}
