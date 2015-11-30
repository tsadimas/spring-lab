package gr.hua.lab2;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.hua.lab2.model.EmployeeDAO;
import gr.hua.lab2.model.Employee;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		 ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

	    Employee emp = (Employee) context.getBean("employee");
	      
		emp.setName("Mitsos");
		
		 Employee emp1 = (Employee) context.getBean("employee");
	      
			emp1.setName("Mitsos1");
		
		String formattedDate = dateFormat.format(date);
		
		
		EmployeeDAO employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
		
		List<Employee> empList = employeeDAO.getAll();
		List<Employee> staff = employeeDAO.getStaff();
		model.addAttribute("employees",	empList);
		model.addAttribute("staff",	staff);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("emp", emp.getName() );
		model.addAttribute("emp1", emp1.getName() );
		
		return "home";
	}
	
}
