package com.giffing.wicket.spring.boot.example.repository.services.customer;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.giffing.wicket.spring.boot.example.model.Customer;
import com.giffing.wicket.spring.boot.example.model.Customer_;
import com.giffing.wicket.spring.boot.example.repository.services.DefaultRepositoryService;
import com.giffing.wicket.spring.boot.example.repository.services.customer.filter.CustomerFilter;
import com.giffing.wicket.spring.boot.example.repository.services.customer.specs.CustomerSpecs;

@Component
@Transactional(readOnly=true)
public class CustomerRepositoryServiceImpl extends DefaultRepositoryService<Customer, CustomerFilter> implements CustomerRepositoryService {

	private CustomerRepository customerRepository;

	@Resource
	private EntityManager em;
	
	@Inject
	public CustomerRepositoryServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}
	
	@Override
	public List<Customer> findAll(Long page, Long count, CustomerFilter filter) {
		Pageable pageable = new PageRequest(page.intValue(), count.intValue(), getSort(filter));
		return customerRepository.findAll(filter(filter), pageable).getContent();
	}

	@Override
	public long count(CustomerFilter filter) {
		return customerRepository.count(filter(filter));
	}
	
	private Specification<Customer> filter(CustomerFilter filter) {
		List<Specification<Customer>> specs = new ArrayList<>();
		
		if(filter.getId() != null){
			specs.add(CustomerSpecs.hasId(filter.getId()));
		}
		
		if(isNotEmpty(filter.getUsername())){
			specs.add(CustomerSpecs.hasUsername(filter.getUsername()));
		}
		
		if(isNotEmpty(filter.getUsernameLike())){
			specs.add(CustomerSpecs.hasUsernameLike(filter.getUsernameLike()));
		}
		
		if(isNotEmpty(filter.getFirstnameLike())){
			specs.add(CustomerSpecs.hasFirstnameLike(filter.getFirstnameLike()));
		}
		
		if(isNotEmpty(filter.getLastnameLike())){
			specs.add(CustomerSpecs.hasLastnameLike(filter.getLastnameLike()));
		}
		
		if(filter.isActive()){
			specs.add(CustomerSpecs.hasActive(filter.isActive()));
		}
		
		Specifications<Customer> spec = null;
		for (Specification<Customer> specification : specs) {
			if(spec == null){
				spec = Specifications.where(specification);
			}else {
				spec = spec.and(specification);
			}
		}
		
		return spec;
	}
	
	boolean isNotEmpty(String toCheck){
		if(toCheck != null && toCheck.length() > 0){
			return true;
		}
		
		return false;
	}


	@Override
	public Customer findById(Long id) {
		return customerRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id) {
		customerRepository.delete(id);
	}

	@Override
	public List<String> findUsernames(int count, String usernamePart) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<Customer> customerRoot = query.from(Customer.class);
		
		query = query.select(customerRoot.get(Customer_.username));
		
		Predicate predicate = CustomerSpecs.hasUsernameLike(usernamePart).toPredicate(customerRoot, query, builder);
		query = query.where(predicate);
		
		TypedQuery<String> createQuery = em.createQuery(query);
		createQuery.setMaxResults(count);
		return createQuery.getResultList();
	}


	
}
