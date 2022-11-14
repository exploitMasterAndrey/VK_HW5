package dao;

import model.Organization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationDaoTest {
    private static Dao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new OrganizationDao();
    }

    @Test
    void create() {
        Organization organization = new Organization("test", 11l, 666);
        dao.create(organization);
        assertEquals(organization, dao.read(organization.getINN().intValue()));
        dao.delete(organization);
    }

    @Test
    void read() {
        Organization organization = new Organization("test", 11l, 666);
        dao.create(organization);
        assertEquals(organization, dao.read(organization.getINN().intValue()));
        dao.delete(organization);
    }

    @Test
    void update() {
        Organization organization = new Organization("test", 111222333l, 666);
        dao.create(organization);
        Organization organization1 = new Organization("test1", 111222333l, 666);
        dao.update(organization1);
        assertEquals(organization1, dao.read(organization1.getINN().intValue()));
        dao.delete(organization1);
    }

    @Test
    void delete() {
        Organization organization = new Organization("test", 111222333l, 666);
        dao.create(organization);
        dao.delete(organization);
        assertNotEquals(organization, dao.read(organization.getINN().intValue()));
    }
}