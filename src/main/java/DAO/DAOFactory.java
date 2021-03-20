package DAO;

import DAO.Impl.ContractDAOImpl;
import DAO.Impl.DepartmentDAOImpl;
import DAO.Impl.EmployeeDAOImpl;
import DAO.Impl.PositionDAOImpl;

public class DAOFactory {
    private static DepartmentDAO departmentDAO = null;
    private static PositionDAO positionDAO = null;
    private static EmployeeDAO employeeDAO = null;
    private static ContractDAO contractDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public DepartmentDAO getDepartmentDAO() {
        if (departmentDAO == null) {
            departmentDAO = new DepartmentDAOImpl();
        }
        return departmentDAO;
    }

    public PositionDAO getPositionDAO() {
        if (positionDAO == null) {
            positionDAO = new PositionDAOImpl();
        }
        return positionDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
        if (employeeDAO == null) {
            employeeDAO = new EmployeeDAOImpl();
        }
        return employeeDAO;
    }
    public ContractDAO getContractDAO() {
        if (contractDAO == null) {
            contractDAO = new ContractDAOImpl();
        }
        return contractDAO;
    }
}
