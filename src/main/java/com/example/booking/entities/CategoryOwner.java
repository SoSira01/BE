package com.example.booking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "eventcategoryowner")
public class CategoryOwner {
    @EmbeddedId
    private CategoryOwnerId id;

//    @Id
    @MapsId("eventCategoryId")
    @ManyToOne
    @JoinColumn(name = "eventCategoryId")
    private Category eventCategory;

//    @Id
    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public CategoryOwner(User user,Category category) {
        this.id = new CategoryOwnerId(category.getId(),user.getId());
        this.eventCategory = category;
        this.user = user;
    }
}
