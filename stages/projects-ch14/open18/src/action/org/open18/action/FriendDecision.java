package org.open18.action;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;
import org.open18.model.Golfer;
import org.open18.notification.Message;

@Name("friendDecision")
@Scope(ScopeType.CONVERSATION)
public class FriendDecision implements Serializable {

    @In protected FacesMessages facesMessages;

    @In protected EntityManager entityManager;

    @In protected Identity identity;

    @In(scope = ScopeType.BUSINESS_PROCESS)
    protected String initiator;

    @Out(value = "message", required = false)
    protected Message verdict;

    @BeginTask
    public String review() {
        return "/reviewFriendRequest.xhtml";
    }

    @EndTask(transition = "approve")
    @Transactional
    public void approve() {
        Golfer golfer1 = (Golfer) entityManager.createQuery(
            "select g from Golfer g " +
            "where username = #{initiator}")
            .getSingleResult();
        Golfer golfer2 = (Golfer) entityManager.createQuery(
            "select g from Golfer g " +
            "where username = #{identity.username}")
            .getSingleResult();

        golfer1.addFriend(golfer2);
        golfer2.addFriend(golfer1);
        prepareMessages(true);
    }

    @EndTask(transition = "reject")
    public void reject() {
        prepareMessages(false);
    }

    private void prepareMessages(boolean approved) {
        verdict = new Message();
        verdict.setRecipient(initiator);
        String resultStr = approved ? "accepted" : "turned down";
        verdict.setContent(identity.getUsername() + 
            " has " + resultStr + " your friend request.");
        if (approved) {
            facesMessages.add(
				"You're now friends with {0}.", initiator);
        }
        else {
            facesMessages.add(
				"You have turned down the friend request made by {0}.", initiator);
        }
    }
}
