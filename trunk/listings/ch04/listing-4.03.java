package org.open18.action;

import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;

@Name("registerAction")
public class RegisterAction {
    
    @Logger
    private Log log;
    
    @In
    FacesMessages facesMessages;
    
    public void register()
    {
        log.info("registerAction.register() action called");
        facesMessages.add("register");
    }
}
