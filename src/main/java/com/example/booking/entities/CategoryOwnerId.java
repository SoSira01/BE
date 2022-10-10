package com.example.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CategoryOwnerId implements Serializable {
    private static final long serialVersionUID = -4404644637017984164L;

    @Column(name = "eventCategoryId")
    private Integer eventCategoryId;

    @Column(name = "userId")
    private Integer userId;

}
