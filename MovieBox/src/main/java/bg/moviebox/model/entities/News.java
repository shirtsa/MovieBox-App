package bg.moviebox.model.entities;

import bg.moviebox.model.enums.NewsType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "news")
public class News extends BaseEntity {

    @NotBlank
    @Size(min = 5, max = 100)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column
    private Instant created = Instant.now();

    @NotBlank
    @Column(nullable = false)
    private String firstImageUrl;

    @NotBlank
    @Column(nullable = false)
    private String secondImageUrl;

    @NotBlank
    @Column(nullable = false)
    private String trailerUrl;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 50, max = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsType newsType;

    public String getName() {
        return name;
    }

    public News setName(String name) {
        this.name = name;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public News setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public News setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
        return this;
    }

    public String getSecondImageUrl() {
        return secondImageUrl;
    }

    public News setSecondImageUrl(String secondImageUrl) {
        this.secondImageUrl = secondImageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public News setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public News setDescription(String description) {
        this.description = description;
        return this;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public News setNewsType(NewsType newsType) {
        this.newsType = newsType;
        return this;
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", firstImageUrl='" + firstImageUrl + '\'' +
                ", secondImageUrl='" + secondImageUrl + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", description='" + description + '\'' +
                ", newsType=" + newsType +
                '}';
    }
}
