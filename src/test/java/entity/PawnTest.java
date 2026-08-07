package entity;
import org.javachess.entity.Pawn;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PawnTest {

    @Test
    void shouldReturnMovePattern() {
        // Given
        Position position = new Position(1, 1);
        Piece pawn = new Pawn(position);

        // When
        List<Position> possiblePositions = pawn.getMovePattern();

        // Then
        assertEquals(List.of(new Position(1,2), new Position(1,3)),possiblePositions);
    }

    @Test
    void shouldReturnAttackPattern() {
        // Given
        Position position = new Position(4, 2);
        Pawn pawn = new Pawn(position);

        // When
        List<Position> attackPositions = pawn.getAttackPattern();

        // Then
        assertEquals(List.of(new Position(3, 3), new Position(5, 3)), attackPositions);
    }

}
