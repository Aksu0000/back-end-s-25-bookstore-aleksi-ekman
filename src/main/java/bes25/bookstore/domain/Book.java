package bes25.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Insert book title")
    @Size(min = 1, max = 250)
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty(message = "Insert book author")
    @Size(min = 1, max = 250)
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull(message = "Insert publication year")
    @Min(value = 0, message = "Book publication year cannot be negative")
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @NotEmpty(message = "Insert ISBN")
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull(message = "Choose a category")
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Book() {}

    public Book(String title, String author, Integer publicationYear, String isbn, Double price, Category category) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

     public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title + " (" + author + "), " + publicationYear + " - " +
           (category != null ? category.getName() : "No category");
    }


}
