package board.project.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

//환경설정 bean으로 등록한다.
@Configuration
@MapperScan(basePackages = { "board.project.dao" })
public class MybatisConig {
	// <bean id="SqlSessionFactory" class="...SqlSessionFactoryBean">
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
		ssf.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// alias설정하기 - setTypeAliasesPackage 이 패키지 내부에서
		// @Alias("aliasName")을 VO로 인식함
		ssf.setTypeAliasesPackage("board.project.vo");
		// mapper위치 설정하기
		ssf.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
		return ssf.getObject();
	}

	// SqlSessionTemplate
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		SqlSessionTemplate ss = new SqlSessionTemplate(sqlSessionFactory);
		return ss;
	}
}
