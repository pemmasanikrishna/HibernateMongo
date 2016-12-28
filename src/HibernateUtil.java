import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;

import com.hibermongo.entities.User;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory createSessionFactory() {
		try {

			Configuration configuration = new OgmConfiguration();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			configuration.addAnnotatedClass(User.class);

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			sessionFactory.getStatistics().setStatisticsEnabled(true);
			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception Occured in Hibernate");
		}
	}

}
