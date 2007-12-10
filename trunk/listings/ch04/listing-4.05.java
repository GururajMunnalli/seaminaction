package org.open18.test;

import org.open18.action.RegisterAction;
import org.testng.annotations.Test;

public class RegisterActionUnitTest {

    @Test
    public void test() throws Exception {
        RegisterAction registerAction = new RegisterAction();
        registerAction.register();
    }
}
