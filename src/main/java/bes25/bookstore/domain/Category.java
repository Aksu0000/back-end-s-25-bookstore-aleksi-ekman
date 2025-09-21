package bes25.bookstore.domain;

import java.util.List;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//import jakarta.validation.constraints.NotNull;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Book> books;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
