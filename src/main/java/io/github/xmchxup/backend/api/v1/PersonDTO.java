package io.github.xmchxup.backend.api.v1;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class PersonDTO {
    @DecimalMin(value = "0.00", message = "不再合法范围内")
    @DecimalMax(value = "11.99", message = "不再合法范围内")
    private BigDecimal totalPrice;
}
