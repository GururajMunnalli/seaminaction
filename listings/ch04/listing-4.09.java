import javax.persistence.EntityManager;

import org.jboss.seam.*;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;

@Name("entityManagerValidator")
@Scope(ScopeType.APPLICATION)
@Startup
public class EntityManagerValidator {

    @Logger
    Log log;

    @Create
    public void onStartup(Component component) {
        try {
            EntityManager em = (EntityManager)
			    Component.getInstance("entityManager");
            log.info("Successfully loaded the entityManager component");
            component.getScope().remove(component.getName());
        } catch (Exception e) {
            throw new RuntimeException("The entityManager component"
                + " is not configured properly", e);
        }
    }
}
