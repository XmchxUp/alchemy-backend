package io.github.xmchxup.backend.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.xmchxup.backend.core.security.JwtTokenUtils;
import io.github.xmchxup.backend.core.security.JwtUser;
import io.github.xmchxup.backend.dto.GithubUserInfo;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Slf4j
@Api(tags = "权限模块")
@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Value("${sharing.clientId}")
    private String clientId;
    @Value("${sharing.clientSecret}")
    private String clientSecret;

    @ApiOperation("Github登陆")
    @PostMapping("/github/signin")
    public ResponseEntity<?> githubLogin(@RequestParam String code) {
        log.info(code);

        String accessToken = getAccessToken(code);
        String userInfoJson = getUserInfo(accessToken);

        GithubUserInfo userInfo = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create().fromJson(userInfoJson, GithubUserInfo.class);

        log.info("userInfo: {}", userInfo);

        String username = userInfo.getLogin() + "#github";
        User user;

        if (userRepository.existsByUsername(username)) {
            user = userRepository.findByUsername(username).orElseThrow();
        } else {
            // create new user with username#github
            user = User.builder()
                    .nickname(userInfo.getName())
                    .username(username)
                    .email(userInfo.getEmail())
                    .password(passwordEncoder.encode(
                            "123456"
                    ))
                    .avatar(userInfo.getAvatarUrl())
                    .motto(userInfo.getBio())
                    .build();

            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException(3011));

            user.setRoles(Collections.singleton(userRole));

            User result = userRepository.save(user);
            user.setId(result.getId());
        }

        JwtUser jwtUser = JwtUser.create(user);
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is forbidden to login");
        }

        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = JwtTokenUtils.createToken(user.getUsername(),
                user.getId().toString(),
                authorities,
                true);

        return ResponseEntity.ok(new JwtAuthenticationResponseVO(token));
    }

    private String getAccessToken(String code) {
        String url = "https://github.com/login/oauth/access_token?" +
                "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code;
        // 构建请求头
        log.info("url: {}", url);
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // post 请求方式
        ResponseEntity<String> response = restTemplate.postForEntity(url,
                requestEntity, String.class);
        String responseStr = response.getBody();
        log.info(responseStr);
        // 解析响应json字符串
        JsonObject jobj = new Gson().fromJson(responseStr, JsonObject.class);
        String accessToken = jobj.get("access_token").getAsString();
        log.info(accessToken);
        return accessToken;
    }

    private String getUserInfo(String accessToken) {
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // AccessToken放在请求头中
        requestHeaders.add("Authorization", "token " + accessToken);
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // get请求方式
        ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/user",
                HttpMethod.GET, requestEntity, String.class);
        String userInfo = response.getBody();
        log.info(userInfo);
        return userInfo;
    }

    @ApiOperation("登陆")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser user = (JwtUser) authentication.getPrincipal();
        if (!user.isEnabled()) {
            throw new ForbiddenException(20006);
        }

        List<String> authorities = user.getAuthorities()
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
    @ApiOperation("登出")
    public ResponseEntity<Void> logout() {
        // TODO:
        // token一但发布 就不能回收，直到过期
        // 上面rememberMe就是用来延长过期时间的
        // 解决方案可以使用Redis来保存user:token信息，每次判断token是否于redis中的是否相等
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
                .avatar("https://cdn.jsdelivr.net/gh/XmchxUp/cloudimg@master/partywizard.4ar7hnvogr00.gif")
                .motto("You can learn anything. (By Tesla)")
                .build();


        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new NotFoundException(3011));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponseVO(true, "用户注册成功"));
    }
}
