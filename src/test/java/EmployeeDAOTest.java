import DAO.DAOFactory;
import DAO.EmployeeDAO;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Collection;

public class EmployeeDAOTest {
    @Test
    public void testEmployee() {
        LocalDate birthDate = null;
        try {
            birthDate = LocalDate.parse("1970-03-14");
        } catch (Exception ex) {
            Assert.fail("Incorrect test " + ex);
        }
        Employee emp = new Employee("Иван Иванович Иванов",
                birthDate, DegreeType.MASTER,
                "Москва", "8(800)555-35-35");

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        employeeDAO.add(emp);

        Employee emp2 = employeeDAO.getById(emp.getId());

        Assert.assertEquals(emp, emp2);

        emp = emp2;

        emp.setPhone("+9(300)1002121");
        employeeDAO.update(emp);

        emp2 = employeeDAO.getById(emp.getId());

        Assert.assertEquals(emp, emp2);

        emp = emp2;

        employeeDAO.delete(emp);
    }

    @Test
    public void testEmployeeByDepPos() {
        Department department = new Department("test_depatrment", null);
        Position position = new Position("test position", "no responsibility");
        Employee employee = new Employee("Иван иваныч", LocalDate.parse("1970-03-14"), DegreeType.DOCTOR, "Сызрань", "+79033163143");
        Contract contract = new Contract(employee, department, position, employee.getEmploymentDate(), null);

        DAOFactory.getInstance().getDepartmentDAO().add(department);
        DAOFactory.getInstance().getPositionDAO().add(position);
        DAOFactory.getInstance().getEmployeeDAO().add(employee);
        DAOFactory.getInstance().getContractDAO().add(contract);

        Collection<Employee> employees_dep = DAOFactory.getInstance().getEmployeeDAO().getByDepartment(department);

        Collection<Employee> employees_pos = DAOFactory.getInstance().getEmployeeDAO().getByPosition(position);


        Collection<Employee> employees_before = DAOFactory.getInstance().getEmployeeDAO().getByEmploymentDate(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));
        Collection<Employee> employees_now = DAOFactory.getInstance().getEmployeeDAO().getByEmploymentDate(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        Collection<Employee> employees_future = DAOFactory.getInstance().getEmployeeDAO().getByEmploymentDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        Assert.assertEquals(employees_dep.size(), 1);
        Assert.assertEquals(employees_pos.size(), 1);
        Assert.assertEquals(employees_before.size(), 0);
        Assert.assertEquals(employees_now.size(), 1);
        Assert.assertEquals(employees_future.size(), 0);

        Assert.assertTrue(employees_dep.contains(employee));
        Assert.assertTrue(employees_pos.contains(employee));
        Assert.assertFalse(employees_before.contains(employee));
        Assert.assertTrue(employees_now.contains(employee));
        Assert.assertFalse(employees_future.contains(employee));


        DAOFactory.getInstance().getContractDAO().delete(contract);
        DAOFactory.getInstance().getPositionDAO().delete(position);
        DAOFactory.getInstance().getEmployeeDAO().delete(employee);
        DAOFactory.getInstance().getDepartmentDAO().delete(department);
    }

}
