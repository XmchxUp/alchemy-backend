package io.github.xmchxup.backend.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Builder
public class PageCounter {
    private Integer page;
    private Integer count;
}
