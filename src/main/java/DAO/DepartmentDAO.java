package DAO;

import models.Department;

import java.sql.SQLException;
import java.util.Collection;

public interface DepartmentDAO {
    void addDepartment(Department dept);
    void updateDepartment(int dep_id, Department dept);
    Department getDepartmentById(int dep_id);
    Collection<Department> getAllDepartments();
    Collection<Department> getActiveDepartments();
    void deleteDepartment(Department dept);
}
