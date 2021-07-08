# Multi data source in Spring Boot

This repo is an example of connecting to multiple data sources. As an example we will take a case of replica(master <- slave) for MySql

## Properties

First we need to configure our properties. Properties can have any structure as the beans will be configured programmatically. 

    spring:
      datasource-write:
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:4406/mydb
        username: 'root'
        password: '111'
        hikari:
          idle-timeout: 10000
          maximum-pool-size: 10
          minimum-idle: 5
          pool-name: WriteHikariPool
          
      datasource-read:
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:5506/mydb
        username: 'root'
        password: '111'
        hikari:
          idle-timeout: 10000
          maximum-pool-size: 10
          minimum-idle: 5
          pool-name: ReadHikariPool
          

## Datasource configurations

    @Configuration
    @ConfigurationProperties("spring.datasource-read")
    @EnableTransactionManagement
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryRead",
            transactionManagerRef = "transactionManagerRead",
            basePackages = {"com.phoosop.springbootmultidatasource.repository.read"}
    )
    public class DataSourceConfigRead extends HikariConfig {
    
        public final static String MODEL_PACKAGE = "com.phoosop.springbootmultidatasource.model.entity";
        public final static String PERSISTENCE_UNIT_NAME = "read";
        protected final Properties JPA_READ_PROPERTIES = new Properties() {{
            put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
            put("hibernate.show_sql", "true");
        }};
    
        public DataSourceConfigRead(HikariReadConfigurations hikariReadConfigurations) {
            this.setPoolName(hikariReadConfigurations.getPoolName());
            this.setMinimumIdle(hikariReadConfigurations.getMinimumIdle());
            this.setMaximumPoolSize(hikariReadConfigurations.getMaximumPoolSize());
            this.setIdleTimeout(hikariReadConfigurations.getIdleTimeout());
        }
    
        @Bean
        public HikariDataSource dataSourceRead() {
            return new HikariDataSource(this);
        }
    
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryRead(
                final HikariDataSource dataSourceRead) {
            return new LocalContainerEntityManagerFactoryBean() {{
                setDataSource(dataSourceRead);
                setPersistenceProviderClass(HibernatePersistenceProvider.class);
                setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
                setPackagesToScan(MODEL_PACKAGE);
                setJpaProperties(JPA_READ_PROPERTIES);
            }};
        }
    
        @Bean
        public PlatformTransactionManager transactionManagerRead(EntityManagerFactory entityManagerFactoryRead) {
            return new JpaTransactionManager(entityManagerFactoryRead);
        }
    }
    
This part used to name entityManagerFactory, transactionManager and set the location of repositories.

[NOTE] *Repositories for read and write should be located in different packages. Models can be used the same for both repositories. Names also needs to be different as they are names of beans*


    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryRead",
            transactionManagerRef = "transactionManagerRead",
            basePackages = {"com.phoosop.springbootmultidatasource.repository.read"}
    )
    
Configuration of datasource:

[NOTE] *Please be careful with names of injected beans*

    public DataSourceConfigRead(HikariReadConfigurations hikariReadConfigurations) {
        this.setPoolName(hikariReadConfigurations.getPoolName());
        this.setMinimumIdle(hikariReadConfigurations.getMinimumIdle());
        this.setMaximumPoolSize(hikariReadConfigurations.getMaximumPoolSize());
        this.setIdleTimeout(hikariReadConfigurations.getIdleTimeout());
    }
    
    @Bean
    public HikariDataSource dataSourceRead() {
        return new HikariDataSource(this);
    }
    
Entity manager factory:

[NOTE] *Please be careful with names of injected beans*

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRead(
            final HikariDataSource dataSourceRead) {
        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSourceRead);
            setPersistenceProviderClass(HibernatePersistenceProvider.class);
            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            setPackagesToScan(MODEL_PACKAGE);
            setJpaProperties(JPA_READ_PROPERTIES);
        }};
    }    
    
## Service

Here we are using two repositories under the same service. One is for writing data and one is for getting data.

Main logic of separation between writing and reading needs to be here.

    @Service
    @RequiredArgsConstructor
    public class UserPersistenceService {
    
        private final UserRepositoryRead userRepositoryRead;
        private final UserRepositoryWrite userRepositoryWrite;
        private final ConversionService conversionService;
    
        public List<UserCommand> findAll() {
            return userRepositoryRead.findAll()
                    .stream()
                    .map(item -> conversionService.convert(item, UserCommand.class))
                    .collect(Collectors.toList());
        }
    
        public UserCommand save(UserCommand userCommand) {
            UserEntity userEntity = conversionService.convert(userCommand, UserEntity.class);
            userRepositoryWrite.save(userEntity);
            userCommand.setId(userEntity.getId());
            return userCommand;
        }
    
    }

    
## Project structure 

Only related packages

    com.name.project -> config
                        
                        model      -> entity
                                      
                        repository -> read
                                      write
                        
                        service