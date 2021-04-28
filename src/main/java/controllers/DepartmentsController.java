package controllers;

import DAO.DAOFactory;
import DAO.DepartmentDAO;
import models.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DepartmentsController {
    @GetMapping({"/", "/departments"})
    public String greeting(Model model) {
        //model.addAttribute("name", name);

        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        models.Department head = departmentDAO.getById(1);
        List<Map.Entry<String, Department>> departments = new ArrayList<>();

        toList(departments, head, "");
        System.out.println(head);
        model.addAttribute("departments", departments);
        return "departments";
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
