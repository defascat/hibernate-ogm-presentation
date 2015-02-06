package by.defascathibernate.ogm.test;

/**
 *
 * @author andy
 */
public class MongoTest extends BasicTest {
    @Override
    protected String getDataSource() {
        return "MONGO";
    }

    @Override
    protected String getNativeQuery(String newCity) {
        return "db.UserProfile.find({\"address.city\": \"" + newCity + "\"})";
    }
}
