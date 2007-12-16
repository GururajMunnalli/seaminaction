package org.open18.test;

import org.open18.action.RegisterAction;
import org.testng.annotations.Test;

public class RegisterActionUnitTest {

    @Test(groups = {"level.unit", "speed.fast"})
    public void registerValidGolfer() {
        RegisterAction registerAction = new RegisterAction();
        String outcome = registerAction.register();
        assert outcome != null && outcome.equals("/home.xhtml");
    }
    
}