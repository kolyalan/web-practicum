package DAO;

import DAO.Impl.DepartmentDAOImpl;

public class DAOFactory {
    private static DepartmentDAO departmentDAO = null;
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
}
