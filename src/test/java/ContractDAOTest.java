import DAO.ContractDAO;
import DAO.DAOFactory;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ContractDAOTest {
    @Test
    public void contractTest() {
        Department department = new Department("test_depatrment", null);
        Position position = new Position("test position", "no responsibility");
        Employee employee = new Employee("Иван иваныч", LocalDate.parse("1970-03-14"), DegreeType.DOCTOR, "Сызрань", "+79033163143");
        Contract contract = new Contract(employee, department, position, employee.getEmploymentDate(), null);

        DAOFactory.getInstance().getDepartmentDAO().add(department);
        DAOFactory.getInstance().getPositionDAO().add(position);
        DAOFactory.getInstance().getEmployeeDAO().add(employee);
        DAOFactory.getInstance().getContractDAO().add(contract);

        ContractDAO contractDAO = DAOFactory.getInstance().getContractDAO();

        Contract contract2 = contractDAO.getById(contract.getId());

        Assert.assertEquals(contract2, contract);

        contract = contract2;


    }
}
