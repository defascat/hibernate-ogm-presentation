package by.defascathibernate.ogm.test;

/**
 *
 * @author andy
 */
public class CouchTest extends BasicTest {

    @Override
    protected String getDataSource() {
        return "COUCH";
    }

    @Override
    protected String getNativeQuery(String newCity) {
        return "select * from UserProfile where address.city = '" + newCity + "'";
    }
}
