package io.github.xmchxup.backend.core.hack;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 处理所有@Controller的前缀
 *
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {
    @Value("${sharing.api-package}")
    private String apiPackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
//		原有的url
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (null != mappingInfo) {
            String prefix = this.getPrefix(handlerType);
//			加上前缀的url
            return RequestMappingInfo.paths(prefix)
                    .build().combine(mappingInfo);
        }
        return null;
    }

    private String getPrefix(Class<?> handlerType) {
        String packageName = handlerType.getPackage().getName();
        return packageName
                .replaceAll(this.apiPackagePath, "")
                .replace(".", "/");
    }
}