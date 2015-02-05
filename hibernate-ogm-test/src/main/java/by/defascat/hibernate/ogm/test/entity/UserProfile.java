package by.defascat.hibernate.ogm.test.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author andy
 */
@Entity
public class UserProfile implements Serializable {
    @Id
    @Column(length = 32)
    private String name;
    
    @Column(nullable = false)
    private byte[] password;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;
    
    @OneToMany(orphanRemoval = true)
    private List<AccelerometerRecord> records;

    @Embedded
    private Address address;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPassword() {
        return password;
    }


    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = MessageDigest.getInstance("MD5").digest((name + password).getBytes());
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public List<AccelerometerRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AccelerometerRecord> records) {
        this.records = records;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
