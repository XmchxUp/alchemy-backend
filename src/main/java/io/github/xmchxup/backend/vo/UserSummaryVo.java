package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.User;
import lombok.Getter;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
public class UserSummaryVo {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;

    public UserSummaryVo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
    }
}
