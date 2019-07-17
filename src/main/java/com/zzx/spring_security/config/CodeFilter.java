package com.zzx.spring_security.config;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zzx.spring_security.util.RandomCodeUtil.RANDOMCODEKEY;
@Component
public class CodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getRequestURI().equals("/login")&&httpServletRequest.getMethod().equalsIgnoreCase("post")){
            String oldcode= String.valueOf(httpServletRequest.getSession().getAttribute(RANDOMCODEKEY));
            String newcode= ServletRequestUtils.getStringParameter(httpServletRequest, "enter");
            if(oldcode.equals(newcode)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }else {
                httpServletResponse.sendRedirect("/login");
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
