package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get Employee
        // Employee emp = a.getEmployee(255530);
        Employee[] emps = a.getEmployeesRole("Engineer");

        // Display results
        a.displayEmployees(emps);

        // Disconnect from database
        a.disconnect();
    }

    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */



    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected aaaaa");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
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

    public Employee getEmployee(int ID)
    {
        try
        {
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
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept_name = rset.getString("dept_name");
                emp.manager = rset.getString("manager_name");

                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }
    public Employee[] getEmployeesRole(String Role)
    {
        try
        {
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
                    "SELECT count(*) AS 'C', employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
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
            Employee[] employees = new Employee[rset.getInt("C")];
            int count = 0;
            while (rset.next())
            {
                employees[count] = new Employee();
                employees[count].emp_no = rset.getInt("emp_no");
                employees[count].first_name = rset.getString("first_name");
                employees[count].last_name = rset.getString("last_name");
                employees[count].title = rset.getString("title");
                employees[count].salary = rset.getInt("salary");
                employees[count].dept_name = rset.getString("dept_name");
                employees[count].manager = rset.getString("manager_name");

            }

            return employees;


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }
    public void displayEmployees(Employee[] emps)
    {
        if (emps != null)
        {
            for(int i = 0; i < emps.length; ++i) {
                System.out.println(
                        emps[i].emp_no + " "
                                + emps[i].first_name + " "
                                + emps[i].last_name + "\n"
                                + emps[i].title + "\n"
                                + "Salary:" + emps[i].salary + "\n"
                                + emps[i].dept_name + "\n"
                                + "Manager: " + emps[i].manager + "\n");
            }
        }
    }
}