package DAO.Impl;

import DAO.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Override
    public void addDepartment(Department dept) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(dept);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("addDepartment failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateDepartment(int dep_id, Department dept) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(dept);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("updateDepartment failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Department getDepartmentById(int dep_id) {
        Session session = null;
        Department dept = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            dept = session.get(Department.class, dep_id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("getDepartmentById failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return dept;
    }

    @Override
    public Collection<Department> getAllDepartments() {
        Session session = null;
        List<Department> departments = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Department> query = session.createQuery("from Department d", Department.class);
            departments = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("getDepartmentById failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return departments;
    }

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
            System.err.println("getDepartmentById failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return departments;
    }

    @Override
    public void deleteDepartment(Department dept) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(dept);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("updateDepartment failed. " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
