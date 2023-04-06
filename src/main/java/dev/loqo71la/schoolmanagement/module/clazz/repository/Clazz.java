package dev.loqo71la.schoolmanagement.module.clazz.repository;

import dev.loqo71la.schoolmanagement.module.common.service.Model;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Clazz implements Model {

    /**
     * Stores the id of the clazz.
     */
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;

    /**
     * Stores the unique clazz code.
     */
    @Column(unique = true, length = 100)
    private String code;

    /**
     * Stores the clazz title.
     */
    @Column(length = 100)
    private String title;

    /**
     * Stores the clazz type.
     */
    @Column(length = 5)
    private String type;

    /**
     * Stores the clazz description.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Stores the total number of assigned students.
     */
    private int totalAssigned;

    /**
     * Stores the id of the assigned teacher.
     */
    private UUID teacherId;

    /**
     * Stores the state of the clazz.
     */
    private boolean enable;

    /**
     * Store the latitude coordinate.
     */
    @Column(columnDefinition="Decimal(12,7)")
    private double latitude;

    /**
     * Stores the longitude coordinate.
     */
    @Column(columnDefinition="Decimal(12,7)")
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
     * Stores the list of students.
     */
    @ManyToMany
    @JoinTable(
            name = "clazz_student",
            joinColumns = @JoinColumn(name = "clazz_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentList;

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
     * Returns the value of 'code' property.
     *
     * @return the clazz code.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the value of 'type' property.
     *
     * @return the clazz type.
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
     * Returns the value of 'title' property.
     *
     * @return the clazz title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of 'title' property.
     *
     * @param title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the value of 'enable' property.
     *
     * @return true is enable, otherwise false.
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Sets the value of 'enable' property.
     *
     * @param enable to be set.
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Returns the value of 'description' property.
     *
     * @return the clazz description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of 'description' property.
     *
     * @param description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the value of 'totalAssigned' property.
     *
     * @return the total number of assigned students.
     */
    public int getTotalAssigned() {
        return totalAssigned;
    }

    /**
     * Sets the value of 'totalAssigned' property.
     *
     * @param totalAssigned to be set.
     */
    public void setTotalAssigned(int totalAssigned) {
        this.totalAssigned = totalAssigned;
    }

    /**
     * Returns the value of 'teacherId' property.
     *
     * @return the id of the assigned teacher.
     */
    public UUID getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the value of 'teacherId' property.
     *
     * @param teacherId to be set.
     */
    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
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
     * Returns the value of 'studentList' property.
     *
     * @return the list of students.
     */
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     * Sets the value of 'studentList' property.
     *
     * @param studentList to be set.
     */
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
