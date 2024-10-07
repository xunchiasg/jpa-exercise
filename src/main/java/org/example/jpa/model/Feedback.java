package org.example.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="feedback", uniqueConstraints = {@UniqueConstraint(name ="email", columnNames = "email")}) // double check columns on MYSQL
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "ID must be a positive number.") // import
    private Long id;

    @Column (nullable = false)
    @NotBlank(message = "Description cannot be blank.")
    @Size(min = 3, message = "Description must be at least 3 characters.")
    @Size(max = 1028, message = "Description must not be more than 1028 characters.") //Modified parameters

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;

    public Feedback() {                                           // create an empty instance of Feedback
    }

    public Feedback(Customer customer, String description) {     // create an instance of Feedback taking in a 'description' parameter
        this.customer = customer;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
