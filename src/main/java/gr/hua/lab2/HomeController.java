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

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
	// Get the EmployeeDAO Bean
	EmployeeDAO employeeDAO = ctx.getBean("employeeDAO", EmployeeDAO.class);

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("employee", new Employee());
		List<Employee> empList = employeeDAO.getAll();
		model.addAttribute("employees", empList);
		return "home";

	}

	
}
