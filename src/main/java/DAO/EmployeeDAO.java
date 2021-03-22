package DAO;

import models.Department;
import models.Employee;
import models.Position;

import java.time.LocalDate;
import java.util.Collection;

public interface EmployeeDAO extends DAO<Employee> {
    Collection<Employee> getByDepartment(Department entity);
    Collection<Employee> getByPosition(Position entity);
    Collection<Employee> getByEmploymentDate(LocalDate from, LocalDate to);
}
