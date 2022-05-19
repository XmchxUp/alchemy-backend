package io.github.xmchxup.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Table(name = "pins")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "delete_time is null")
public class Pin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String about;
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "pins_category",
            joinColumns = @JoinColumn(name = "pin_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnore
    private Category category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pins_saved",
            joinColumns = @JoinColumn(name = "pid"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    @JsonIgnore
    private List<PinSaved> saves;

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", about='" + about + '\'' +
                ", destination='" + destination +
                '}';
    }
}
