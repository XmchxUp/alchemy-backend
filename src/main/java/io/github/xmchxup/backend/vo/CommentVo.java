package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.Comment;
import lombok.Data;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Data
public class CommentVo {
    private UserSummaryVo postedBy;
    private String comment;

    public CommentVo(Comment comment) {
        this.postedBy = new UserSummaryVo(comment.getUser());
        this.comment = comment.getComment();
    }
}
