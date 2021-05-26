package DAO;

import models.Department;
import models.Employee;
import models.Position;

import java.time.LocalDate;
import java.util.Collection;

public interface EmployeeDAO extends DAO<Employee> {
    Collection<Employee> getActiveByDepartment(Department entity);
    Collection<Employee> getAllByDepartment(Department entity);
    Collection<Employee> getActiveByPosition(Position entity);
    Collection<Employee> getAllByPosition(Position entity);
    Collection<Employee> getByEmploymentDate(LocalDate from, LocalDate to);
}
