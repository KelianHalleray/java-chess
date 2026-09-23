package entity;
import org.javachess.entity.Chessboard;
import org.javachess.entity.Pawn;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PawnTest {
    private PositionValidator positionValidator;
    private Color colorWhite;
    private Color colorBlack;

    @BeforeEach
    void setUp() {
        positionValidator = new Chessboard();
        colorWhite = Color.WHITE;
        colorBlack = Color.BLACK;

    }

    @Test
    void shouldReturnMovePattern() {
        // Given
        Position position = new Position(1, 1);
        Piece pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> possiblePositions = pawn.getMovePattern();

        // Then
        assertEquals(List.of(new Position(1,2)),possiblePositions);
    }

    @Test
    void shouldReturnAttackPattern() {
        // Given
        Position position = new Position(4, 2);
        Pawn pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> attackPositions = pawn.getAttackPattern();

        // Then
        assertEquals(List.of(new Position(3, 3), new Position(5, 3)), attackPositions);
    }

    @Test
    void shouldNotReturnInvalidPositionsInMovePattern() {
        // Given
        Position position = new Position(7, 7);
        Piece pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> possiblePositions = pawn.getMovePattern();


        // Then
        assertEquals(0, possiblePositions.size());
    }

    @Test
    void shouldNotReturnInvalidPositionsInAttackPattern() {
        // Given
        Position position = new Position(7, 7);
        Pawn pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> attackPositions = pawn.getAttackPattern();

        // Then
        assertEquals(0, attackPositions.size());
    }

    @Test
    void shouldMoveTowardsNegativeYWhenColorIsBlack() {
        // Given
        Position position = new Position(3, 5);
        Pawn pawn = new Pawn(position, positionValidator, colorBlack);

        // When
        List<Position> possiblePositions = pawn.getMovePattern();
        List<Position> realPossiblePositions = List.of(
                new Position(3, 4)
        );

        // Then
        assertEquals(realPossiblePositions, possiblePositions);
    }

    @Test
    void shouldMoveTowardsPositiveYWhenColorIsWhite() {
        // Given
        Position position = new Position(3, 3);
        Pawn pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> possiblePositions = pawn.getMovePattern();
        List<Position> realPossiblePositions = List.of(
                new Position(3, 4)
        );

        // Then
        assertEquals(realPossiblePositions, possiblePositions);
    }

    @Test
    void shouldAttackTowardsPositiveYWhenColorIsWhite() {
        // Given
        Position position = new Position(3, 3);
        Pawn pawn = new Pawn(position, positionValidator, colorWhite);

        // When
        List<Position> possibleAttackPositions = pawn.getAttackPattern();
        List<Position> realPossibleAttackPositions = List.of(
                new Position(4, 4),
                new Position(2, 4)
        );

        // Then
        assertEquals(new HashSet<>(realPossibleAttackPositions), new HashSet<>(possibleAttackPositions));
    }

    @Test
    void shouldAttackTowardsNegativeYWhenColorIsBlack() {
        // Given
        Position position = new Position(3, 5);
        Pawn pawn = new Pawn(position, positionValidator, colorBlack);

        // When
        List<Position> possibleAttackPositions = pawn.getAttackPattern();
        List<Position> realPossibleAttackPositions = List.of(
                new Position(2, 4),
                new Position(4, 4)
        );

        // Then
        assertEquals(new HashSet<>(realPossibleAttackPositions), new HashSet<>(possibleAttackPositions));
    }

}
