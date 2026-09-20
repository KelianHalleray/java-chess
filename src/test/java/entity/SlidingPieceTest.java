package entity;

import org.javachess.entity.Bishop;
import org.javachess.entity.Chessboard;
import org.javachess.entity.Position;
import org.javachess.entity.SlidingPiece;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlidingPieceTest {
    private PositionValidator positionValidator;
    private Color whiteColor;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
        whiteColor = Color.WHITE;
    }

    @Test
    void shouldReturnPositionsBetweenPieceAndDestination() {
        // Given
        Position position = new Position(1, 1);
        SlidingPiece bishop = new Bishop(position, positionValidator, whiteColor);
        Position destination = new Position(5, 5);

        // When
        List<Position> path = bishop.getPathTo(destination);


        // Then
        assertEquals(List.of(
                        new Position(2, 2),
                        new Position(3, 3),
                        new Position(4, 4)
                ),
                path
        );
    }
}
