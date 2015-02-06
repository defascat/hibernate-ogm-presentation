package by.defascathibernate.ogm.test;

/**
 *
 * @author andy
 */
public class Neo4JTest extends BasicTest {
    @Override
    protected String getDataSource() {
        return "NEO4J";
    }

    @Override
    protected String getNativeQuery(String newCity) {
        return "MATCH (p:UserProfile) WHERE p.address.city='" + newCity + "' RETURN p";
    }
    
}
