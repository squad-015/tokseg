package com.squad15.armariointeligente;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
class ArmarioInteligenteApplicationTests {

	// Container compartilhado para todos os testes (static)
	@Container
	private static final PostgreSQLContainer<?> postgres =
			new PostgreSQLContainer<>("postgres:latest")
					.withDatabaseName("armariodb")
					.withUsername("postgres")
					.withPassword("postgres");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		// Configura as propriedades dinâmicas para o Spring
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);

		// Configuração adicional recomendada para testes
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
		registry.add("spring.jpa.show-sql", () -> "true");
	}

	@Test
	void contextLoads(ApplicationContext context) {
		assertNotNull(context, "O contexto do Spring não foi carregado corretamente!");
	}
}