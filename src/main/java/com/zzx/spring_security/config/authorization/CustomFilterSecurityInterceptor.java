package com.zzx.spring_security.config.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;


@Component
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final Logger log = LoggerFactory.getLogger(CustomFilterSecurityInterceptor.class);
    private static final String FILTER_APPLIED = "__spring_security_CustomFilterSecurityInterceptor_filterApplied";
    private final CustomInvocationSecurityMetadataSourceService customInvocationSecurityMetadataSourceService;
    private final CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired
    public CustomFilterSecurityInterceptor(CustomInvocationSecurityMetadataSourceService customInvocationSecurityMetadataSourceService, CustomAccessDecisionManager customAccessDecisionManager) {
        this.customInvocationSecurityMetadataSourceService = customInvocationSecurityMetadataSourceService;
        this.customAccessDecisionManager = customAccessDecisionManager;
    }


    /**
     *  初始化时将定义的DecisionManager,注入到父类AbstractSecurityInterceptor中
     */
    @PostConstruct
    public void init(){
        log.info("设置==========================================鉴权决策管理器");
        super.setAccessDecisionManager(customAccessDecisionManager);
    }


    /**
     * 向父类提供要处理的安全对象类型，因为父亲被调用的方法参数类型大多是Object，框架需要保证传递进去的安全对象类型相同
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }


    /**
     * 获取到自定义MetadataSource的方法
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.customInvocationSecurityMetadataSourceService;
    }



    /**
     *
     * 由Web容器调用，以向filter指示正在将其放入服务中。
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filer==========================================init");
    }

    /**
     * 每当request/response对由于客户端请求链末端的资源而通过链时，容器调用过滤器的doFilter方法。
     * 传入此方法的filter chain 允许Filter传递请求并响应链中的下一个实体。
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[自定义过滤器]：{}","CustomFilterSecurityInterceptor.doFilter()");
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        invoke(filterInvocation);
    }


    /**
     * 由Web容器调用，以向filter指示它正在退出服务。
     * 只有当filter的doFilter方法中的所有线程都退出或超出时间间后，才调用此方法。
     */
    @Override
    public void destroy() {
        log.info("filer==========================================destroy");
    }


    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        else {
            if (fi.getRequest() != null ) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }
            /* 调用父类的beforeInvocation ==> accessDecisionManager.decide(..) */
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }
            finally {
                super.finallyInvocation(token);
            }
            super.afterInvocation(token, null);
        }
    }
}
