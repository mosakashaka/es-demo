package org.msksk.esdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//下面此行用来排序的注解接口,用于处理加载优先级的问题,拥有两个枚举变量
@Order(Ordered.HIGHEST_PRECEDENCE)
//下面此行代表此类为配置类
@Configuration
//下面此行代表此类开启事务管理
@EnableTransactionManagement(proxyTargetClass = true)
//也可以定义为类 如DeptRepository.class   也可以定义过滤器 includeFilters={ @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Service.class)}
@EnableJpaRepositories(basePackages="org.msksk.esdemo.repository")
public class MybatisConfig {
}
