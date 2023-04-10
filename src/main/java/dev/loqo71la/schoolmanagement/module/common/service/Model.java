package dev.loqo71la.schoolmanagement.module.common.service;

import java.util.Date;
import java.util.UUID;

/**
 * Defines a base model.
 */
public interface Model {

    /**
     * Returns the value of 'id' property.
     *
     * @return the id.
     */
    UUID getId();

    /**
     * Sets the value of 'id' property.
     *
     * @param id to be set.
     */
    void setId(UUID id);

    /**
     * Returns the value of 'createdBy' property.
     *
     * @return the username who created the model.
     */
    String getCreatedBy();

    /**
     * Sets the value of 'createdBy' property.
     *
     * @param createdBy to be set.
     */
    void setCreatedBy(String createdBy);

    /**
     * Returns the value of 'createdAt' property.
     *
     * @return the time it was created.
     */
    Date getCreatedAt();

    /**
     * Sets the value of 'createdAt' property.
     *
     * @param createdAt to be set.
     */
    void setCreatedAt(Date createdAt);

    /**
     * Returns the value of 'updatedBy' property.
     *
     * @return the username who updated the model.
     */
    String getUpdatedBy();

    /**
     * Sets the value of 'updatedBy' property.
     *
     * @param updatedBy to be set.
     */
    void setUpdatedBy(String updatedBy);

    /**
     * Returns the value of 'updatedAt' property.
     *
     * @return the time it was updated.
     */
    Date getUpdatedAt();

    /**
     * Sets the value of 'updatedAt' property.
     *
     * @param updatedAt to be set.
     */
    void setUpdatedAt(Date updatedAt);
}
