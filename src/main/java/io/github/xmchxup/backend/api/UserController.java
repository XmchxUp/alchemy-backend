package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.UserRepository;
import io.github.xmchxup.backend.vo.UserSummaryVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummaryVo getCurrentUser() {
        User currentUser = currentUserUtils.getCurrentUser().orElseThrow(() -> new ParameterException(20007));
        return new UserSummaryVo(currentUser);
    }
}
