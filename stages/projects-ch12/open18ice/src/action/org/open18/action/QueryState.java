package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("queryState")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class QueryState {
    private Integer firstResult;
    
    private String order;

    public Integer getFirstResult() {
        return firstResult;
    }

    public String getOrder() {
        return order;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
