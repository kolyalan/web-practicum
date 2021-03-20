package DAO.Impl;

import DAO.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentDAOImpl extends DAOImpl<Department> implements DepartmentDAO {

    @Override
    public Collection<Department> getActiveDepartments() {
        Session session = null;
        List<Department> departments = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Department> query = session.createQuery("from Department where archived = true", Department.class);
            departments = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("getActiveDepartments failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return departments;
    }
}
