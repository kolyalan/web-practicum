import DAO.ContractDAO;
import DAO.DAOFactory;
import models.Contract;
import models.Department;
import models.Employee;
import models.Position;

import java.util.Collection;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
/*        Collection<Department> depts = DAOFactory.getInstance().getDepartmentDAO().getAll();
        if (depts != null) {
            for (Department dept : depts) {
                System.out.println("DepartmentId: " + dept.getId());
                System.out.println("DepartmentName: " + dept.getName());
                Department head = dept.getHeadDepartment();
                System.out.println("HeadDepartmentName: " + ((head == null) ? "NULL" : head.getName()));
                Set<Department> children = dept.getChildDeps();
                if (children != null) {
                    for (Department child : children) {
                        System.out.println("    child " + child.getName());
                    }
                }
            }
        }*/
/*
        Collection<Position> positions = DAOFactory.getInstance().getPositionDAO().getAll();
        if (positions != null) {
            for (Position pos : positions) {
                System.out.println("PosId: " + pos.getId());
                System.out.println("name: " + pos.getName());
                System.out.println("Responsibilities: " + pos.getResponsibility());
                Set<Contract> contracts = pos.getContracts();
                if (contracts != null) {
                    System.out.println("workers:");
                    for (Contract contract : contracts) {
                        System.out.println("    contr " + contract.getId() + " dept " + contract.getDepartment().getName() + " pos " + contract.getPosition().getName() + " worker " + contract.getEmployee().getName());
                    }
                }
            }
        }
*/
/*
        Collection<Employee> employees = DAOFactory.getInstance().getEmployeeDAO().getAll();
        if (employees != null) {
            for (Employee emp : employees) {
                System.out.println("EmpId\t\t\t" + emp.getId());
                System.out.println("EmpName\t\t\t" + emp.getName());
                System.out.println("EmpPhone\t\t" + emp.getPhone());
                System.out.println("EmpAddress\t\t" + emp.getAddress());
                System.out.println("EmpBirthDate\t" + emp.getBirthDate());
                System.out.println("EmploymentDate\t" + emp.getEmploymentDate());
                System.out.println("Education\t\t" + emp.getEducation());
                Contract curr = emp.getCurrentContract();
                System.out.println("CurrentJob\t\t" + ((curr == null)?"no current job":("Position: " + curr.getPosition().getName() + " Dept: " + curr.getDepartment().getName() + " EmploymentDate: " +  curr.getEmploymentDate())));
                System.out.println(" ");

            }
        }*/

        Collection<Contract> contracts = DAOFactory.getInstance().getContractDAO().getAll();
        if (contracts != null) {
            for (Contract contract : contracts) {
                System.out.println("ContractId: " + contract.getId());
                System.out.println("ContractEmployee: " + contract.getEmployee().getName());
                System.out.println("ContractDepartment: " + contract.getDepartment().getName());
                System.out.println("ContractPosition: " + contract.getPosition().getName());
                System.out.println("ContractEmploymentDate: " + contract.getEmploymentDate());
                System.out.println("ContractQuitDate: " + contract.getQuitDate());
                System.out.println("");
            }
        }
        System.out.println("hello world");
    }
}
