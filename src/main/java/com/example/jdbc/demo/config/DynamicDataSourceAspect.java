package com.example.jdbc.demo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * Dao aspect.
     */
    @Pointcut("@annotation(com.example.jdbc.demo.config.TargetDataSource))")
    public void daoAspect() {
    }

    /**
     * Switch DataSource
     *
     * @param point the point
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        MethodSignature  signature = (MethodSignature)point.getSignature();

        TargetDataSource targetDataSource = signature.getMethod().getAnnotation(TargetDataSource.class);

        DataSourceContextHolder.setDBType(targetDataSource.dataSourceKey().name());

        logger.info("Restore DataSource to [{}] in Method [{}]",
                targetDataSource.dataSourceKey().name());
    }


    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DataSourceContextHolder.clearDBType();
        logger.info("Restore DataSource to [{}] in Method [{}]",
                DataSourceContextHolder.getDBType(), point.getSignature());
    }

}
