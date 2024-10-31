package bg.moviebox.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "celebrities")
public class Celebrity extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String imageUrl;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 20, max = 5000)
    private String biography;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "celebrities_movie_productions",
//            joinColumns = @JoinColumn(name = "celebrity_id"),
//            inverseJoinColumns = @JoinColumn(name = "movie_id"))
//    private Set<Production> knownForMovies;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "celebrities_tv_productions",
//            joinColumns = @JoinColumn(name = "celebrity_id"),
//            inverseJoinColumns = @JoinColumn(name = "tv_id"))
//    private Set<Production> knownForTvShows;

    public Celebrity() {
//        this.knownForMovies = new HashSet<>();
//        this.knownForTvShows = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Celebrity setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Celebrity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public Celebrity setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    @Override
    public String toString() {
        return "Celebrity{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}
