package entity;

import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.entity.Rook;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RookTest {
    private PositionValidator positionValidator;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
    }

    @Test
    void shouldReturnMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece rook = new Rook(position, positionValidator);

        // When
        List<Position> possiblePositions = rook.getMovePattern();

        // Then
        List<Position> realListPositions = List.of(
                new Position(2, 3),
                new Position(1, 3),
                new Position(0, 3),
                new Position(3, 4),
                new Position(3, 5),
                new Position(3,6),
                new Position(3, 7),
                new Position(4, 3),
                new Position(5, 3),
                new Position(6, 3),
                new Position(7, 3),
                new Position(3, 2),
                new Position(3, 1),
                new Position(3, 0)
        );

        assertEquals(new HashSet<>(realListPositions), new HashSet<>(possiblePositions));
        assertEquals(14, possiblePositions.size());
    }
}
