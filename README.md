# spring-lab
Εργαστήριο 2 : Beans και datasources
Δημιουργία Project
File → New → Other → Spring Project → Spring MVC Project
Δώστε σαν όνομα του project το lab2 και σαν όνομα για το top-level package το gr.hua.lab2

Δημιουργήστε ένα νέο package κάτω από το gr.hua.lab2  με όνομα model
και εκεί δημιουργήστε μια κλάση Employee.java

package model;

public class Employee {
	 
    private int id;
    private String name;
    private String role;
     
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
     
    @Override
    public String toString(){
        return "{ID="+id+",Name="+name+",Role="+role+"}";
    }
}

Beans
Τα αντικείμενα τα οποία αποτελούν το backend της εφαρμογής σας και τα οποία διαχειρίζονται από τον Spring IoC container ονομάζονται Beans. 
Σαν bean ορίζεται ένα αντικείμενο το οποίο αρχικοποιείται, συναρμολογείται και διαχειρίζεται από τον Spring IoC container. Αυτά τα beans δημιουργούνται μαζί με τα μεταδεδομένα παραμετροποίησης τα οποία παρέχονται στον container με τη μορφή XML <bean/> definitions.
Παράμετροι των beans

Properties
Description
class
This attribute is mandatory and specify the bean class to be used to create the bean.
name
This attribute specifies the bean identifier uniquely. In XML-based configuration metadata, you use the id and/or name attributes to specify the bean identifier(s).
scope
This attribute specifies the scope of the objects created from a particular bean definition and it will be discussed in bean scopes chapter.
constructor-arg
This is used to inject the dependencies. 
properties
This is used to inject the dependencies. 
autowiring mode
This is used to inject the dependencies. 
lazy-initialization mode
A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.
initialization method
A callback to be called just after all necessary properties on the bean have been set by the container.
destruction method
A callback to be used when the container containing the bean is destroyed. 



στη συνέχεια πρπει να δηλώσουμε την κλάση μας σαν bean
Spring bean configuration file (από το src/main/resources New → Other → Spring bean configuration file → επιλογή project → spring.xml σαν όνομα αρχείου → beans  → web-context -spring1 )
Θα δημιουργηθεί ένα αρχείο με όνομα spring.xml 

Για να δηλώσουμε την κλση Employee ότι θα την χρησιμοποιήσουμε σαν bean θα πρπει να το κάνουμε στο αρχείο που δημιουργήσαμε, ως εξής:


 <bean id="employee" class="gr.hua.lab2.model.Employee" scope="singleton">
 </bean>

Όταν ορίζουμε ένα bean στο Spring, έχουμε την επιλογή να ορίσουμε το scope του. Για παράδειγμα για να αναγκάσουμε το Spring να παράγει ένα νέο bean κάθε φορά που το χρειάζεται, πρέπει να ορίσουμε το scope του σαν prototype. Παρομοίως αν θλουμε το Spring να μας επιστρέφει το ίδιο instance του bean κάθε φορά, δηλώνουμε το scope σαν singleton.



Άσκηση: δηλώσατε το employee bean σαν singleton. Προσπαθήστε να δημιουργήσετε 2 instances του και δείτε το αποτέλεσμα.



Τύποι Beans

Scope
Description
singleton
This scopes the bean definition to a single instance per Spring IoC container (default).
prototype
This scopes a single bean definition to have any number of object instances.
request
This scopes a bean definition to an HTTP request. Only valid in the context of a web-aware Spring ApplicationContext.
session
This scopes a bean definition to an HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.
global-session
This scopes a bean definition to a global HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.

Χρήση των beans
Πως θα αποκτήσουμε πρόσβαση σε αυτό το bean μέσα από τον Controller μας;
Με το που δημιουργήσατε την εφαρμογ έχει δημιουργηθεί ένας HomeController στον κατάλογο src/main/java και στο αντίστοιχο package.

Για να αποκτήσουμε πρόσβαση στο bean, πρέπει να φορτώσουμε το bean configuration file, και για να το κάνουμε αυτό, γράφουμε τον εξής κώδικα:

ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
Employee emp = (Employee) context.getBean("employee");
Το αρχείο μας πρέπει να βρίσκεται στον κατάλογο src/main/resources
στη συνέχεια μπορούμε να καλέσουμε οποιαδήποτε μέθοδο της κλάσης employee επιθυμούμε, και να περάσουμε δεδομένα σε κάποιο view.



Άσκηση: Καλέστε τη μέθοδο setname της employee και στείλτε τα δεδομένα στο home view.





Σύνδεση με βάση δεδομένων
Για το λόγο αυτό θα χρειαστούμε να ακόμη bean το οποίο θα είναι τύπου org.springframework.jdbc.datasource.DriverManagerDataSource
Στο ίδιο αρχείο που ορίσαμε το bean προηγουμένως μπορούμε να ορίσουμε και το datasource bean μας, ως εξής:

 <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://SERVER-NAME:3306/DB-NAME" />
        <property name="username" value="DB-USERNAME" />
        <property name="password" value="DB-PASSWORD" />
    </bean>

Για να χρησιμοποιήσουμε μια jdbc σύνδεση με τη βάση mysql, θα χρειαστούμε τα εξής 2 dependencies τα οποία θα πρέπει να τα δηλώσουμε στο pom.xml
spring-jdbc (org.springframework)
mysql-connector-java (com.mysql)
Στο παράδειγμά μας θα υποθσουμε ότι έχουμε μια βάση με ναν πίνακα employee και τα εξής στοιχεία: id, name και role.

CREATE TABLE IF NOT EXISTS `Employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=154 ;




Στοιχεία σύνδεσης σε demo βση:

host: tsadimas.hua.gr
db: dist_syst
user: dist_syst
pass: d1stR

Επίσης, για να επικοινωνήσουμε με τη βάση, θα χρειαστούμε μια DAO κλάση, ακολουθώντας το Data Access Object Pattern.
Για το λόγο αυτό θα δημιουργήσουμε ακόμη 2 κλάσεις:
EmployeeDAO (Interface για την EmployeeDAOImpl)
EmployeeDAOImpl

package gr.hua.lab2.model;

import java.util.List;

import gr.hua.lab2.model.Employee; 
//CRUD operations
public interface EmployeeDAO {
     
    //Create
    public void save(Employee employee);
    //Read
    public Employee getById(int id);
    //Update
    public void update(Employee employee);
    //Delete
    public void deleteById(int id);
    //Get All
    public List<Employee> getAll();
}
και

package gr.hua.lab2.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.sql.DataSource;

import gr.hua.lab2.model.Employee;
 
 
public class EmployeeDAOImpl implements EmployeeDAO {
 
    private DataSource dataSource;
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    public void save(Employee employee) {
        String query = "insert into Employee (id, name, role) values (?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getRole());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee saved with id="+employee.getId());
            }else System.out.println("Employee save failed with id="+employee.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    public Employee getById(int id) {
        String query = "select name, role from Employee where id = ?";
        Employee emp = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                emp = new Employee();
                emp.setId(id);
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                System.out.println("Employee Found::"+emp);
            }else{
                System.out.println("No Employee found with id="+id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emp;
    }
 
    public void update(Employee employee) {
        String query = "update Employee set name=?, role=? where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setInt(3, employee.getId());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee updated with id="+employee.getId());
            }else System.out.println("No Employee found with id="+employee.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    public void deleteById(int id) {
        String query = "delete from Employee where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee deleted with id="+id);
            }else System.out.println("No Employee found with id="+id);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    public List<Employee> getAll() {
        String query = "select id, name, role from Employee";
        List<Employee> empList = new ArrayList<Employee>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                empList.add(emp);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return empList;
    }
}
Η κλάση EmployeeDAOImpl χρησιμοποιεί prepared statements για την επικοινωνία με τη βση δεδομένων. 
Δηλώνουμε λοιπόν σαν bean το EmployeeDAO ώστε να μπορέσουμε να το καλέσουμε μέσα από τον Controller μας (αρχείο spring.xml που δημιουργήσαμε στην αρχή)

<bean id="employeeDAO" class="gr.hua.lab2.model.EmployeeDAOImpl">
        <property name="dataSource" ref="dataSource" />
 </bean>

Με αυτόν τον τρόπο μπορούμε να το καλέσουμε μέσα από τον HomeController μας,

EmployeeDAO employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
List<Employee> empList = employeeDAO.getAll();
model.addAttribute("employees",	empList);

και να το δείξουμε στο view μας

<section>
<table>
	<tr>
	<th> id </th>
	<th> name </th>
	<th> role </th>
	</tr>
	<c:forEach items="${employees}" var="employee"> 
  <tr>
    <td>${employee.id}</td>
    <td>${employee.name}</td>
    <td>${employee.role}</td>
  </tr>
</c:forEach>
</table>
</section>

Μελετήστε το jstl core taglib



Άσκηση: Δημιουργήστε μια ακόμη μέθοδο στον HomeController σας η οποία να προσθέτει έναν νέο Employee στη βάση σας.





Άσκηση: Δημιουργήστε μια ακόμη μέθοδο στον EmployeeDAOImplr  η οποία να επιστρέφει μόνο τους υπαλλήλους που έχουν ρόλο staff.





Υλικό για μελέτη
Spring Bean Definition http://www.tutorialspoint.com/spring/spring_bean_definition.htm
Spring Framework series: Μέρος 1ο http://www.j2ee.gr/spring-framework-series-%CE%BC%CE%AD%CF%81%CE%BF%CF%82-1%CE%BF/
Spring JDBC and JdbcTemplate CRUD with DataSource Example Tutorial http://www.journaldev.com/2593/spring-jdbc-and-jdbctemplate-crud-with-datasource-example-tutorial
Understanding Spring MVC Model and Session Attributes http://www.intertech.com/Blog/understanding-spring-mvc-model-and-session-attributes
Spring MVC: Session http://fruzenshtein.com/spring-mvc-session/



	      

