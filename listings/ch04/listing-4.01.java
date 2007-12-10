package org.open18.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.jboss.seam.annotations.*;
import org.jboss.seam.ScopeType;

@Entity
@PrimaryKeyJoinColumn(name="MEMBER_ID")
@Table(name = "GOLFER")
@Name("newGolfer")
@Scope(ScopeType.EVENT)
public class Golfer extends Member {
    private String name;
    private Gender gender;
    private Date dateJoined;
    private Date dateOfBirth;
    private String specialty;
    private String proStatus;

    @Column(name = "name", nullable = false)
    @Length(max = 40)
    @NotNull
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "joined", nullable = false)
    @NotNull
    public Date getDateJoined() {
        return dateJoined;
    }
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Column(name = "pro_status")
    public String getProStatus() {
        return proStatus;
    }
    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
}
