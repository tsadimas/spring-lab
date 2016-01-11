package gr.hua.lab2;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import gr.hua.lab2.model.Employee;
import gr.hua.lab2.model.EmployeeDAO;


@Controller
public class ApiController {

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
	// Get the EmployeeDAO Bean
	EmployeeDAO employeeDAO = ctx.getBean("employeeDAO", EmployeeDAO.class);

	
	@RequestMapping(value = "/employees", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Employee> getEmployeesInJSON() {
	List<Employee>employees=employeeDAO.getAll();
   	 return employees;
    }
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Employee getEmployeeInJSON(@PathVariable Integer id) throws EmployeeNotFoundException {
	Employee emp=employeeDAO.getById(id);
	
	if (emp == null) {
		throw new EmployeeNotFoundException();
	}
   	 return emp;
    }
	
	@RequestMapping(value = "/employee/add", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED) 
	public @ResponseBody Employee insertEmployeeInJSON(@RequestBody Employee empl)  {
	employeeDAO.save(empl);
   	 return empl;
    }
	
	
	@RequestMapping(value = "/employee/addparam", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED) 
	public @ResponseBody Employee paraminsertEmployeeInJSON(@RequestParam("name") String name,@RequestParam("role") String role )  {
	Employee empl=new Employee();
	empl.setName(name);
	empl.setRole(role);
	Integer i=employeeDAO.save(empl);
	empl.setId(i);
	System.out.println("Employee saved id = " + i);		
   	 return empl;
    }
	
	@RequestMapping(value = "/employee/remove/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public  @ResponseBody Employee paramdeleteEmployeeInJSON(@PathVariable Integer id) throws EmployeeNotFoundException {
		Employee emp=null;
		
	
		emp=employeeDAO.getById(id);
		if (emp==null) {
			throw new EmployeeNotFoundException();
		}
		employeeDAO.deleteById(id);

		
		return emp;
		
	}
	
	
	    
}



