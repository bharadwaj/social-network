package com.my.network.socialnetwork;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.base.Preconditions;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "networkEntityManager",
        transactionManagerRef = "networkTransactionManager",
        basePackages = "com.my.network.socialnetwork"
)
public class NetworkDbConfig {
    @Autowired
    private Environment env;

    //
    @Primary
    @Bean
    public PlatformTransactionManager networkTransactionManager() {
        return new JpaTransactionManager(networkEntityManager().getObject());
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean networkEntityManager() {

        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(networkDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(NetworkDbConfig.class.getPackage().getName()+".model");

/*final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        factoryBean.setJpaPropertyMap(properties);*/


        return factoryBean;
    }

    @Primary
    @Bean
    public DataSource networkDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("networkdb.jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("networkdb.jdbc.user")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("networkdb.jdbc.pass")));

        return dataSource;

    }



}
