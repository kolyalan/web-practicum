import DAO.DAOFactory;
import DAO.EmployeeDAO;
import models.DegreeType;
import models.Employee;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

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
}
