package com.giffing.wicket.spring.boot.example.web.pages.customers.create;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.example.model.Customer;
import com.giffing.wicket.spring.boot.example.repository.services.customer.CustomerRepositoryService;
import com.giffing.wicket.spring.boot.example.web.html.border.LabledFormBroder;
import com.giffing.wicket.spring.boot.example.web.html.form.ValidationForm;
import com.giffing.wicket.spring.boot.example.web.pages.BasePage;
import com.giffing.wicket.spring.boot.example.web.pages.customers.CustomerListPage;
import com.giffing.wicket.spring.boot.example.web.pages.customers.model.UsernameTextField;

@MountPath("customers/create")
public class CustomerCreatePage extends BasePage{
	
	@SpringBean
	private CustomerRepositoryService service;
	
	CompoundPropertyModel<Customer> customerModel;
	
	public CustomerCreatePage(){
		customerModel = new CompoundPropertyModel<>(new Customer());
		queue(new ValidationForm<>("form", customerModel));
		queue(new LabledFormBroder<>(getString("username"), new UsernameTextField("username")));
		queue(new LabledFormBroder<>(getString("firstname"), new RequiredTextField<>("firstname")));
		queue(new LabledFormBroder<>(getString("lastname"), new RequiredTextField<>("lastname")));
		queue(new LabledFormBroder<>(getString("password"), new PasswordTextField("password")));
		queue(submitButton());
		queue(cancelButton());
	}

	private Component submitButton() {
		return new Button("submit"){
			@Override
			public void onSubmit() {
				service.save(customerModel.getObject());
				setResponsePage(CustomerListPage.class);
			}
		};
	}
	
	private Component cancelButton() {
		Button cancelButton = new Button("cancel"){

			@Override
			public void onSubmit() {
				setResponsePage(CustomerListPage.class);
			}
			
		};
		cancelButton.setDefaultFormProcessing(false);
		return cancelButton;
	}
	
}
