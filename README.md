
Platform built using Spring Boot, Spring Security with JPA Authentication, Spring Data JPA with MySQL, Spring MVC. The frontend is built using Angular

# Recent Changes

- Updated to latest spring boot version - 2.6.2
- Added 'spring-boot-starter-validation' dependency in pom.xml file to support Java Bean Validation annotations.
- *IMPORTANT*: Removed Legacy JWT Authentication Mechanism and replaced it with latest Spring Security JWT Support.
- Adapted SecurityConfig.java class according to latest Spring Security configuration settings, see method configure(AuthenticationManagerBuilder) inside SecurityConfig.java class
- Set spring.jpa.open-in-view property as false.

