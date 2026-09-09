package entity;

import org.javachess.entity.Knight;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KnightTest {

    @Test
    void shouldReturnMovePattern() {
        // Given

        Position position = new Position(3, 3);
        Piece knight = new Knight(position);

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
}
