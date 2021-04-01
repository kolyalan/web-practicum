package DAO;

import models.Department;

import java.util.Collection;

public interface DepartmentDAO extends DAO<Department> {
    Collection<Department> getActive();
}
