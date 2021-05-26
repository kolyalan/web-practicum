package controllers;

import DAO.DAOFactory;
import DAO.DepartmentDAO;
import DAO.EmployeeDAO;
import DAO.PositionDAO;
import models.Department;
import models.Employee;
import models.Position;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class PositionsController {

    @GetMapping("/positions")
    public String positions(Model model) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Collection<Position> positions = positionDAO.getActive();

        model.addAttribute("positions", positions);
        return "positions";
    }

    @GetMapping("/position/{id:[0-9]+}")
    public String position(@PathVariable(name="id") int id,
                           Model model) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Position position = positionDAO.getById(id);

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        Collection<Employee> employees = employeeDAO.getActiveByPosition(position);
        Collection<Employee> allEmployees = employeeDAO.getAllByPosition(position);

        model.addAttribute("position", position);
        model.addAttribute("employees", employees);
        model.addAttribute("deletable", allEmployees.isEmpty());
        model.addAttribute("achievable", !position.isArchived() && employees.isEmpty());
        return "position";
    }

    @GetMapping("/position/new")
    public String newPos(Model model) {

        model.addAttribute("position", null);
        return "position_edit";
    }

    @GetMapping("/position/edit/{id:[0-9]+}")
    public String edit(@PathVariable(name="id") int id,
                       Model model) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Position position = positionDAO.getById(id);

        model.addAttribute("position", position);
        return "position_edit";
    }

    @PostMapping({"/position/save"})
    public String deptSave(@RequestParam(name = "id", defaultValue = "-1") int id,
                           @RequestParam(name = "posName", defaultValue = "") String name,
                           @RequestParam(name = "responsibility", defaultValue = "") String responsibility,
                           RedirectAttributes redirectAttributes) {
        System.out.println("id " + id + ", name " + name + ", resp " + responsibility);

        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();

        String message = "";
        if (id == -1) {
            Position pos = new Position(name, responsibility);
            positionDAO.add(pos);
            message = "Должность успешно добавлена";
        } else {
            Position pos = positionDAO.getById(id);
            boolean changed = false;
            if (!pos.getName().equals(name)) {
                pos.setName(name);
                changed = true;
            }
            if (!pos.getResponsibility().equals(responsibility)) {
                pos.setResponsibility(responsibility);
                changed = true;
            }
            if (changed) {
                positionDAO.update(pos);
                message = "Должность успешно изменена";
            } else {
                message = "Данные не изменились";
            }
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/positions";
    }


    @GetMapping({ "/position/delete/{id:[0-9]+}"})
    public String delete(@PathVariable int id,
                         RedirectAttributes redirectAttributes) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Position position = positionDAO.getById(id);

        if (position != null) {
            positionDAO.delete(position);
            redirectAttributes.addFlashAttribute("message", "Должность успешно удалена.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Должность не найдена. Удалять нечего.");
        }
        return "redirect:/positions";
    }

    @GetMapping({ "/position/archive/{id:[0-9]+}"})
    public String archive(@PathVariable int id,
                          RedirectAttributes redirectAttributes) {
        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        Position position = positionDAO.getById(id);

        if (position != null) {
            position.setArchived(true);
            positionDAO.update(position);
            redirectAttributes.addFlashAttribute("message", "Должность успешно архивирована.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Должность не нужно архивировать. Ее и так нет.");
        }
        return "redirect:/positions";
    }

}
