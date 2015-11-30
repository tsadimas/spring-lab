package gr.hua.lab2.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Employee {
	 
    private int id;
    
    @Size(min=2, max=30)
    private String name;
    
    @Size(min=2, max=30)
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