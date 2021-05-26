package DAO.Impl;

import DAO.EmployeeDAO;
import models.Department;
import models.Employee;
import models.Position;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class EmployeeDAOImpl extends DAOImpl<Employee> implements EmployeeDAO {
    @Override
    public Collection<Employee> getActiveByDepartment(Department entity) {
        Session session = null;
        List<Employee> employees;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "select emp " +
                    "from Employee emp join emp.contracts contr " +
                    " where contr.department = :department and contr.quitDate = null", Employee.class);
            query.setParameter("department", entity);
            employees = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public Collection<Employee> getAllByDepartment(Department entity) {
        Session session = null;
        List<Employee> employees;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "select emp " +
                            "from Employee emp join emp.contracts contr " +
                            " where contr.department = :department", Employee.class);
            query.setParameter("department", entity);
            employees = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public Collection<Employee> getActiveByPosition(Position entity) {
        Session session = null;
        List<Employee> employees;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "select emp " +
                            "from Employee emp join emp.contracts contr " +
                            " where contr.position = :position and contr.quitDate = null", Employee.class);
            query.setParameter("position", entity);
            employees = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public Collection<Employee> getAllByPosition(Position entity) {
        Session session = null;
        List<Employee> employees;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "select emp " +
                            "from Employee emp join emp.contracts contr " +
                            " where contr.position = :position", Employee.class);
            query.setParameter("position", entity);
            employees = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public Collection<Employee> getByEmploymentDate(LocalDate from, LocalDate to) {
        Session session = null;
        List<Employee> employees;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "select emp " +
                        "from Employee emp " +
                        "where emp.employmentDate >= :from and emp.employmentDate <= :to", Employee.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            employees = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }
}
