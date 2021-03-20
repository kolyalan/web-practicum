package DAO;

import models.Position;

import java.util.Collection;

public interface PositionDAO extends DAO<Position> {
    Collection<Position> getActivePositions();
}
