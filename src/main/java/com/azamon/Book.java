package com.azamon;

import javax.persistence.*;

@Entity(name = "et_books")
@Table(name = "et_books")
public class Book {

    @Id
    /* IDENTITY is used because I used SERIAL in postgreSQL database. */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    public Book() {
        super();
    }

    /* Constructor*/
    public Book(String title, String description, String author, String genre){
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

    /* Getters and setters*/

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
