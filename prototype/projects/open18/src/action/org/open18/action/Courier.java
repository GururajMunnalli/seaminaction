package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Identity;

@Name("courier")
public class Courier {
	
	/**
	 * One approach: Use {@literal@In} and {@literal@Out} on the memo field so
	 * that memo is exposed as a context variable after the call to setMemo().
	 * If it is not exposed after the call to the setter, then the private
	 * variable will be overwritten when send() is called.
	 * 
	 * Another approach: Use getter/setter method to access memo. Then just
	 * expose dispatcherMemo when needed, such as after send() method. Can
	 * make all other methods use {@literal@BypassInterceptors}
	 */
	@In(required = false)
	@Out(required = false)
	protected Message message;

	@Out(required = false)
	protected Message dispatchedMessage;
	
	@Out(scope = ScopeType.BUSINESS_PROCESS, required = false)
	protected String sender;
	
	@In
	protected Identity identity;

	@Observer(value = "courier.messageWaiting", create = true)
	@CreateProcess(definition = "Courier")
	public void send() {
		sender = identity.getUsername();
		dispatchedMessage = message;
		resetMessage();
	}

	@CreateProcess(definition = "Broadcast")
	public void broadcast() {
		sender = identity.getUsername();
		dispatchedMessage = message;
		resetMessage();
	}
	
	@StartTask @EndTask(transition = "acknowledge")
	public void acknowledge() {
	}
	
	@BypassInterceptors
	public Message getMessage() {
		return this.message;
	}
	
	/**
	 * BypassInterceptors is required if we are using injection for
	 * memo because it will be reinjected before call to send().
	 * If getter/setter used to access memo only, then can disable
	 * interceptors safely.
	 */
	//@BypassInterceptors
	public void setMessage(Message message) {
		this.message = message;
	}
	
	@Create
	@BypassInterceptors
	public void resetMessage() {
		this.message = new Message();
	}
}
