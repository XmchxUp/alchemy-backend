package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.model.Comment;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.CommentRepository;
import io.github.xmchxup.backend.vo.ApiResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CurrentUserUtils currentUserUtils;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("发布评论")
    public ResponseEntity<?> save(@RequestParam String comment, @RequestParam Long pinId) {
        User user = currentUserUtils.getCurrentUser().orElseThrow();
        commentRepository.save(new Comment(null, pinId, comment, user));
        return ResponseEntity.ok(new ApiResponseVO(true, "评论成功！"));
    }

}
