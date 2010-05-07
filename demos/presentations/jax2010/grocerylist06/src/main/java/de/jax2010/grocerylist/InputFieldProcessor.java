package de.jax2010.grocerylist;

import javax.enterprise.inject.Model;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIMessage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@Model
public class InputFieldProcessor {
   public void preRender(ComponentSystemEvent e) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      UIComponent cc = e.getComponent().getParent();

      HtmlOutputLabel label = null;
      UIInput firstInput = null;
      UIMessage message = null;

      for (UIComponent child : e.getComponent().getChildren()) {
         if (child instanceof HtmlOutputLabel) {
            label = (HtmlOutputLabel) child;
         }
         else if (child instanceof EditableValueHolder) {
            if (firstInput == null) {
               firstInput = (UIInput) child;
            }
         }
         else if (child instanceof UIMessage) {
            message = (UIMessage) child;
         }

         if (label != null && firstInput != null & message != null) {
            break;
         }
      }

      if (firstInput != null && label != null) {
         label.setFor(firstInput.getId());
      }
      
      if (firstInput != null && message != null) {
         message.setFor(firstInput.getId());
      }

      // <c:set var="invalid" value="#{not empty facesContext.getMessageList(cc.clientId.concat(':input'))}"/>
      if (facesContext.isValidationFailed() && facesContext.getMessages(firstInput.getClientId()).hasNext()) {
         System.out.println(cc.getClientId() + " has a problem");
         cc.getAttributes().put("invalid", true);
      }

      // <c:set var="label" value="#{empty cc.attrs.label ? cc.id.substring(0, 1).toUpperCase().concat(cc.id.substring(1)) : cc.attrs.label}"/>
      if (!cc.getAttributes().containsKey("label")) {
         cc.getAttributes().put("label", cc.getId().substring(0, 1).toUpperCase() + cc.getId().substring(1));
      }

   }
}
