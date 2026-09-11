package service;

import org.javachess.entity.Bishop;
import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.interfaces.PositionValidator;
import org.javachess.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveServiceTest {
    private MoveService moveService;
    private PositionValidator positionValidator;

    @BeforeEach
    void setUp() {
        moveService = new MoveService();
        positionValidator = new Chessboard();
    }

    @Test
    void shouldReturnFalseIfPositionIsNotInPiecesMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator);
        Position badPosition = new Position(3, 4);

        // When
        boolean isInMovePattern = moveService.isInMovePattern(bishop, badPosition);

        // Then
        assertFalse(isInMovePattern);
    }

    @Test
    void shouldReturnTrueIfPositionIsInPiecesMovePattern() {
        // Given
        Position position = new Position(3, 3);
        Piece bishop = new Bishop(position, positionValidator);
        Position goodPosition = new Position(4, 4);

        // When
        boolean isInMovePattern = moveService.isInMovePattern(bishop, goodPosition);

        // Then
        assertTrue(isInMovePattern);
    }

}
