package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.dto.CreatePinDTO;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Category;
import io.github.xmchxup.backend.model.Pin;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.CategoryRepository;
import io.github.xmchxup.backend.repository.PinRepository;
import io.github.xmchxup.backend.vo.ApiResponseVO;
import io.github.xmchxup.backend.vo.PinDetailVo;
import io.github.xmchxup.backend.vo.PinPureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CurrentUserUtils currentUserUtils;

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

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("创建Pin")
    public ResponseEntity<?> save(@Valid @RequestBody CreatePinDTO pinDTO) {
        Category category = categoryRepository.findById(pinDTO.getCategoryId())
                .orElseThrow(() -> new ParameterException(40001));

        User user = currentUserUtils.getCurrentUser().orElseThrow(() -> new ParameterException(20004));
        if (!user.getId().equals(pinDTO.getUserId())) {
            throw new ParameterException(40002);
        }

        pinRepository.save(Pin.builder()
                .title(pinDTO.getTitle())
                .about(pinDTO.getAbout())
                .destination(pinDTO.getDestination())
                .image(pinDTO.getImage())
                .owner(user)
                .category(category)
                .build());
        return ResponseEntity.ok(new ApiResponseVO(true, "发布成功"));
    }

    @DeleteMapping("/{pid}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("删除Pin")
    public ResponseEntity<?> delete(@PathVariable Long pid) {
        Pin pin = pinRepository.findById(pid).orElseThrow(() -> new ParameterException(40000));

        User user = currentUserUtils.getCurrentUser().orElseThrow(() -> new ParameterException(20004));
        if (!user.getId().equals(pin.getOwner().getId())) {
            throw new ParameterException(40002);
        }
        pinRepository.delete(pin);
        return ResponseEntity.ok(new ApiResponseVO(true, "删除成功"));
    }
}
