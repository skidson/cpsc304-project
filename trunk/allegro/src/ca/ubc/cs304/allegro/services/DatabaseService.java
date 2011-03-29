package ca.ubc.cs304.allegro.services;

import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import ca.ubc.cs304.allegro.model.Customer;

public class DatabaseService {
	
	private HibernateTemplate template;
	
	public Customer loadCustomer(final String id) {
		HibernateCallback<Customer> callback = new HibernateCallback<Customer>() {
			public Customer doInHibernate(Session session) throws SQLException {
				return (Customer)session.load(Customer.class, id);
			}
		};
		return (Customer)template.execute(callback);
	}
	
	public void saveCustomer(final Customer customer) {
		HibernateCallback<Customer> callback = new HibernateCallback<Customer>() {
			public Customer doInHibernate(Session session) throws SQLException {
				session.saveOrUpdate(customer);
				return null;
			}
		};
		template.execute(callback);
	}
}
