package io.github.xmchxup.backend.dto;

import lombok.Data;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Data
public class GithubUserInfo {
    private String login;
    private String name;
    private String avatarUrl;
    private String bio;
    private String email;
}
