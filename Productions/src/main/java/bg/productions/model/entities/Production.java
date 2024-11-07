package bg.productions.model.entities;

import bg.productions.model.enums.Genre;
import bg.productions.model.enums.ProductionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "productions")
public class Production{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Size(min = 2, max = 200)
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String imageUrl;

    @Column(nullable = false)
    @NotEmpty
    private String videoUrl;


    @Enumerated(EnumType.STRING)
    private Genre genre;


    @Enumerated(EnumType.STRING)
    private ProductionType productionType;

    @Column(nullable = false)
    @NotNull
    @Min(value = 1906)
    @Max(value = 2024)
    private Integer year;

    @Column(nullable = false)
    @NotNull
    private Integer length;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer rating;

    @Column
    @PositiveOrZero
    @NotNull
    private Integer rentPrice;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 50, max = 5000)
    private String description;

    public Production() {
    }

    public Long getId() {
        return id;
    }

    public Production setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Production setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Production setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Production setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public Production setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Production setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getRentPrice() {
        return rentPrice;
    }

    public Production setRentPrice(Integer rentPrice) {
        this.rentPrice = rentPrice;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Production setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public Production setProductionType(ProductionType productionType) {
        this.productionType = productionType;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Production setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Production setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Production{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", genre=" + genre +
                ", productionType=" + productionType +
                ", year=" + year +
                ", length=" + length +
                ", rating=" + rating +
                ", rentPrice=" + rentPrice +
                ", description='" + description + '\'' +
                '}';
    }
}