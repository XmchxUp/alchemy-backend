package io.github.xmchxup.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 255)
    private String title;
    @NotBlank
    @Length(max = 255)
    private String about;
    @NotBlank
    @Length(max = 255)
    private String destination;
    @NotBlank
    @Length(max = 255)
    private String image;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long userId;
}
