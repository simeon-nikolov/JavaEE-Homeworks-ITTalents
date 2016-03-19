# 1. Write a SQL query to find the average salary in the "Sales" department. Use AVG(). 

select avg(salary) from hr.employees e
join hr.departments d on (d.department_id = e.department_id and d.department_name = 'Sales');

# 2. Write a SQL query to find the number of employees in the "Sales" department.
# Use COUNT(*).

select count(*) from hr.employees e
join hr.departments d on (d.department_id = e.department_id and d.department_name = 'Sales');

# 3. Write a SQL query to find the number of all locations where the company has an office.

select count(*) from hr.locations l
join hr.departments d on (d.location_id = l.location_id);

# 4. Write a SQL query to find the number of all departments that has manager.

select count(*) from hr.departments where manager_id is not null;

# 5. Write a SQL query to find the number of all departments that has no manager. 

select count(*) from hr.departments where manager_id is null;

# 6. Write a SQL query to find all departments' names and the average salary for each of them.

select d.department_name as 'Department naem', avg(e.salary) as 'Avarage salary'
from hr.employees e
join hr.departments d on (d.department_id = e.department_id)
group by e.department_id;

# 7. Write a SQL query to find the count of all employees in each department. Display the 
# name, location and number of employees for each department.

select count(e.employee_id) as 'Employees count', d.department_name as 'Department name', l.city as 'Location'
from hr.employees e
join hr.departments d on (e.department_id = d.department_id)
join hr.locations l on (d.location_id = l.location_id)
group by e.department_id;

# 8. Write a SQL query to find for each department and for each manager the count of all 
# corresponding employees.

select count(*) as 'Employees count', concat(m.first_name, ' ', m.last_name) as 'Manager name', 
	d.department_name as 'Department name'
from hr.employees e
join hr.employees m on (e.manager_id = m.employee_id)
join hr.departments d on (e.department_id = d.department_id)
group by e.department_id, e.manager_id;

# 9. Write a SQL query to find all managers that have exactly 5 employees. Display their 
# names and the name and location of their department.

select concat(m.first_name, ' ', m.last_name) as 'Manager name',
d.department_name as 'Depratment name', l.city as 'Location'
from hr.employees m
join hr.employees e on (e.manager_id = m.employee_id)
join hr.departments d on (m.department_id = d.department_id)
join hr.locations l on (d.location_id = l.location_id)
group by e.manager_id
having count(e.employee_id) = 5;

# 10. Write a SQL query to find the total number of employees for each region.

select count(e.employee_id), r.region_name as 'Region'
from hr.employees e 
join hr.departments d on (e.department_id = d.department_id)
join hr.locations l on (d.location_id = l.location_id)
join hr.countries c on (l.country_id = c.country_id)
join hr.regions r on (c.region_id = r.region_id)
group by r.region_id;

# 11. Write a SQL query to find for each department and for each job title the total number of 
# employees.

select count(e.employee_id) as 'Employees count', j.job_title as 'Job Title', d.department_name as 'Department name'
from hr.employees e
join hr.departments d on (d.department_id = e.department_id)
join hr.jobs j on (j.job_id = e.job_id)
group by e.department_id, e.job_id;

# 12. Write a SQL query to find the names and salaries of the employees that take the 
# minimal salary in the company. Use nested SELECT statement.

select concat(e.first_name, ' ', e.last_name) as 'Full name', e.salary as 'Min Salary'
from hr.employees e where e.salary = (select MIN(salary) from hr.employees);

# 13. Write a SQL query to find the names and salaries of the employees that get a salary that 
# is up to 10% higher than the minimal salary for the company.

select concat(e.first_name, ' ', e.last_name) as 'Full name', e.salary as 'Salary'
from hr.employees e where e.salary <= (select MIN(salary) * 1.1 from hr.employees);

# 14. Write a SQL that displays all departments and the highest salary for each department 
# along with the name of the employee that takes it. If multiple employees in the same
# department have highest salary, display the first of them.

select d.department_name as 'Department name', 
	e.salary as 'Highest salary', concat(e.first_name, ' ', e.last_name) as 'Employee name'
from hr.employees e join hr.departments d on (d.department_id = e.department_id)
group by e.department_id
having e.salary in (select max(salary) from hr.employees group by department_id);

# 15. Write a SQL query to find the names of all employees whose last name is exactly 5 
# characters long.

select concat(e.first_name, ' ', e.last_name) as 'Full name'
from hr.employees e where length(e.last_name) = 5;

# 16. Write a SQL query to find the names of all employees whose first name and last name 
# start with the same letter. 

select concat(e.first_name, ' ', e.last_name) as 'Full name'
from hr.employees e where substr(e.first_name, 1, 1) = substr(e.last_name, 1, 1);

# 17. Display all departments names and their manager's name. For departments without
# manager display "(No manager)".

select d.department_name as 'Depratment name', 
	coalesce(concat(m.first_name, ' ', m.last_name), 'No manager') as 'Manager name'
from hr.employees m
right outer join hr.departments d on (d.manager_id = m.employee_id);

# 18. Display all employees along with their number of directly managed people. For  
# employees not managing anybody display "Just an employee". For employees 
# managing only 1 employee display "Junior manager".

select concat(m.first_name, ' ', m.last_name), count(e.employee_id)
from hr.employees m
left outer join hr.employees e on (e.manager_id = m.employee_id)
group by e.manager_id;

# 19. Write a SQL query to print the current date and time in the format 
# "hour:minutes:seconds day-month-year". Display also the date coming after a week.

select date_format(now(), '%H:%i:%s %d-%m-%Y'), date_format(date_add(now(), INTERVAL 7 DAY), '%H:%i:%s %d-%m-%Y');

# 20. Write a SQL statement to create a table USERS. Users should have username, 
# password, full name and last login time. Choose appropriate data types for the fields of 
# the table. Define a primary key column with a primary key constraint.  Define a trigger 
# to automatically fill the full name column value before inserting a record.

CREATE TABLE IF NOT EXISTS hr.users (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(30) NOT NULL,
	`password` VARCHAR(30) NOT NULL,
	fullname VARCHAR(50),
	last_login TIMESTAMP
);

# 21. Write a SQL statement to create a view that displays the users from the USERS table
# that have been in the system today. Test if the view works correctly.

SELECT * FROM hr.users WHERE DATE(`last_login`) = CURDATE();

# 22. Write a SQL statement to create a table GROUPS. Groups should have unique name 
# (use unique constraint).

CREATE TABLE IF NOT EXISTS hr.groups (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	group_name VARCHAR(30) NOT NULL UNIQUE
);

# 23. Write a SQL statement to add a column GROUP_ID to the table USERS. Fill some data 
# in this new column and as well in the GROUPS table. Write a SQL statement to add a 
# foreign key constraint between tables USERS and GROUPS. 

ALTER TABLE hr.users
ADD group_id int(6) unsigned,
ADD FOREIGN KEY (group_id) REFERENCES hr.groups(id);

# 24. 

# ...


#33.Write a SQL query to find all the average work hours per week for each country.
select c.country_name as 'Country Name', avg(sum(w.hours)) as 'Avarage working hours' from hr.workhours w
join hr.emplyees e on w.employee_id = e.employee_id
join hr.departments d on e.department_id = d.department_id
join hr.locations l on l.location_id = d.location_id
join hr.countries c on c.country_id = l.country_id
group by c.country_id
having w.date between now() and (now() - 5);