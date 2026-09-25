package entity;

import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.entity.Queen;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class QueenTest {
    private PositionValidator positionValidator;
    private Color color;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
        color = Color.WHITE;
    }

    @Test
    void shouldReturnMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece queen = new Queen(position, positionValidator, color);

        // When
        List<Position> possiblePositions = queen.getMovePattern();

        // Then
        List<Position> realListPositions = List.of(
                // Vertical
                new Position(3, 4),
                new Position(3, 5),
                new Position(3, 6),
                new Position(3, 7),
                new Position(3, 2),
                new Position(3, 1),
                new Position(3, 0),
                // Horizontal
                new Position(4, 3),
                new Position(5, 3),
                new Position(6, 3),
                new Position(7, 3),
                new Position(2, 3),
                new Position(1, 3),
                new Position(0, 3),
                // Diagonales
                new Position(4, 4),
                new Position(5, 5),
                new Position(6, 6),
                new Position(7, 7),
                new Position(2, 2),
                new Position(1, 1),
                new Position(0, 0),
                new Position(4, 2),
                new Position(5, 1),
                new Position(6, 0),
                new Position(2, 4),
                new Position(1, 5),
                new Position(0, 6)
        );

        assertEquals(new HashSet<>(realListPositions), new HashSet<>(possiblePositions));
        assertEquals(27, possiblePositions.size());
    }

    @Test
    void shouldReturnQueenCopy() {
        // Given
        Position position = new Position(3, 3);
        Piece queen = new Queen(position, positionValidator, color);

        Chessboard copiedChessboard = new Chessboard();
        // When
        Piece copiedQueen = queen.copy(copiedChessboard);


        // Then
        assertNotSame(queen, copiedQueen);
        assertEquals(queen.getColor(), copiedQueen.getColor());
        assertEquals(queen.getPosition(), copiedQueen.getPosition());
    }
}
