package io.github.xmchxup.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Table(name = "pins_saved")
@AllArgsConstructor
@NoArgsConstructor
public class PinSaved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pid;
    private Long uid;
}
