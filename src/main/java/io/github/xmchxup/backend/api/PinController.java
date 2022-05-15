package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Pin;
import io.github.xmchxup.backend.repository.PinRepository;
import io.github.xmchxup.backend.vo.PinDetailVo;
import io.github.xmchxup.backend.vo.PinPureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "Pin内容模块")
@RestController
@RequestMapping("/api/pin")
@Validated
public class PinController {
    @Autowired
    private PinRepository pinRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("获取所有Pin")
    public List<PinPureVo> getAllPins() {
        return pinRepository.findAll()
                .stream()
                .map(PinPureVo::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{pinId}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("通过Id获取Pin的详细信息")
    public PinDetailVo getPinDetail(@PathVariable Long pinId) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new ParameterException(40000));
        return new PinDetailVo(pin);
    }
}
