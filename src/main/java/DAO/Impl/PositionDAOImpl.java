package DAO.Impl;

import DAO.PositionDAO;
import models.Position;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.Collection;
import java.util.List;

public class PositionDAOImpl extends DAOImpl<Position> implements PositionDAO {
    @Override
    public Collection<Position> getActive() {
        Session session = null;
        List<Position> positions;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Position> query = session.createQuery("from Position where archived = false", Position.class);
            positions = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return positions;
    }
}
