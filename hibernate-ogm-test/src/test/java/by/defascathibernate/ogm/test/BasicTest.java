package by.defascathibernate.ogm.test;

import by.defascat.hibernate.ogm.test.entity.AccelerometerRecord;
import by.defascat.hibernate.ogm.test.entity.Address;
import by.defascat.hibernate.ogm.test.entity.UserProfile;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author andy
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BasicTest {
    protected EntityManager em;
    protected EntityManagerFactory emf;
    
    protected abstract String getDataSource();
    protected abstract String getNativeQuery(final String newCity);

    @After
    public void after() {
        em.close();
        emf.close();
    }

    @Before
    public void setup() {
        emf = Persistence.createEntityManagerFactory(getDataSource());
        em = emf.createEntityManager();
    }

    @Test
    public void test1InsertProfile() throws NoSuchAlgorithmException, IOException {
        EntityTransaction tm = em.getTransaction();
        tm.begin();
        final UserProfile userProfile = new UserProfile();
        userProfile.setName("testuser");
        userProfile.setPassword("123456");
        userProfile.setAvatar(FileUtils.readFileToByteArray(new File("avatar.png")));
        final Address address = new Address();
        address.setCity("Minsk");
        address.setCountry("Belarus");
        userProfile.setAddress(address);
        final AccelerometerRecord accelerometerRecord = new AccelerometerRecord();
        accelerometerRecord.setLatitude(53.9);
        accelerometerRecord.setLongitude(27.5667);
        accelerometerRecord.setX(0.1);
        accelerometerRecord.setY(0.2);
        accelerometerRecord.setZ(0.3);
        accelerometerRecord.setUserProfile(userProfile);
        userProfile.setRecords(Arrays.asList(accelerometerRecord));
        em.persist(userProfile);
        em.persist(accelerometerRecord);
        tm.commit();
    }

    @Test(expected = RollbackException.class)
    public void test2InsertDuplicate() throws NoSuchAlgorithmException, IOException {
        EntityTransaction tm = em.getTransaction();
        tm.begin();
        final UserProfile userProfile = new UserProfile();
        userProfile.setName("testuser");
        userProfile.setPassword("123456");
        final Address address = new Address();
        address.setCity("Minsk");
        address.setCountry("Belarus");
        userProfile.setAddress(address);
        em.persist(userProfile);
        tm.commit();
    }

    @Test
    public void test3FindItemJPQL() throws NoSuchAlgorithmException, IOException {
        final List<UserProfile> items = em.createQuery("from UserProfile p where p.name = 'testuser'").getResultList();
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("testuser", items.get(0).getName());
    }

    @Test
    public void test4UpdateItem() throws NoSuchAlgorithmException, IOException {
        final UserProfile user = em.find(UserProfile.class, "testuser");
        Assert.assertEquals("Minsk", user.getAddress().getCity());
        
        EntityTransaction tm = em.getTransaction();
        tm.begin();
        user.getAddress().setCity("Borovlyany");
        em.merge(user);
        tm.commit();
    }

    @Test
    public void test5FindNative() throws NoSuchAlgorithmException, IOException {
        final String newCity = "Borovlyany";
        final List<UserProfile> items = em.createNativeQuery(getNativeQuery(newCity), UserProfile.class).getResultList();
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("testuser", items.get(0).getName());
        Assert.assertEquals(newCity, items.get(0).getAddress().getCity());
    }

    @Test
    public void test5aFindNativeWrong() throws NoSuchAlgorithmException, IOException {
        final List<UserProfile> items = em.createNativeQuery(getNativeQuery("Lesnoy"), UserProfile.class).getResultList();
        Assert.assertEquals(0, items.size());
    }

    @Test
    public void test6RemoveItem() throws NoSuchAlgorithmException, IOException {
        final UserProfile user = em.find(UserProfile.class, "testuser");
        EntityTransaction tm = em.getTransaction();
        tm.begin();
        em.remove(user);
        tm.commit();
    }

    @Test
    public void test7FindItemAfterRemoval() throws NoSuchAlgorithmException, IOException {
        final List<UserProfile> items = em.createQuery("from UserProfile p where p.name = 'testuser'").getResultList();
        Assert.assertEquals(0, items.size());
    }

}
