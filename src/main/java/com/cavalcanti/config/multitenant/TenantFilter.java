package com.cavalcanti.config.multitenant;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

//        String tenant = AuthenticationService.getTenant((HttpServletRequest) request);
	HttpServletRequest req = (HttpServletRequest) request;
	String tenantName = req.getHeader("X-TenantID");
	String path = req.getRequestURI();
	System.out.println("current path: "+ path);
	System.out.println("tenantName: "+ tenantName);
        TenantContext.setCurrentTenant(tenantName);
//        TenantContext.setCurrentTenant(tenant);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }

    }
}