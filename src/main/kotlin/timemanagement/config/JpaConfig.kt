package timemanagement.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableJpaRepositories(basePackages = ["timemanagement.repository"])
@EntityScan(basePackages = ["timemanagement.model"])
@EnableTransactionManagement
class JpaConfig {
}