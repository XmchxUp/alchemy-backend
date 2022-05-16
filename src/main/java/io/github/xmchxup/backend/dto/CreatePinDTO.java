package io.github.xmchxup.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@ToString
public class CreatePinDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String about;
    @NotBlank
    private String destination;
    @NotBlank
    private String image;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long userId;
}
