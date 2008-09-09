package org.open18.partner.model;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Tournament implements Serializable {
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private String hostFacilityName;

    private String hostFacilityLocation;

    private Date entryDeadline;

    private String sponsers;

    private String benefitingCharities;

    private String summary;

    private String contact;

    private Double entryFee;

    private String purse;

    private String phone;

    private String website;

    private String email;
    
	@Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

	@Temporal(TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

	@Temporal(TemporalType.DATE)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate;
    }

    public String getHostFacilityName() {
        return hostFacilityName;
    }

    public void setHostFacilityName( String hostFacilityName ) {
        this.hostFacilityName = hostFacilityName;
    }

    public String getHostFacilityLocation() {
        return hostFacilityLocation;
    }

    public void setHostFacilityLocation( String hostFacilityLocation ) {
        this.hostFacilityLocation = hostFacilityLocation;
    }

	@Temporal(TemporalType.DATE)
    public Date getEntryDeadline() {
        return entryDeadline;
    }

    public void setEntryDeadline( Date entryDeadline ) {
        this.entryDeadline = entryDeadline;
    }

    public String getSponsers() {
        return sponsers;
    }

    public void setSponsers( String sponser ) {
        this.sponsers = sponser;
    }

    public String getBenefitingCharities() {
        return benefitingCharities;
    }

    public void setBenefitingCharities( String benefitingCharities ) {
        this.benefitingCharities = benefitingCharities;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary( String summary ) {
        this.summary = summary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact( String contact ) {
        this.contact = contact;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee( Double entryFee ) {
        this.entryFee = entryFee;
    }

    public String getPurse() {
        return purse;
    }

    public void setPurse( String purse ) {
        this.purse = purse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite( String website ) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

}
