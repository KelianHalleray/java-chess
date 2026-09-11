package entity;

import org.javachess.entity.*;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KnightTest {
    private PositionValidator positionValidator;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
    }

    @Test
    void shouldReturnMovePattern() {
        // Given

        Position position = new Position(3, 3);
        Piece knight = new Knight(position, positionValidator);

        // When
        List<Position> possiblePositions = knight.getMovePattern();

        // Then
        List<Position> realListPositions = List.of(
                new Position(2, 5),
                new Position(4, 5),
                new Position(5, 4),
                new Position(5, 2),
                new Position(4, 1),
                new Position(2, 1),
                new Position(1, 2),
                new Position(1, 4)
        );


        assertEquals(new HashSet<>(realListPositions), new HashSet<>(possiblePositions));
        assertEquals(8, possiblePositions.size());
    }

    @Test
    void shouldNotReturnInvalidPositionInMovePattern() {
        // Given
        Position position = new Position(7, 7);
        Piece knight = new Knight(position, positionValidator);

        // When
        List<Position> possiblePositions = knight.getMovePattern();

        // Then
        assertEquals(2, possiblePositions.size());
    }

}
