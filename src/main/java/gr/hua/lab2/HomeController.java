package gr.hua.lab2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	public String addEmployee(@ModelAttribute("newEmployee") @Valid Employee employee, BindingResult result) {

		 if (result.hasErrors()) {
	            return "employee";
	        }
		 
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
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadfile() {
		return "upload";
	}
	
	@RequestMapping(value = "/multiupload", method = RequestMethod.GET)
	public String multiuploadfile() {
		return "uploadMultiple";
	}
	
	/**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
 
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                System.out.println("Upload path "+dir );
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());
 
                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }
 
    /**
     * Upload multiple file using Spring Controller
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadMultipleFileHandler(@RequestParam("name") String[] names,
            @RequestParam("file") MultipartFile[] files) {
 
        if (files.length != names.length)
            return "Mandatory information missing";
 
        String message = "";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = names[i];
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());
 
                message = message + "You successfully uploaded file=" + name
                        + "<br />";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        return message;
    }
	
	}
