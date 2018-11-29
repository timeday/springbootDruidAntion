package com.example.jdbc.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.default.url}")
    private String defaultDBUrl;
    @Value("${spring.datasource.default.username}")
    private String defaultDBUser;
    @Value("${spring.datasource.default.password}")
    private String defaultDBPassword;
    @Value("${spring.datasource.default.driver-class-name}")
    private String defaultDBDreiverName;

    @Value("${spring.datasource.master.url}")
    private String masterDBUrl;
    @Value("${spring.datasource.master.username}")
    private String masterDBUser;
    @Value("${spring.datasource.master.password}")
    private String masterDBPassword;
    @Value("${spring.datasource.default.driver-class-name}")
    private String masterDBDreiverName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;


    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

        DruidDataSource defaultDataSource = new DruidDataSource();
        defaultDataSource.setUrl(defaultDBUrl);
        defaultDataSource.setUsername(defaultDBUser);
        defaultDataSource.setPassword(defaultDBPassword);
        defaultDataSource.setDriverClassName(defaultDBDreiverName);

        //configuration
        defaultDataSource.setInitialSize(initialSize);
        defaultDataSource.setMinIdle(minIdle);
        defaultDataSource.setMaxActive(maxActive);
        defaultDataSource.setMaxWait(maxWait);
        defaultDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        defaultDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        defaultDataSource.setValidationQuery(validationQuery);
        defaultDataSource.setTestWhileIdle(testWhileIdle);
        defaultDataSource.setTestOnBorrow(testOnBorrow);
        defaultDataSource.setTestOnReturn(testOnReturn);
        defaultDataSource.setPoolPreparedStatements(poolPreparedStatements);
        defaultDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            defaultDataSource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: "+ e);
        }
        defaultDataSource.setConnectionProperties(connectionProperties);


        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName(masterDBDreiverName);
        masterDataSource.setUrl(masterDBUrl);
        masterDataSource.setUsername(masterDBUser);
        masterDataSource.setPassword(masterDBPassword);

        //configuration
        masterDataSource.setInitialSize(initialSize);
        masterDataSource.setMinIdle(minIdle);
        masterDataSource.setMaxActive(maxActive);
        masterDataSource.setMaxWait(maxWait);
        masterDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        masterDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        masterDataSource.setValidationQuery(validationQuery);
        masterDataSource.setTestWhileIdle(testWhileIdle);
        masterDataSource.setTestOnBorrow(testOnBorrow);
        masterDataSource.setTestOnReturn(testOnReturn);
        masterDataSource.setPoolPreparedStatements(poolPreparedStatements);
        masterDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            masterDataSource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: "+ e);
        }
        masterDataSource.setConnectionProperties(connectionProperties);


        Map<Object,Object> map = new HashMap<>();
        map.put(DataSourceKey.ZHWDEMO.name(), defaultDataSource);
        map.put(DataSourceKey.WSS.name(), masterDataSource);
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setTypeAliasesPackage("com.example.jdbc.demo.entity");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/*.xml"));
        return bean.getObject();

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 事务管理
     *此处事务只能回滚默认数据源操作，如果需要回滚其他数据源的操作请使用分布式事务进行处理。
     * @return 事务管理实例
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager(){
       return  new DataSourceTransactionManager(dynamicDataSource());
    }


}
