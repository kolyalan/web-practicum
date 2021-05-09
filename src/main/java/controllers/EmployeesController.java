package controllers;

import DAO.DAOFactory;
import DAO.EmployeeDAO;
import models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class EmployeesController {

    @GetMapping("/employees")
    public String employees(Model model) {

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Collection<Employee> employees = employeeDAO.getAll();

        model.addAttribute("employees", employees);

        return "employees";
    }
}
