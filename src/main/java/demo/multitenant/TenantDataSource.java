package demo.multitenant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class TenantDataSource implements Serializable {

  private HashMap<String, DataSource> dataSources = new HashMap<>();

  @Autowired
  private MultiTenantConfigProperties multiTenantDataSourcesConfigProperties;


  private DataSource getDataSource(DataSourceConfig config) {
    String tenantName = config.getTenantName();
    if (dataSources.get(tenantName) != null) {
      return dataSources.get(tenantName);
    }
    DataSource dataSource = createDataSource(config);
    if (dataSource != null) {
      dataSources.put(tenantName, dataSource);
    }
    return dataSource;
  }

  Map<String, DataSource> getAll() {
    List<DataSourceConfig> configList =
        multiTenantDataSourcesConfigProperties.getDataSourceList();
    Map<String, DataSource> result = new HashMap<>();
    for (DataSourceConfig config : configList) {
      DataSource dataSource = getDataSource(config);
      result.put(config.getTenantName(), dataSource);
    }
    return result;
  }

  private DataSource createDataSource(DataSourceConfig config) {
    if (config != null) {
      DataSourceBuilder factory = DataSourceBuilder
          .create().driverClassName(config.getDriverClassName())
          .username(config.getUsername())
          .password(config.getPassword())
          .url(config.getUrl());
      return factory.build();
    }
    return null;
  }

}
