# SpringMiniProject

> 본 프로젝트는 스프링 학습을 위한 강의 기반 프로젝트

> [참고 강의사이트](https://www.inflearn.com/course/spring-mvc5-project/dashboard)

## 개발 환경
**Front-end**
  * HTML5
  * CSS3
  * BootStrap 4.0
  
**Back-end**
  * Java 11
  * Oracle 11g

**Tools**
  * Eclipse (Spring Framework 5.0)
  * apache/tomcat9.0
  * SQL developer
 
 
## 시작하기

### [Spring-Blog-Java](https://github.com/GeneratorEOM/Spring-Blog-Java) 와 동일한 프로젝트이며 
### 스프링 설정 부분만 XML 형식으로 설정되었다.

### XML 형식 설정
  
파일 구조는 아래와 같다.  
  
<img src="https://user-images.githubusercontent.com/64389409/99355104-0cacaa00-28eb-11eb-8650-2522eb8e9a5a.PNG" width="30%" heigth="30%">  
  
* web.xml
  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="4.0"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
						http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd">
	<!-- 현재 웹 어플리케이션에서 받아들이는 모든 요청에 대해 appServlet이라는 이름으로 정의되어 있는 서블릿을 사용하겠다 -->
	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	<!-- 요청 정보를 분석해서 컨트롤러를 선택하는 서블릿을 지정한다. -->
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<!-- Spring MVC 에서 제공하고 있는 기본 서블릿을 지정한다. -->
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- Spring MVC 설정을 위한 xml파일을 지정한다. -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/config/servlet-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
		
		<!-- 파일 설정 -->
		<multipart-config>
			<max-file-size>52428800</max-file-size>
			<max-request-size>524288000</max-request-size>
			<file-size-threshold>0</file-size-threshold>
		</multipart-config>
	</servlet>
	
	<!-- Bean  을 정의할 xml 파일을 지정한다 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/config/root-context.xml</param-value>
	</context-param>
	
	<!-- 리스너 설정 -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!-- 파라미터 인코딩 필터 설정 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- 파일 처리 -->
	<filter>
		<display-name>springMultipartFilter</display-name>
		<filter-name>springMultipartFilter</filter-name>
		<filter-class>org.springframework.web.multipart.support.MultipartFilter</filter-class>
		<init-param>
			<param-name>multipartResolverBeanName</param-name>
			<param-value>multipartResolver</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>springMultipartFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
</web-app>
```
  
* servlet-context.xml
  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans
	xmlns="http://www.springframework.org/schema/mvc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc 
      					  http://www.springframework.org/schema/mvc/spring-mvc.xsd
            			  http://www.springframework.org/schema/beans 
            			  http://www.springframework.org/schema/beans/spring-beans.xsd
              			  http://www.springframework.org/schema/context 
              			  http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- 스캔한 패키지 내부의 클래스 중 Controller 어노테이션을 가지고 있는 클래스들을 Controller로 로딩하도록 
		한다. -->
	<annotation-driven/>
	
	<!-- 스캔할 bean들이 모여있는 패키지를 지정한다. -->
	<context:component-scan base-package="kr.co.blog.controller"/>
	<context:component-scan base-package="kr.co.blog.dao"/>
	<context:component-scan base-package="kr.co.blog.service"/>
	
	<!-- Controller의 메서드에서 반환하는 문자열 앞 뒤에 붙일 경로 정보를 셋팅한다. -->
	<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<beans:property name="prefix" value="/WEB-INF/views/"></beans:property>
		<beans:property name="suffix" value=".jsp"></beans:property>
	</beans:bean>
	
	<!-- 정적파일(이미지, 사운드, 동영상, JS, CSS 등등) 경로 셋팅 -->
	<resources mapping="/**" location="/resources/"></resources>
	
	<!-- properties 파일의 내용을 사용할수 있도록 Bean을 정의한다. -->
	<beans:bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<beans:property name="locations">
			<beans:list>
				<beans:value>/WEB-INF/properties/db.properties</beans:value>
				<beans:value>/WEB-INF/properties/option.properties</beans:value>
			</beans:list>
		</beans:property>
	</beans:bean>
	
  <!-- mybatis 셋팅 -->
	<beans:bean class="org.apache.commons.dbcp2.BasicDataSource" id="basic_data_source">
		<beans:property name="driverClassName" value="${db.classname}"/>
		<beans:property name="url" value="${db.url}"/>
		<beans:property name="username" value="${db.username}"/>
		<beans:property name="password" value="${db.password}"/>
	</beans:bean>
	
	<beans:bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSession">
		<beans:property name="dataSource" ref="basic_data_source"></beans:property>
		<beans:property name="mapperLocations" value="/WEB-INF/mapper/*.xml"></beans:property>
	</beans:bean>
	
	<beans:bean class="org.mybatis.spring.SqlSessionTemplate" id="sqlSessionTemplate">
		<beans:constructor-arg index="0" ref="sqlSession"/>
	</beans:bean>
	
	<!-- 인터셉터 셋팅 -->
	<interceptors>
		<interceptor>
			<mapping path="/**"/>
			<beans:bean class="kr.co.blog.interceptor.TopMenuInterceptor"/>
		</interceptor>
		<interceptor>
			<mapping path="/user/modify"/>
			<mapping path="/user/logout"/>
			<mapping path="/board/*"/>
			<exclude-mapping path="/board/main"/>
			<beans:bean class="kr.co.blog.interceptor.CheckLoginInterceptor"/>
		</interceptor>
		<interceptor>
			<mapping path="/board/modify"/>
			<mapping path="/board/delete"/>
			<beans:bean class="kr.co.blog.interceptor.CheckWriterInterceptor"/>
		</interceptor>
	</interceptors>
	
	<!-- 메세지 등록 -->
	<beans:bean class="org.springframework.context.support.ReloadableResourceBundleMessageSource" id="messageSource">
		<beans:property name="basenames">
			<beans:list>
				<beans:value>/WEB-INF/properties/error_message</beans:value>
			</beans:list>
		</beans:property>
	</beans:bean>

	<!-- 메세지 접근을 위한 접근자 등록 -->
	<beans:bean class="org.springframework.context.support.MessageSourceAccessor">
		<beans:constructor-arg ref="messageSource"/>
	</beans:bean>
	
	<beans:bean class="org.springframework.web.multipart.support.StandardServletMultipartResolver" id="multipartResolver"/>
</beans:beans>

```
 설정부분만 xml 형식으로 바뀌었고 나머지는 대부분 동일하다.   
   
 다른점이 가물가물한데 생각나는것을 적어보려고 한다.  
   
 ``` java
public class CheckLoginInterceptor implements HandlerInterceptor{
  // xml 방식에서는 주입 받을 수 있다.
  @Resource(name = "loginUserBean")
  // 지연 로딩
  @Lazy
  private UserBean loginUserBean;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
	
    if(loginUserBean.isUserLogin() == false) {
      String contextPath = request.getContextPath();
      response.sendRedirect(contextPath + "/user/not_login");
      return false; 
    }	
    return true;
  }	
 }
```
     
 java 설정에서는 인터셉터에서 객체를 주입받을 수 없다고 하였는데 xml 에서는 가능하다고 한다.
      
 @Lazy 어노테이션은 객체가 실제 사용될 때 로딩하도록 해주는 어노테이션이다. ( 지연 로딩 )  
   
 만약 @Lazy 가 없으면 오류가 생긴다.. 왜??  
 
 LoginUserBean 은 @SessionScope 를 통해서 객체를 주입 받게 설정되었다.
 
  @Autowired 나 @resource 같은 어노테이션들은 서버가 실행될 때 자동으로 객체를 주입하려고 시도한다.
   
 이때 서로의 주입 시점을 생각해보자.  
 
 @Autowired & @resource 는 서버가 실행될 때, @SessionScope 는 브라우저에서 최초 요청이 들어올 때 주입 받는다.
 
 그렇다면 서버가 실행될 때 @resource 이니까 LoginUserBean 에 주입을 시도하는데 @SessionScope 가 떡하니 설정되어있다.  
 
 @resource 는 지금 주입해야 하는데 @SessionScope 는 브라우저 요청이 들어와야하니까 서로 충돌이 발생해서 오류가 나지 않았을까 예상해본다.
  
 이러한 오류는 xml 방식에서만 생기는 오류라고 한다.    











