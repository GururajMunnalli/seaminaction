package org.open18.spring.adapter;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Scope(ScopeType.APPLICATION)
public class HttpInvokerProxyFactoryBeanAdapter extends HttpInvokerProxyFactoryBean {
    @Create
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Unwrap
    public Object getObject() {
        return super.getObject();
    }
}
