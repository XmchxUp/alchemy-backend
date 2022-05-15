package io.github.xmchxup.backend.model;

import io.github.xmchxup.backend.enumeration.RoleType;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "roles")
@Where(clause = "delete_time is null")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleType name;
}
