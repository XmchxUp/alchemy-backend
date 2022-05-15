package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.DBFileRepository;
import io.github.xmchxup.backend.repository.UserRepository;
import io.github.xmchxup.backend.vo.FileInfoVo;
import io.github.xmchxup.backend.vo.UserIdentityAvailability;
import io.github.xmchxup.backend.vo.UserSummaryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "用户API")
@Validated
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserUtils currentUserUtils;

    @Autowired
    private DBFileRepository fileRepository;

    @ApiOperation("当前用户")
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummaryVo getCurrentUser() {
        User currentUser = currentUserUtils.getCurrentUser().orElseThrow(() -> new ParameterException(20007));
        return new UserSummaryVo(currentUser);
    }

    @ApiOperation("根据用户Id获取所有文件信息")
    @GetMapping("/user/{userId}/files")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFiles(@PathVariable Long userId) {
        List<FileInfoVo> data = fileRepository.findDBFilesByOwnerId(userId)
                .stream().map(FileInfoVo::new).collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(data);
    }

    @ApiOperation("用户名是否可用")
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @ApiOperation("邮箱是否可用")
    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}
