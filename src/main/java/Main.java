import DAO.DAOFactory;
import models.Department;

import java.util.Collection;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Collection<Department> depts = DAOFactory.getInstance().getDepartmentDAO().getAllDepartments();
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
        }
        System.out.println("hello world");
    }
}
