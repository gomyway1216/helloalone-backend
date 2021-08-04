package com.yudaiyaguchi.helloalonebackend;

import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.yudaiyaguchi.helloalonebackend.firebase.Task;
import com.yudaiyaguchi.helloalonebackend.firebase.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TaskController.BASE_URL)
public class TaskController {
	
	public static final String BASE_URL = "/api/v1/customers";

	@Autowired
	TaskService taskService;
	
	@PostMapping("/createtask")
	public String createTask(@RequestBody Task task) throws InterruptedException, ExecutionException {
		System.out.println("createtask used");
		try {
			taskService.createTask(task);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "failure";
		}
		return "success";
	}
	
    @GetMapping("/tasks")
    public String showForm() {
    	System.out.println("sample");
        return "sample";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String viewAccount() {
    	System.out.println("account");
        return "accountSummary";
    }
}
