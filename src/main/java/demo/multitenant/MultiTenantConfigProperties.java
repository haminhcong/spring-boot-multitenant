package demo.multitenant;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("multi-tenant")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MultiTenantConfigProperties {
  private List<DataSourceConfig> dataSourceList;
  private String defaultTenantName;
}
