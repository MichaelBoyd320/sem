package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get a single Employee
        // Employee emp = a.getEmployee(255530);
        // get employees based on role
        //ArrayList<Employee> emps = a.getEmployeesRole("Engineer");
        // get all employees
        //ArrayList<Employee> emps = a.getAllSalaries();

        // get department
        Department dept = a.getDepartment("Sales");

        displayEmployee(dept.manager);

        // Display results
        //a.displayEmployees(emps);

        // Disconnect from database
        a.disconnect();
    }

    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */


    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected aaaaa");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement

            // employee has emp no
            // title has emp no
            // salaries has emp no
            // dept_emp has emp no and dept no
            // dept has dept no
            // dept_manager has managerIds and dept no

            // to get dept name for employee, we need to select dept_name where d.dept_no = de.dept_no AND de.emp_no = e.emp_no
            // and to get the name of the manager, we need to
            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, titles.title, salaries.salary, departments.dept_name, m.first_name AS 'manager_name'"
                            + "FROM employees e, titles, salaries, departments, dept_emp, dept_manager "
                            + "JOIN employees m ON m.emp_no = dept_manager.emp_no "
                            + "WHERE e.emp_no = " + ID + " AND titles.emp_no = e.emp_no AND salaries.emp_no = e.emp_no "
                            + "AND dept_emp.emp_no = e.emp_no AND departments.dept_no = dept_emp.dept_no "
                            + "AND dept_manager.dept_no = departments.dept_no ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept.dept_name = rset.getString("dept_name");
                //emp.manager = rset.getString("manager_name");

                return emp;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public ArrayList<Employee> getEmployeesRole(String Role) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement

            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, titles.title, salaries.salary "
                            + "FROM employees, salaries, titles "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = titles.emp_no "
                            + "AND salaries.to_date = '9999-01-01' AND titles.to_date = '9999-01-01'"
                            + "AND titles.title = '" + Role + "' "
                            + "ORDER BY employees.emp_no ASC";

            // Execute SQL statement


            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            ArrayList<Employee> employees = new ArrayList<Employee>();

            int count = 0;
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                employees.add(emp);
            }

            return employees;


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    static public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void displayEmployees(ArrayList<Employee> emps) {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s %-10s %-30s %-20s", "Emp No", "First Name", "Last Name", "Salary", "Title", "Manager", "Department"));
        // Loop over all employees in the list
        for (Employee emp : emps) {
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary, emp.title, emp.manager, emp.dept.dept_name);
            System.out.println(emp_string);
        }
    }

   // public ArrayList<Employee> getSalariesByDepartment(Department dept)
  //  {
        // get an array list of all employees
  //  }

    public Department getDepartment(String dept)
    {
        // get the details of a specific department based on just the name
//
        Department department = new Department();
        department.dept_no = "";
        department.dept_name = dept;

        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // get the details of the department
            String strSelect =
                    "SELECT departments.dept_no, departments.dept_name, dept_manager.emp_no "
                            + "FROM departments, dept_manager "
                            + "WHERE dept_manager.dept_no = departments.dept_no AND departments.dept_name = '" + dept + "' ";

            // Execute SQL statement


            ResultSet rset = stmt.executeQuery(strSelect);


            while (rset.next())
            {
                department.dept_name = rset.getString("dept_name");
                department.dept_no = rset.getString("dept_no");

                Employee manager = getEmployee(rset.getInt("emp_no"));
                manager.dept = department;


                department.manager = manager;
            }

            return department;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details based on department");
            return null;
        }


        //return department;
    }
}