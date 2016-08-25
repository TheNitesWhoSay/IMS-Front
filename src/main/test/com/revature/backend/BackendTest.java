package com.revature.backend;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.OrderLine;
import com.revature.ims_backend.entities.Product;
import com.revature.ims_backend.entities.ProductImage;
import com.revature.ims_backend.entities.PurchaseOrder;

public class BackendTest {
	private static SessionFactory sf;
	private Session session;
	
	@BeforeClass
	public static void setUp() throws Exception {
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	@Before
	public void setupSession() {
		session = sf.openSession();
	}
	
	@After
	public void cleanupSession() {
		if ( session != null )
			session.close();
	}
	
	@AfterClass
	public static void tearDown() {
		sf.close();
	}
	
	///////////// Test Content //////////////
	@Test
	public void testStuff() {
		Query query = session.createQuery("FROM Client WHERE id=1");
		Client client = (Client) query.uniqueResult();
		System.out.println(client.getEmail());
	}
}
