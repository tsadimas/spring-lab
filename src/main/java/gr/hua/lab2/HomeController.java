package gr.hua.lab2;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String employee(Model model) {
		Employee newEmployee = new Employee();
		model.addAttribute("newEmployee", newEmployee);
		return "employee";
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute("newEmployee") Employee employee) {

		if (employee.getId() == 0) {
			employeeDAO.save(employee);
		} else {
			employeeDAO.update(employee);
		}
		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public String editPerson(@PathVariable("id") int id, Model model) {
		model.addAttribute("newEmployee", employeeDAO.getById(id));
		return "employee";
	}

	@RequestMapping("/remove/{id}")
	public String removePerson(@PathVariable("id") int id) {

		employeeDAO.deleteById(id);
		return "redirect:/";
	}
}
