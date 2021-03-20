package DAO.Impl;

import DAO.DAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DAOImpl<T> implements DAO<T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")  // turn off warning of unchecked cast
    public DAOImpl() {
        this.persistentClass =
                (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void add(T entity) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("add failed for " + persistentClass.getName() + ". "+ e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(T entity) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("update failed for " + persistentClass.getName() + ". "+ e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public T getById(int dep_id) {
        Session session = null;
        T entity = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            entity = session.get(persistentClass, dep_id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("getById failed for " + persistentClass.getName() + ". "+ e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Collection<T> getAll() {
        Session session = null;
        List<T> enities = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<T> query = session.createQuery("from " + persistentClass.getName() + "", persistentClass);
            enities = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("getAll failed for " + persistentClass.getName() + ". "+ e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return enities;
    }

    @Override
    public void delete(T entity) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("delete failed for " + persistentClass.getName() + ". "+ e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
