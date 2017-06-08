package com.sdi.presentation.validators;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;


@FacesValidator("RepeatedLogin")
public class RepeatedLoginValidator implements Validator {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ResourceBundle bundle = facesContext.getApplication().getResourceBundle(
			facesContext, "msgs");

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		UserDao dao = Factories.persistence.newUserDao();
		if (dao.findByLogin(String.valueOf(arg2)) != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
							bundle.getString("loginRepetido")));
		}

	}

}
