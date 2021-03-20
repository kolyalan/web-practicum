import DAO.DAOFactory;
import DAO.DepartmentDAO;
import models.Department;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DepartmentDAOTest {

    @Test
    public void testDepartment() {
        Department head = new Department("Test head_dep", null);
        Department child1 = new Department("Test ch1_dep", head);
        Department child2 = new Department("Test ch2_dep", head);

        DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();
        departmentDAO.add(head);
        departmentDAO.add(child1);
        departmentDAO.add(child2);

        Assert.assertNotEquals(head.getId(), 0);
        Assert.assertNotEquals(child1.getId(), 0);
        Assert.assertNotEquals(child2.getId(), 0);
        Assert.assertEquals(head.getId()+1, child1.getId());
        Assert.assertEquals(child1.getId()+1, child2.getId());

        Department head2 = departmentDAO.getById(head.getId());
        Department child12 = departmentDAO.getById(child1.getId());
        Department child22 = departmentDAO.getById(child2.getId());

        Assert.assertEquals(head, head2);
        Assert.assertEquals(child1, child12);
        Assert.assertEquals(child2, child22);

        head = head2;
        child1 = child12;
        child2 = child22;

        child2.setHeadDepartment(child1);
        departmentDAO.update(child2);

        child2 = departmentDAO.getById(child2.getId());

        Assert.assertEquals(child2.getHeadDepartment(), child1);
        child1 = departmentDAO.getById(child1.getId());
        Assert.assertTrue(child1.getChildDeps().contains(child2));

        departmentDAO.delete(child2);
        departmentDAO.delete(child1);
        departmentDAO.delete(head);
    }
}
