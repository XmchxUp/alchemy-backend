package io.github.xmchxup.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Table(name = "pins")
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
