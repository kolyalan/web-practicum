import DAO.DAOFactory;
import DAO.PositionDAO;
import models.Position;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositionDAOTest {

    @Test
    public void testPosition() {
        Position myPos = new Position("Менеджер по работе с менеджерами", "Следит за работой всех остальных менеджеров, включая себя.");

        PositionDAO positionDAO = DAOFactory.getInstance().getPositionDAO();
        positionDAO.add(myPos);

        Position myPos2 = positionDAO.getById(myPos.getId());

        Assert.assertEquals(myPos, myPos2);

        myPos = myPos2;

        myPos.setResponsibility(myPos.getResponsibility() + " Особенно себя.");
        positionDAO.update(myPos);

        myPos2 = positionDAO.getById(myPos.getId());

        Assert.assertEquals(myPos, myPos2);

        positionDAO.delete(myPos);
    }

}
