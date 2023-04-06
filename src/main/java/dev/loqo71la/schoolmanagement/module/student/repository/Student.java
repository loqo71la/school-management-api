package dev.loqo71la.schoolmanagement.module.student.repository;

import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.common.service.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Student implements Model {

    /**
     * Stores the id of the student.
     */
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;

    /**
     * Stores the unique idNo of student.
     */
    @Column(unique = true, length = 100)
    private String idNo;

    /**
     * Stores the student first name.
     */
    @Column(length = 100)
    private String firstName;

    /**
     * Stores the student last name.
     */
    @Column(length = 100)
    private String lastName;

    /**
     * Stores the id of the gender.
     */
    private int gender;

    /**
     * Stores the student type.
     */
    @Column(length = 5)
    private String type;

    /**
     * Store the latitude coordinate.
     */
    @Column(columnDefinition = "Decimal(12,7)")
    private double latitude;

    /**
     * Stores the longitude coordinate.
     */
    @Column(columnDefinition = "Decimal(12,7)")
    private double longitude;

    /**
     * Stores the username who created the model.
     */
    @Column(length = 50)
    private String createdBy;

    /**
     * Stores the time it was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * Stores the username who updated the model.
     */
    @Column(length = 50)
    private String updatedBy;

    /**
     * Stores the time it was updated.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * Stores the list of assigned clazz.
     */
    @ManyToMany(mappedBy = "studentList")
    private List<Clazz> clazzList;

    /**
     * Returns the value of 'id' property.
     *
     * @return the id.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Sets the value of 'id' property.
     *
     * @param id to be set.
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the value of 'idNo' property.
     *
     * @return the student idNo.
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Sets the value of 'idNo' property.
     *
     * @param idNo to be set.
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Returns the value of 'firstName' property.
     *
     * @return the student first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of 'firstName' property.
     *
     * @param firstName to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the value of 'lastName' property.
     *
     * @return the student last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of 'lastName' property.
     *
     * @param lastName to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the value of 'gender' property.
     *
     * @return the id of the gender.
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets the value of 'gender' property.
     *
     * @param gender to be set.
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * Returns the value of 'type' property.
     *
     * @return the student type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of 'type' property.
     *
     * @param type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the value of 'latitude' property.
     *
     * @return the latitude coordinate.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of 'latitude' property.
     *
     * @param latitude to be set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the value of 'longitude' property.
     *
     * @return the longitude coordinate.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of 'longitude' property.
     *
     * @param longitude to be set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns the value of 'createdBy' property.
     *
     * @return the username who created the model.
     */
    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of 'createdBy' property.
     *
     * @param createdBy to be set.
     */
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the value of 'createdAt' property.
     *
     * @return the time it was created.
     */
    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of 'createdAt' property.
     *
     * @param createdAt to be set.
     */
    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the value of 'updatedBy' property.
     *
     * @return the username who updated the model.
     */
    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the value of 'updatedBy' property.
     *
     * @param updatedBy to be set.
     */
    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Returns the value of 'updatedAt' property.
     *
     * @return the time it was updated.
     */
    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the value of 'updatedAt' property.
     *
     * @param updatedAt to be set.
     */
    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the value of 'clazzList' property.
     *
     * @return the list of clazzes.
     */
    public List<Clazz> getClazzList() {
        return clazzList;
    }

    /**
     * Sets the value of 'clazzList' property.
     *
     * @param clazzList to be set.
     */
    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }
}
