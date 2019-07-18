package com.zzx.spring_security.config.authorization;

import com.zzx.spring_security.bo.Permission;
import com.zzx.spring_security.dao.SecurityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;



@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private static final Logger log = LoggerFactory.getLogger(CustomInvocationSecurityMetadataSourceService.class);
    private final SecurityDao dao;

    /* key 是url+method ,value 是对应url资源的角色列表 */
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();

    @Autowired
    public CustomInvocationSecurityMetadataSourceService(SecurityDao dao) {
        this.dao = dao;
    }

    /**
     * @PostConstruct 用于在依赖关系注入完成之后需要执行的方法，以执行任何初始化。
     * 此方法必须在将类放入服务之前调用，且只执行一次。
     */
    @PostConstruct
    public void init() {
        log.info("[自定义权限资源数据源]：{}", "初始化权限资源");
        List<Permission> permissions = dao.allPermission();
        permissions.forEach(item -> {
            List<String> roles=dao.allrole(item.getUrl());
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            for (String role : roles) {
                configAttributes.add(new SecurityConfig(role));
            }
            requestMap.put(new AntPathRequestMatcher(item.getUrl()), configAttributes);
        });
        System.out.println(requestMap.toString());
    }


    /**
     * getAttributes方法返回本次访问需要的权限，可以有多个权限。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("[自定义权限资源数据源]：{}", "获取本次访问需要的权限");
        if (requestMap.isEmpty()) {
            init();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                log.info("[自定义权限资源数据源]：当前路径[{}]需要的资源权限:[{}] ==> 触发鉴权决策管理器", entry.getKey(), entry.getValue().toString());
                return entry.getValue();
            }
        }
        log.info("[自定义权限资源数据源]：{}==> {}", "白名单路径", request.getRequestURI());
        return null;
    }


    /**
     * getAllConfigAttributes方法如果返回了所有定义的权限资源，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null。
     * 如果可用，则返回由实现类定义的所有ConfigAttribute。
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        log.info("[自定义权限资源数据源]：获取所有的角色==> {}", allAttributes.toString());
        return allAttributes;
    }


    /**
     * AbstractSecurityInterceptor 调用
     * supports方法返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
