package org.javachess.entity;

import java.util.Objects;

public class Position {
    private final int position_x;
    private final int position_y;

    public Position(int position_x, int position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Position)) {
            return false;
        }

        Position p = (Position) obj;

        return position_x == p.position_x
                && position_y == p.position_y;

    }

    @Override
    public int hashCode()  {
        return Objects.hash(position_x, position_y);
    }

}
