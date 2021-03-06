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
import javax.persistence.Transient;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 *
 * @author andy
 */
@Entity
@Indexed(index="indexes/profiles") // Required for couch search
public class UserProfile implements Serializable {
    @Id
    @Column(length = 32)
    private String name;
    
    // @Column(nullable = false)
    /* 
        Couch fails when retreiving object 
        java.lang.IllegalArgumentException: Can not set [B field by.defascat.hibernate.ogm.test.entity.UserProfile.password to java.lang.String
    */
    @Transient
    private byte[] password;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;
    
    @OneToMany(orphanRemoval = true)
    private List<AccelerometerRecord> records;

    @Embedded
    @IndexedEmbedded // Couch requirement
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
