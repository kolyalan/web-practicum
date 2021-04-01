package DAO.Impl;

import DAO.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.Collection;
import java.util.List;

public class DepartmentDAOImpl extends DAOImpl<Department> implements DepartmentDAO {

    @Override
    public Collection<Department> getActive() {
        Session session = null;
        List<Department> departments;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Department> query = session.createQuery("from Department where archived = false", Department.class);
            departments = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return departments;
    }
}
