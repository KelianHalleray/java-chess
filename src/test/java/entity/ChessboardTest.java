package entity;

import org.javachess.entity.Chessboard;
import org.javachess.entity.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
