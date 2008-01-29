package org.open18.test;

import org.jboss.seam.Seam;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.contexts.TestLifecycle;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.core.Init;
import org.jboss.seam.core.Interpolator;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;
import org.jboss.seam.mock.MockApplication;
import org.jboss.seam.mock.MockExternalContext;
import org.jboss.seam.mock.MockFacesContext;
import org.jboss.seam.mock.MockServletContext;
import org.jboss.seam.util.Reflections;
import org.open18.action.RegisterAction;
import org.open18.auth.PasswordBean;
import org.open18.validator.GolferValidator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Collections;

public class RegisterActionUnitTest {

    @BeforeTest
    protected void setup() {
        TestLifecycle.beginTest(new MockServletContext(), Collections.<String, Object>emptyMap());
        Contexts.getApplicationContext().set(Seam.getComponentName(Init.class), new Init());
        Contexts.getApplicationContext().set(Seam.getComponentName(Expressions.class), new Expressions());
        Contexts.getApplicationContext().set(Seam.getComponentName(Interpolator.class), new Interpolator());
    }

    @AfterTest
    protected void teardown() {
        TestLifecycle.endTest();
    }

    @Test(groups = {"level.unit", "speed.fast"})
    public void registerValidGolfer() {
        MockFacesContext facesContext = new MockFacesContext(new MockExternalContext(), new MockApplication());
        facesContext.setCurrent();
        RegisterAction registerAction = new RegisterAction();
        setField(registerAction, "log", Logging.getLog(registerAction.getClass()));
        FacesMessages facesMessages = new FacesMessages();
        setField(registerAction, "facesMessages", facesMessages);
        GolferValidator golferValidator = new GolferValidator();
        setField(registerAction, "golferValidator", golferValidator);
        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setPassword("ilovegolf");
        passwordBean.setConfirm("ihategolf");
        setField(registerAction, "passwordBean", passwordBean);
        String outcome = registerAction.register();
        assert outcome == null;
        assert facesMessages.getCurrentMessages().size() == 1;
        assert facesMessages.getCurrentMessagesForControl("confirm").size() == 1;
    }

    protected void setField(Object object, String fieldName, Object value)
    {
       Field field = Reflections.getField(object.getClass(), fieldName);
       if (!field.isAccessible()) field.setAccessible(true);
       Reflections.setAndWrap(field, object, value);
    }
}