package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.Pin;
import lombok.Data;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Data
public class PinPureVo {
    private String image;
    private String destination;
    private Long id;
    private UserSummaryVo postedBy;


    public PinPureVo(Pin pin) {
        this.image = pin.getImage();
        this.destination = pin.getDestination();
        this.id = pin.getId();
        this.postedBy = new UserSummaryVo(pin.getOwner());
    }
}
