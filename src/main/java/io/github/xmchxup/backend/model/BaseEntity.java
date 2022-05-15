package io.github.xmchxup.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@MappedSuperclass // 不要生成一个实体
public abstract class BaseEntity {
    @JsonIgnore
    @Column(insertable = false, updatable = false) // 创建/更新相关实体的责任不在当前类中
    private Timestamp createTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Timestamp updateTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Timestamp deleteTime;
}
