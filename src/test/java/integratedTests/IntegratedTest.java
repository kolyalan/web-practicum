package integratedTests;

import DAO.*;
import models.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IntegratedTest {
    protected final String appURL = "http://localhost:8080/";
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(1500, 1000));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(appURL);
    }

    @Test
    public void departmentAddEditArchiveRemoveTest() throws InterruptedException {
        Department dept = new Department("Просто отдел", null);


        Assert.assertEquals(driver.getTitle(), "Department list");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("departmentAddButton")).click();
        Assert.assertEquals(driver.getTitle(), "Department edit");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("deptNameInput")).sendKeys(dept.getName());
        Select headDeptDropdown = new Select(driver.findElement(By.id("deptHeadInput")));
        String headName = headDeptDropdown.getOptions().get(1).getText();
        headDeptDropdown.selectByIndex(1);
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("deptSaveButton")).click();

        Assert.assertEquals(driver.getTitle(), "Department list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Отдел успешно создан");

        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("messageClose")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.partialLinkText(dept.getName())).click();
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("deptEditButton")).click();

        driver.findElement(By.id("deptNameInput")).sendKeys(dept.getName());
        headDeptDropdown = new Select(driver.findElement(By.id("deptHeadInput")));
        headName = headDeptDropdown.getOptions().get(2).getText();
        headDeptDropdown.selectByIndex(2);

        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("deptSaveButton")).click();

        Assert.assertEquals(driver.getTitle(), "Department list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Отдел успешно обновлен!");

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("messageClose")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.partialLinkText(dept.getName())).click();
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("deptArchiveButton")).click();
        Assert.assertEquals(driver.getTitle(), "Department list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Отдел успешно архивирован");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("showArchive")).click();
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.partialLinkText(dept.getName())).click();
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("deptDeleteButton")).click();
        Assert.assertEquals(driver.getTitle(), "Department list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Отдел успешно удален");
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("messageClose")).click();
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void testEmployee() throws InterruptedException {
        Employee employee = new Employee("Иванов Иван Иванович", LocalDate.parse("19.11.1987",DateTimeFormatter.ofPattern("dd.MM.yyyy")), DegreeType.BACHELOR, "Земля", "+7(157)163-45-68");

        driver.findElement(By.id("menuEmps")).click();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(driver.getTitle(), "Employees list");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("addEmployeeButton")).click();
        Assert.assertEquals(driver.getTitle(), "Employee edit");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("name")).sendKeys(employee.getName());
        driver.findElement(By.id("birthDate")).sendKeys("1987-11-19");
        Select educationDropdown = new Select(driver.findElement(By.id("education")));
        //String edName = educationDropdown.getOptions().get(2).getText();
        educationDropdown.selectByIndex(2);
        driver.findElement(By.id("phone")).sendKeys(employee.getPhone());
        driver.findElement(By.id("address")).sendKeys(employee.getAddress());
        driver.findElement(By.id("empSaveButton")).click();
        TimeUnit.SECONDS.sleep(1);

        Assert.assertEquals(driver.getTitle(), "Employees list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Работник успешно добавлен.");
        driver.findElement(By.id("messageClose")).click();

        List<WebElement> table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                cell.click();
                break;
            }
        }
        TimeUnit.SECONDS.sleep(1);

        Assert.assertEquals(driver.findElement(By.id("empName")).getText(), employee.getName());
        Assert.assertEquals(driver.findElement(By.id("empBirthDate")).getText(), employee.getBirthDate().toString());
        Assert.assertEquals(driver.findElement(By.id("empEducation")).getText(), employee.getEducation().getRussian());
        Assert.assertEquals(driver.findElement(By.id("empPhone")).getText(), employee.getPhone());
        Assert.assertEquals(driver.findElement(By.id("empAddress")).getText(), employee.getAddress());

        driver.findElement(By.id("empEdit")).click();

        Select pos = new Select(driver.findElement(By.id("position")));
        String posName = pos.getOptions().get(4).getText();
        pos.selectByIndex(4);

        Select dept = new Select(driver.findElement(By.id("department")));
        String deptName = dept.getOptions().get(8).getText();
        dept.selectByIndex(8);

        driver.findElement(By.id("empSaveButton")).click();

        Assert.assertEquals(driver.getTitle(), "Employees list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Место работы успешно обновлено.");
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("messageClose")).click();

        driver.findElement(By.id("searchName")).sendKeys(employee.getName());
        driver.findElement(By.id("search")).click();

        boolean found = false;
        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("searchName")).sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        Select searchEd = new Select(driver.findElement(By.id("searchEd")));
        searchEd.selectByValue(employee.getEducation().toString());
        driver.findElement(By.id("search")).click();

        found = false;
        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        TimeUnit.SECONDS.sleep(1);

        searchEd = new Select(driver.findElement(By.id("searchEd")));
        searchEd.selectByIndex(0);
        Select searchDept = new Select(driver.findElement(By.id("searchDept")));
        searchDept.selectByIndex(8);
        driver.findElement(By.id("search")).click();

        found = false;
        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        TimeUnit.SECONDS.sleep(1);

        searchDept = new Select(driver.findElement(By.id("searchDept")));
        searchDept.selectByIndex(0);
        Select searchPos = new Select(driver.findElement(By.id("searchPos")));
        searchPos.selectByIndex(4);
        driver.findElement(By.id("search")).click();

        found = false;
        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        TimeUnit.SECONDS.sleep(1);

        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                cell.click();
                break;
            }
        }
        TimeUnit.SECONDS.sleep(1);

        Assert.assertEquals(driver.getTitle(), "Employee Page");
        driver.findElement(By.id("empDismiss")).click();

        Assert.assertEquals(driver.getTitle(), "Employees list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Сотрудник успешно уволен");

        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().contains(employee.getName())){
                cell.click();
                break;
            }
        }

        String url = driver.getCurrentUrl();
        int id = Integer.parseInt(url.substring(url.lastIndexOf("/")+1));

        EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
        ContractDAO contractDAO = DAOFactory.getInstance().getContractDAO();
        Employee emp = employeeDAO.getById(id);

        for (Contract contract : emp.getContracts()) {
            contractDAO.delete(contract);
        }
        employeeDAO.delete(emp);
    }

    @Test
    public void testPositions() throws InterruptedException {
        Position position = new Position("Просто должность", "и должностные права...");

        driver.findElement(By.id("menuPos")).click();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(driver.getTitle(), "Positions list");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("addPositionButton")).click();
        Assert.assertEquals(driver.getTitle(), "Position edit");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("posName")).sendKeys(position.getName());
        driver.findElement(By.id("posResp")).sendKeys(position.getResponsibility());
        driver.findElement(By.id("posSave")).click();

        Assert.assertEquals(driver.getTitle(), "Positions list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Должность успешно добавлена");
        TimeUnit.SECONDS.sleep(1);

        List<WebElement> table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().equals(position.getName())) {
                cell.click();
                break;
            }
        }

        Assert.assertEquals(driver.getTitle(), "Position page");
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(driver.findElement(By.id("posName")).getText(), position.getName());
        Assert.assertEquals(driver.findElement(By.id("posResp")).getText(), position.getResponsibility());

        driver.findElement(By.id("posEdit")).click();
        Assert.assertEquals(driver.getTitle(), "Position edit");
        TimeUnit.SECONDS.sleep(1);

        position.setName(position.getName() + " (честно)");
        driver.findElement(By.id("posName")).sendKeys(" (честно)");
        driver.findElement(By.id("posResp")).sendKeys(" И больше ничего. Прям совсем! Работа мечты!");
        driver.findElement(By.id("posSave")).click();

        Assert.assertEquals(driver.getTitle(), "Positions list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Должность успешно изменена");
        TimeUnit.SECONDS.sleep(1);

        table = driver.findElements(By.tagName("th"));
        for (WebElement cell : table) {
            if (cell.getText().equals(position.getName())) {
                cell.click();
                break;
            }
        }

        Assert.assertEquals(driver.getTitle(), "Position page");
        TimeUnit.SECONDS.sleep(1);

        String url = driver.getCurrentUrl();
        int id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));

        driver.findElement(By.id("posArchive")).click();
        Assert.assertEquals(driver.getTitle(), "Positions list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Должность успешно архивирована.");
        TimeUnit.SECONDS.sleep(1);

        driver.get(appURL + "position/" + id);
        Assert.assertEquals(driver.getTitle(), "Position page");
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.id("posDelete")).click();
        Assert.assertEquals(driver.getTitle(), "Positions list");
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "Должность успешно удалена.");
        TimeUnit.SECONDS.sleep(1);

    }

    @AfterClass
    public void clear() {
        driver.quit();
    }

}
