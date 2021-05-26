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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class DepartmentsController {
    @GetMapping({"/", "/departments"})
    public String depts(@RequestParam(name="archived", defaultValue = "0") boolean archived,  Model model) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> allDepts = null;
        if (archived) {
            allDepts = departmentDAO.getAll();
        } else {
            allDepts = departmentDAO.getActive();
        }
        List<Map.Entry<String, Department>> departments = new ArrayList<>();

        for (Department head :allDepts) {
            if (head.getHeadDepartment() == null) {
                toList(departments, head, "", archived);
            }
        }
        model.addAttribute("departments", departments);
        model.addAttribute("archive", archived);
        return "departments";
    }

    @GetMapping({ "/department/{id:[0-9]+}"})
    public String dept(@PathVariable int id, Model model) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        models.Department dept = departmentDAO.getById(id);

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Collection<Employee> employees = employeeDAO.getActiveByDepartment(dept);
        Collection<Employee> allEmployees = employeeDAO.getAllByDepartment(dept);

        model.addAttribute("dept", dept);
        model.addAttribute("employees", employees);
        model.addAttribute("deletable", dept.getChildDeps().isEmpty() && allEmployees.isEmpty());
        model.addAttribute("achievable", !dept.isArchived() && dept.getActiveChildDeps().isEmpty() && employees.isEmpty());
        return "department";
    }

    @GetMapping({ "/department/new"})
    public String deptNew(@RequestParam(name="head", defaultValue = "-1") int head_id,
                          @RequestParam(name="child", defaultValue = "-1") int child_id,
                          Model model) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> activeDepartments = departmentDAO.getActive();

        Department head = null;
        if (head_id != -1) {
            head = departmentDAO.getById(head_id);
        }

        Department child = null;
        if (child_id != -1) {
            child = departmentDAO.getById(child_id);
        }

        model.addAttribute("dept", null);
        model.addAttribute("defaultHead", head);
        model.addAttribute("child", child);
        model.addAttribute("allDepts", activeDepartments);
        return "department_edit";
    }

    @GetMapping({ "/department/edit/{id:[0-9]+}"})
    public String deptEdit(@PathVariable int id, Model model) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Collection<Department> activeDepartments = departmentDAO.getActive();
        Department dept = departmentDAO.getById(id);

        activeDepartments.remove(dept);

        model.addAttribute("dept", dept);
        model.addAttribute("defaultHead", dept.getHeadDepartment());
        model.addAttribute("childId", null);
        model.addAttribute("allDepts", activeDepartments);
        return "department_edit";
    }

    @PostMapping({"/department/save"})
    public String deptSave(@RequestParam(name = "id", defaultValue = "-1") int id,
                           @RequestParam(name = "deptName", defaultValue = "") String name,
                           @RequestParam(name = "headDept", defaultValue = "-1") int headId,
                           @RequestParam(name = "childId", defaultValue = "-1") int childId,
                           RedirectAttributes redirectAttributes) {
        System.out.println("id " + id + ", name " + name + ", headDept " + headId + ", childId " + childId);
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();

        if (id == -1) {
            Department head = null;
            if (headId > 0) {
                head = departmentDAO.getById(headId);
            }
            Department dept = new Department(name, head);
            departmentDAO.add(dept);
            if (childId != -1) {
                Department child = departmentDAO.getById(childId);
                child.setHeadDepartment(dept);
                departmentDAO.update(child);
            }
            redirectAttributes.addFlashAttribute("message", "Отдел успешно создан");
        } else {
            Department dept = departmentDAO.getById(id);
            boolean changed = false;
            if (!name.equals("") && !dept.getName().equals(name)) {
                dept.setName(name);
                changed = true;
            }
            Department head = null;
            if (headId > 0) {
                if (dept.getHeadDepartment() == null || headId != dept.getHeadDepartment().getId()) {
                    head = departmentDAO.getById(headId);
                    dept.setHeadDepartment(head);
                    changed = true;
                }
            } else if (headId == 0 && dept.getHeadDepartment() != null) {
                dept.setHeadDepartment(null);
                changed = true;
            }
            if (changed) {
                departmentDAO.update(dept);
                redirectAttributes.addFlashAttribute("message", "Отдел успешно обновлен!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Данные не изменились. Обновление не понадобилось.");
            }
        }
        return "redirect:/departments";
    }

    @GetMapping({ "/department/delete/{id:[0-9]+}"})
    public String delete(@PathVariable int id,
                       RedirectAttributes redirectAttributes) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Department dept = departmentDAO.getById(id);

        if (dept != null) {
            departmentDAO.delete(dept);
            redirectAttributes.addFlashAttribute("message", "Отдел успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("message", "Отдел не найден. Удалять нечего.");
        }
        return "redirect:/departments";
    }

    @GetMapping({ "/department/archive/{id:[0-9]+}"})
    public String archive(@PathVariable int id,
                         RedirectAttributes redirectAttributes) {
        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        Department dept = departmentDAO.getById(id);

        if (dept != null) {
            dept.setArchived(true);
            departmentDAO.update(dept);
            redirectAttributes.addFlashAttribute("message", "Отдел успешно архивирован");
        } else {
            redirectAttributes.addFlashAttribute("message", "Отдел не нужно архивировать. Его и так нет.");
        }
        return "redirect:/departments";
    }

    private void toList(List<Map.Entry<String, Department>> departments, Department head, String level, boolean archive) {
        if (head == null) return;
        if (!archive && head.isArchived()) return;
        departments.add(new AbstractMap.SimpleEntry<>(level, head));
        if (head.getChildDeps() != null) {
            for (Department child : head.getChildDeps()) {
                toList(departments, child, level + "&nbsp; &nbsp; &nbsp; &nbsp; ", archive);
            }
        }
    }
}
