package by.defascat.hibernate.ogm.test.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author andy
 */
@Embeddable
public class Address implements Serializable {
    private String city;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
