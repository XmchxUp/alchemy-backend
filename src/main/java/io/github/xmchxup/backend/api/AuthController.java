package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.JwtTokenProvider;
import io.github.xmchxup.backend.dto.LoginDTO;
import io.github.xmchxup.backend.dto.SignUpDTO;
import io.github.xmchxup.backend.enumeration.RoleName;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "权限管理")
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @ApiOperation("登陆")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponseVO(jwt));
        } catch (DisabledException e) {
            throw new ForbiddenException(20006);
        } catch (BadCredentialsException e) {
            throw new ForbiddenException(10004);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @ApiOperation("注册")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpRequest) {
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


        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
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
