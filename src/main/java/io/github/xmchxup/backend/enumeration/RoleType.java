package io.github.xmchxup.backend.enumeration;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public enum RoleType {
    ROLE_USER("用户"),
    ROLE_TEMP_USER("临时用户"),
    ROLE_MANAGER("管理者"),
    ROLE_ADMIN("Admin");

    RoleType(java.lang.String description) {
    }
}