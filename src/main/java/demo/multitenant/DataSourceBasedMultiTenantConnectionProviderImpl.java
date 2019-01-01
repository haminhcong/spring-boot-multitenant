package demo.multitenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	@Autowired ApplicationContext context;

	@Autowired
	private MultiTenantConfigProperties multiTenantDataSourcesConfigProperties;

	static Map<String, DataSource> map = new HashMap<>();

	boolean init = false;

	@PostConstruct
	public void load() {
	}

	@Override
	protected DataSource selectAnyDataSource() {
		String defaultTenantName = multiTenantDataSourcesConfigProperties.getDefaultTenantName();
		if (!init) {
			initDataSources();
		}
		return map.get(defaultTenantName);
	}

	@Override
	protected DataSource selectDataSource(String tenantName) {
		if (!init) {
			initDataSources();
		}
		return map.get(tenantName);
	}

	private void initDataSources(){
		init = true;
		TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
		map.putAll(tenantDataSource.getAll());
	}
}