package entity;

import org.javachess.entity.Bishop;
import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BishopTest {
    private PositionValidator positionValidator;


    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
    }

    @Test
    void shouldReturnMovePattern() {
        // Given

        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator);

        // When
        List<Position> possiblePositions = bishop.getMovePattern();

        // Then
        List<Position> realListPositions = List.of(
                new Position(2, 4),
                new Position(1, 5),
                new Position(0, 6),
                new Position(4, 4),
                new Position(5, 5),
                new Position(6, 6),
                new Position(7, 7),
                new Position(4, 2),
                new Position(5, 1),
                new Position(6, 0),
                new Position(2, 2),
                new Position(1, 1),
                new Position(0, 0)
        );

        assertEquals(new HashSet<>(realListPositions), new HashSet<>(possiblePositions));
        assertEquals(13, possiblePositions.size());
    }
}
