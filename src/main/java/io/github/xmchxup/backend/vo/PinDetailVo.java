package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.Pin;
import lombok.Data;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Data
public class PinDetailVo {
    private Long id;
    private String title;
    private String image;
    private String about;
    private String destination;
    private UserSummaryVo postedBy;
    //    TODO comments


    public PinDetailVo(Pin pin) {
        this.image = pin.getImage();
        this.destination = pin.getDestination();
        this.id = pin.getId();
        this.about = pin.getAbout();
        this.title = pin.getTitle();
        this.postedBy = new UserSummaryVo(pin.getOwner());
    }
}
