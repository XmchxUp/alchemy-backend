package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.JwtTokenUtils;
import io.github.xmchxup.backend.core.security.JwtUser;
import io.github.xmchxup.backend.dto.LoginRequest;
import io.github.xmchxup.backend.dto.SignUpRequest;
import io.github.xmchxup.backend.enumeration.RoleType;
import io.github.xmchxup.backend.exception.http.ForbiddenException;
import io.github.xmchxup.backend.exception.http.NotFoundException;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Role;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.RoleRepository;
import io.github.xmchxup.backend.repository.UserRepository;
import io.github.xmchxup.backend.vo.ApiResponseVO;
import io.github.xmchxup.backend.vo.JwtAuthenticationResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "认证")
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation("登陆")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ParameterException(20004));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ParameterException(20005);
        }

        JwtUser jwtUser = JwtUser.create(user);
        if (!jwtUser.isEnabled()) {
            throw new ForbiddenException(20006);
        }

        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUsername(),
                user.getId().toString(),
                authorities,
                loginRequest.getRememberMe());
        return ResponseEntity.ok(new JwtAuthenticationResponseVO(token));
    }


    @PostMapping("/logout")
    @ApiOperation("退出")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("用户注册")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ParameterException(20002);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ParameterException(20003);
        }

        User user = User.builder()
                .nickname(signUpRequest.getNickname())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(
                        signUpRequest.getPassword()
                ))
                .build();


        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new NotFoundException(3011));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseVO(true, "用户注册成功"));
    }
}
