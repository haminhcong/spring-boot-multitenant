package demo.multitenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceConfig {

  private String tenantName;
  private String url;
  private String username;
  private String password;
  private String driverClassName;
}