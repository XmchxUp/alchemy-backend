package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.Pin;
import io.github.xmchxup.backend.model.PinSaved;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Data
public class PinPureVo {
    private String image;
    private String destination;
    private Long id;
    private UserSummaryVo postedBy;
    private List<PinSaved> save;


    public PinPureVo(Pin pin) {
        this.image = pin.getImage();
        this.destination = pin.getDestination();
        this.id = pin.getId();
        this.postedBy = new UserSummaryVo(pin.getOwner());
        this.save = new ArrayList<>(pin.getSaves());
    }
}
