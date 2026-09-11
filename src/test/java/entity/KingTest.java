package entity;


import org.javachess.entity.Chessboard;
import org.javachess.entity.King;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KingTest {
    private PositionValidator positionValidator;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
    }

    @Test
    void shouldReturnMovePattern() {
        // Given

        Position position = new Position(3, 3);
        Piece king = new King(position, positionValidator);

        // When
        List<Position> possiblePositions = king.getMovePattern();

        // Then
        List<Position> realListPosition = List.of(
                new Position(2, 4),
                new Position(3, 4),
                new Position(4, 4),
                new Position(4, 3),
                new Position(4, 2),
                new Position(3, 2),
                new Position(2, 2),
                new Position(2, 3)
        );

        assertTrue(realListPosition.containsAll(possiblePositions));
        assertEquals(8, possiblePositions.size());
    }

    @Test
    void shouldNotReturnInvalidPositionInMovePattern() {
        // Given
        Position position = new Position(7, 7);
        Piece king = new King(position, positionValidator);

        // When
        List<Position> possiblePositions = king.getMovePattern();

        // Then
        assertEquals(3, possiblePositions.size());
    }
}
