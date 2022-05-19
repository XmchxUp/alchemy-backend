package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.dto.CreatePinDTO;
import io.github.xmchxup.backend.service.PinService;
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

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "Pin内容模块")
@RestController
@RequestMapping("/api/pin")
@Validated
public class PinController {
    @Autowired
    private PinService pinService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("获取所有Pin")
    public List<PinPureVo> getAllPins() {
        return pinService.getAllPins();
    }

    @GetMapping("/{pinId}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("通过Id获取Pin的详细信息")
    public PinDetailVo getPinDetail(@PathVariable Long pinId) {
        return new PinDetailVo(pinService.getPinById(pinId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("创建Pin")
    public ResponseEntity<?> save(@Valid @RequestBody CreatePinDTO pinDTO) {
        pinService.createPin(pinDTO);
        return ResponseEntity.ok(new ApiResponseVO(true, "发布成功"));
    }

    @DeleteMapping("/{pid}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("删除Pin")
    public ResponseEntity<?> delete(@PathVariable Long pid) {
        pinService.deletePin(pid);
        return ResponseEntity.ok(new ApiResponseVO(true, "删除成功"));
    }
}
