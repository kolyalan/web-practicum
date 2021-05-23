package controllers;

import DAO.DAOFactory;
import DAO.DepartmentDAO;
import DAO.EmployeeDAO;
import DAO.PositionDAO;
import models.DegreeType;
import models.Department;
import models.Employee;
import models.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

@Controller
public class EmployeesController {

    @GetMapping("/employees")
    public String employees(@RequestParam(name = "name", defaultValue = "") String name,
                            @RequestParam(name = "education", defaultValue = "") String education,
                            @RequestParam(name = "dept", defaultValue = "-1") int deptId,
                            @RequestParam(name = "position", defaultValue = "-1") int posId,
                            Model model) {

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Collection<Employee> employees = employeeDAO.getAll();
        Collection<Employee> employeesFiltered = new LinkedList<>();

        for (Employee emp : employees) {
            if (!name.equals("") && !emp.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                continue;
            }
            if (!education.equals("") && !emp.getEducation().equals(DegreeType.valueOf(education))) {
                continue;
            }
            if (deptId != -1 && (emp.getCurrentContract() == null || emp.getCurrentContract().getDepartment().getId() != deptId)) {
                continue;
            }
            if (posId != -1 && (emp.getCurrentContract() == null || emp.getCurrentContract().getPosition().getId() != posId)) {
                continue;
            }
            employeesFiltered.add(emp);
        }

        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> allDepts = departmentDAO.getActive();

        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Collection<Position> allPositions = positionDAO.getActive();

        model.addAttribute("nameQ", name);
        model.addAttribute("educationQ", education);
        model.addAttribute("deptQ", deptId);
        model.addAttribute("positionQ", posId);

        model.addAttribute("employees", employeesFiltered);
        model.addAttribute("allDepts", allDepts);
        model.addAttribute("allPositions", allPositions);

        return "employees";
    }

    @GetMapping("/employee/{id:[0-9]+}")
    public String employee(@PathVariable(name = "id") int id, Model model) {

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Employee emp = employeeDAO.getById(id);

        model.addAttribute("employee", emp);
        return "employee";
    }
}
