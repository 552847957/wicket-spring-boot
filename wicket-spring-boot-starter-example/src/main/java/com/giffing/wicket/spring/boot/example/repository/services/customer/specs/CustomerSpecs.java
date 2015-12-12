package com.giffing.wicket.spring.boot.example.repository.services.customer.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.giffing.wicket.spring.boot.example.model.Customer;
import com.giffing.wicket.spring.boot.example.model.Customer_;

public class CustomerSpecs {

	public static Specification<Customer> hasId(final Long id) {
		 return new Specification<Customer>() {
		      public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
		            CriteriaBuilder builder) {
		         return builder.equal(root.get(Customer_.id), id);
		      }
		    };
	}
	
	public static Specification<Customer> hasUsername(final String username) {
	    return new Specification<Customer>() {
	      public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
	            CriteriaBuilder builder) {
	         return builder.like(builder.lower(root.get(Customer_.username)), username.toLowerCase());
	      }
	    };
	  }

	
	
}
