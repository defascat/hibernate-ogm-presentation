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
}
