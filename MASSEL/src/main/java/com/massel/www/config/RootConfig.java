package com.massel.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.massel.www"})
@MapperScan(basePackages = {"com.massel.www.repository"})
public class RootConfig {	
	
	@Autowired
	ApplicationContext applicationcontext;
	
	//DB설정
	//log4jdbc-log4j2 라이브러리 사용시 Driver "net.sf.log4jdbc.sql.jdbcapi.DriverSpy"
	
	@Bean //ApllicationContext에 bean으로 등록 어노테이션(있는 라이브러리 => bean 아니면 component)
	public DataSource dataSource() {
		HikariConfig hikariconfig = new HikariConfig(); 
		hikariconfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariconfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/masseldb");
		hikariconfig.setUsername("");
		hikariconfig.setPassword("");
		hikariconfig.setMinimumIdle(5); // 최소 유효한 커넥션 수
		hikariconfig.setConnectionTestQuery("SELECT now()"); // test 쿼리
		hikariconfig.setPoolName("springHikariCP");
		hikariconfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		hikariconfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "200");
		hikariconfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "true");
		hikariconfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariconfig);
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSource());
		//설정 사항이 저장되어있는 경로
		sqlFactoryBean.setConfigLocation(
				applicationcontext.getResource("classpath:/MybatisConfig.xml"));
		sqlFactoryBean.setMapperLocations(
				applicationcontext.getResources("classpath:/Mappers/*.xml"));
		return (SqlSessionFactory)sqlFactoryBean.getObject();
		
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	
}

