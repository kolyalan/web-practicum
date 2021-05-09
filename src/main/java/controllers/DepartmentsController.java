package controllers;

import DAO.DAOFactory;
import DAO.DepartmentDAO;
import DAO.EmployeeDAO;
import models.Department;
import models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class DepartmentsController {
    @GetMapping({"/", "/departments"})
    public String depts(Model model) {
        //model.addAttribute("name", name);

        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        models.Department head = departmentDAO.getById(1);
        List<Map.Entry<String, Department>> departments = new ArrayList<>();

        toList(departments, head, "");
        System.out.println(head);
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping({ "/department/{id}"})
    public String dept(@PathVariable int id, Model model) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        models.Department dept = departmentDAO.getById(id);

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Collection<Employee> employees = employeeDAO.getByDepartment(dept);

        model.addAttribute("dept", dept);
        model.addAttribute("employees", employees);
        return "department";
    }

    private void toList(List<Map.Entry<String, Department>> departments, Department head, String level) {
        if (head == null) return;
        departments.add(new AbstractMap.SimpleEntry<>(level, head));
        if (head.getChildDeps() != null) {
            for (Department child : head.getChildDeps()) {
                toList(departments, child, level + "&nbsp; &nbsp; &nbsp; &nbsp; ");
            }
        }
    }
}
