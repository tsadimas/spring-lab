package gr.hua.lab2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This employee is not found in the system")
public class EmployeeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 100L;
}

