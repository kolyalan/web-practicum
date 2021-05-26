package controllers;

import DAO.*;
import models.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @GetMapping("/employee/edit/{id:[0-9]+}")
    public String edit(@PathVariable(name = "id") int id, Model model) {

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Employee emp = employeeDAO.getById(id);

        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> allDepts = departmentDAO.getActive();
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Collection<Position> allPositions = positionDAO.getActive();

        model.addAttribute("employee", emp);
        model.addAttribute("allPositions", allPositions);
        model.addAttribute("allDepts", allDepts);
        return "employee_edit";
    }

    @GetMapping("/employee/new")
    public String empNew(Model model) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Collection<Position> allPositions = positionDAO.getActive();
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> allDepts = departmentDAO.getActive();

        model.addAttribute("allPositions", allPositions);
        model.addAttribute("allDepts", allDepts);
        model.addAttribute("employee", null);
        return "employee_edit";
    }

    @PostMapping({"/employee/save"})
    public String deptSave(@RequestParam(name = "id", defaultValue = "-1") int id,
                           @RequestParam(name = "empName", defaultValue = "") String name,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "birthDate", defaultValue = "") LocalDate birthDate,
                           @RequestParam(name = "education", defaultValue = "") String education,
                           @RequestParam(name = "phone", defaultValue = "") String phone,
                           @RequestParam(name = "address", defaultValue = "") String address,
                           @RequestParam(name = "position", defaultValue = "-1") int posId,
                           @RequestParam(name = "dept", defaultValue = "-1") int deptId,
                           RedirectAttributes redirectAttributes) {
        System.out.println("id " + id + ", name " + name + ", birthDate " + birthDate + ", education " + education +
                            ", phone " + phone + ", address " + address + ", position " + posId + ", dept " + deptId);
        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        ContractDAO contractDAO = DAOFactory.getInstance().getContractDAO();
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();

        String message = "";
        if (id == -1) {
            Employee emp = new Employee(name, birthDate, DegreeType.valueOf(education), address, phone);
            employeeDAO.add(emp);
            message += "Работник успешно добавлен.";
            if (posId != -1 && deptId != -1) {
                Department dept = departmentDAO.getById(deptId);
                Position pos = positionDAO.getById(posId);
                Contract contr = new Contract(emp, dept, pos, LocalDate.now(), null);
                contractDAO.add(contr);
                message += " И устроен на работу.";
            }
        } else {
            Employee emp = employeeDAO.getById(id);
            boolean changed = false;
            if (!emp.getName().equals(name)) {
                emp.setName(name);
                changed = true;
            }
            if (!emp.getBirthDate().equals(birthDate)) {
                emp.setBirthDate(birthDate);
                changed = true;
            }
            if (!emp.getEducation().equals(DegreeType.valueOf(education))) {
                emp.setEducation(DegreeType.valueOf(education));
                changed = true;
            }
            if (!emp.getPhone().equals(phone)) {
                emp.setPhone(phone);
                changed = true;
            }
            if (!emp.getAddress().equals(address)) {
                emp.setAddress(address);
                changed = true;
            }
            if (changed) {
                employeeDAO.update(emp);
                message += "Личные данные успешно обновлены. ";
            }
            if ((emp.getCurrentContract() == null && posId != -1 && deptId != -1) || (
                    emp.getCurrentContract() != null && (
                    emp.getCurrentContract().getPosition().getId() != posId ||
                    emp.getCurrentContract().getDepartment().getId() != deptId))) {
                Contract current = emp.getCurrentContract();
                if (current != null) {
                    current.setQuitDate(LocalDate.now());
                    contractDAO.update(current);
                }

                Department dept = departmentDAO.getById(deptId);
                Position pos = positionDAO.getById(posId);
                Contract newContract = new Contract(emp, dept, pos, LocalDate.now(), null);
                contractDAO.add(newContract);
                message += "Место работы успешно обновлено.";
            } else {
                if (!changed) {
                    message += "Ничего не изменилось.";
                }
            }
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/employees";
    }

    @GetMapping({ "/employee/dismiss/{id:[0-9]+}"})
    public String archive(@PathVariable int id,
                          RedirectAttributes redirectAttributes) {
        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Employee employee = employeeDAO.getById(id);

        ContractDAO contractDAO = DAOFactory.getInstance().getContractDAO();

        if (employee != null) {
            Contract contr = employee.getCurrentContract();
            if (contr != null) {
                contr.setQuitDate(LocalDate.now());
                contractDAO.update(contr);
                redirectAttributes.addFlashAttribute("message", "Сотрудник успешно уволен");
            } else {
                redirectAttributes.addFlashAttribute("message", "Данный сотрудник сейчас не занимает никакую должность");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Такого сотрудника нет. Уволить не получилось.");
        }
        return "redirect:/employees";
    }
}
