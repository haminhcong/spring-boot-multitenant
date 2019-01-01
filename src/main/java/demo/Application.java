package demo;

import demo.multitenant.DataSourceBasedMultiTenantConnectionProviderImpl;
import demo.multitenant.MultiTenantFilter;
import demo.multitenant.TenantIdentifierResolver;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import org.hibernate.MultiTenancyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication
public class Application {

  @Autowired
  DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;

  @Autowired
  TenantIdentifierResolver tenantResolver;

  @Autowired
  AutowireCapableBeanFactory beanFactory;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @PersistenceContext
  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.multiTenancy", MultiTenancyStrategy.DATABASE.name());
    props.put("hibernate.multi_tenant_connection_provider", dsProvider);
    props.put("hibernate.tenant_identifier_resolver", tenantResolver);
    result.setPackagesToScan("demo");
    result.setJpaVendorAdapter(jpaVendorAdapter());
    result.setJpaPropertyMap(props);
    return result;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(true);
    // Generate DDL is not supported in Hibernate to multi-tenancy features
    // https://hibernate.atlassian.net/browse/HHH-7395
    hibernateJpaVendorAdapter.setGenerateDdl(false);
    return hibernateJpaVendorAdapter;
  }

  @Bean
  public FilterRegistrationBean myFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    Filter tenantFilter = new MultiTenantFilter();
    beanFactory.autowireBean(tenantFilter);
    registration.setFilter(tenantFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

}