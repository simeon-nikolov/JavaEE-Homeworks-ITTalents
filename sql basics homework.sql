#Simeon Nikolov, Java EE 2

#1. Write a SQL query to display all information about all departments.

select * from hr.departments;

#2. Write a SQL query to find all department names.

select department_name as 'Department name' from hr.departments;

#3. Write a SQL query to find the salary of each employee by month, by day 
# and hour. Consider that one month has 20 workdays and each workday 
# has 8 work hours.

select 
	salary / 12 as 'Monthly salary', 
    (salary / 12) / 20 as 'Daily salary', 
    ((salary / 12) / 20) / 8 as 'Salary per hour'
from hr.employees order by salary desc;

# 4. Write a SQL query to find the email addresses of each employee. 
# Consider that the mail domain is mail.somecompany.com. Emails should 
# look like "bernst@mail.somecompany.com". The produced column 
# should be named "Full Email Address".

select 
	concat(email, '@mail.somecompany.com') as 'Full Email Address'
from hr.employees;

#5. Write a SQL query to find all different salaries that are paid to 
# the employees. Use DISTINCT.

select distinct salary as 'Different Salaries' from hr.employees order by salary desc;

#6. Write a SQL query to find all departments and all region 
# names, country names and city names as a single list. Use UNION.

select department_name from hr.departments
union
select region_name from hr.regions
union 
select country_name from hr.countries
union
select distinct city from hr.locations;

#7. Write a SQL query to find all information about the 
# employees whose position is "AC_MGR" (Accounting Manager).

select * from hr.employees 
where job_id = 'AC_MGR';

#8. Write a SQL query to find the names of all employees whose 
#first name starts with "Sa". Use LIKE.

select concat(first_name, ' ', last_name) as 'Full Name' 
from hr.employees
where first_name like 'Sa%';

#9. Write a SQL query to find the names of all employees whose last name 
# contains the character sequence "ei". Use LIKE.

select concat(first_name, ' ', last_name) as 'Full Name' 
from hr.employees
where last_name like '%ei%';

#10. Write a SQL query to find the names of all employees whose 
# salary is in the range [3000...5000]. Use BETWEEN.

select concat(first_name, ' ', last_name) as 'Full Name' 
from hr.employees
where salary between 3000 and 5000;

#11. Write a SQL query to find the names of all employees whose 
# salary is in the range [2000...15000] but is not in range [5000 … 10000]. 
# Use MINUS.

select concat(first_name, ' ', last_name) as 'Full Name', salary as 'Salary'
from hr.employees
where (salary between 2000 and 15000) and (salary not between 5000 and 10000); #MySQL does not support MINUS

#12. Write a SQL query to find the names of all employees whose 
# salary is 2500, 4000 or 5000. Use IN.

select concat(first_name, ' ', last_name) as 'Full Name', salary as 'Salary'
from hr.employees
where salary in (2500, 4000, 5000);

#13. Write a SQL query to find all locations that have no state or 
# post code defined. Use IS NULL.

select * from hr.locations 
where state_province is null or postal_code is null;

#14. Write a SQL query to find all employees that are paid more 
# than 10 000. Order them in decreasing order by salary. Use ORDER BY.

select concat(first_name, ' ', last_name) as 'Full Name', salary as 'Salary'
from hr.employees
where salary > 10000
order by salary desc;

#15. Write a SQL query to find the first 10 employees joined the 
# company (most senior people).

select concat(first_name, ' ', last_name) as 'Full Name', hire_date as 'Hire date'
from hr.employees
order by hire_date limit 10;

#16. Write a SQL query to find all departments and the town of 
# their location. Use NATURAL JOIN.

select d.department_name as 'Department name', l.city as 'City'
from hr.departments d natural join hr.locations l;

#17. Write a SQL query to find all departments and the town of 
# their location. Use join with USING clause.

select d.department_name as 'Department name', l.city as 'City'
from hr.departments d join hr.locations l using (location_id);

# 18. Write a SQL query to find all departments and the town of 
# their location. Use inner join with ON clause.

select d.department_name as 'Department name', l.city as 'City'
from hr.departments d join hr.locations l on (l.location_id = d.location_id);

# 19. Modify the last SQL query to include also the name of the 
# manager for each department.

select concat(m.first_name, ' ', m.last_name) as 'Manager Name', 
	d.department_name as 'Department name', l.city as 'City'
from hr.departments d join hr.locations l on (l.location_id = d.location_id)
join hr.employees m on (d.manager_id = m.employee_id);

# 20. Write a SQL query to find all the locations and the 
# departments for each location along with the locations that do not have 
# department. User right outer join.

select l.city as 'Location city', d.department_name as 'Department name'
from hr.departments d right outer join hr.locations l on (d.location_id = l.location_id);

# 21. Rewrite the last SQL query to use left outer join.

select l.city as 'Location city', d.department_name as 'Department name'
from hr.locations l left outer join hr.departments d on (d.location_id = l.location_id);

# 22. Rewrite the last query to use WHERE instead of JOIN.

select l.city as 'Location city', d.department_name as 'Department name'
from hr.locations l, hr.departments d
where (l.location_id = d.location_id)
union
select lc.city as 'Location city', null as 'Department name'
from hr.locations lc
where (lc.location_id not in (select location_id from hr.departments));

# 23. Write a SQL query to find the manager name of each 
# department.  

select concat(m.first_name, ' ', m.last_name) as 'Manager Name',  
	d.department_name as 'Department name'
from hr.departments d 
join hr.employees m on (d.manager_id = m.employee_id);

# 24. Modify the last SQL query to find also the location of each 
# department manager.

select concat(m.first_name, ' ', m.last_name) as 'Manager Name', 
	d.department_name as 'Department name', l.city as 'City'
from hr.departments d join hr.locations l on (l.location_id = d.location_id)
join hr.employees m on (d.manager_id = m.employee_id); # same as 19.

# 25. Write a SQL query to find the names of all employees from 
# the departments "Sales" and "Finance" whose hire year is between 1995 
# and 2000.  

select concat(e.first_name, ' ', e.last_name) as 'Name',
	d.department_name as 'Department name', e.hire_date as 'Hire date'
from hr.employees e
join hr.departments d on e.department_id = d.department_id
where (d.department_name in ('Sales', 'Finance')) and
	(e.hire_date between '1995-1-1' and '2000-1-1');
    
# 26. Find all employees that have worked in the past in the 
# department “Sales”. Use complex join condition.

select distinct concat(e.first_name, ' ', e.last_name) as 'Name'
from hr.job_history jh
join hr.employees e on (jh.employee_id = e.employee_id)
join hr.departments d on (jh.department_id = d.department_id 
	and d.department_name = 'Sales'); 
    
# 27. Write a SQL query to display all employees (first and last 
# name) along with their corresponding manager (first and last name). Use 
# self-join.  

select concat(e.first_name, ' ', e.last_name) as 'Employee name',
	concat(m.first_name, ' ', m.last_name) as 'Manager name'
from hr.employees e join hr.employees m on (e.manager_id = m.employee_id);

# 28. Combine all first names with all last names of the employees 
# with a CROSS JOIN.

select e1.first_name as 'First name', e2.last_name as 'Last name'
from hr.employees e1 cross join hr.employees e2;

# 29. Write a SQL query to display all employees, along with their 
# job title, department, location, country and region. Use multiple joins.

select concat(e.first_name, ' ', e.last_name) as 'Employee name',
	j.job_title as 'Job title', d.department_name as 'Department name',
    l.city as 'Location city', c.country_name as 'Country',
    r.region_name as 'Region' 
from hr.employees e 
left outer join hr.jobs j on e.job_id = j.job_id
left outer join hr.departments d on e.employee_id = d.department_id
left outer join hr.locations l on d.location_id = l.location_id
left outer join hr.countries c on l.country_id = c.country_id
left outer join hr.regions r on c.region_id = r.region_id;

# 30. Modify the last SQL query to display also the manager name 
# for each employee or “No manager” in case there is no manager. 

select concat(e.first_name, ' ', e.last_name) as 'Employee name',
	coalesce(concat(m.first_name, ' ', m.last_name), 'No manager') as 'Manager name',
	j.job_title as 'Job title', d.department_name as 'Department name',
    l.city as 'Location city', c.country_name as 'Country',
    r.region_name as 'Region' 
from hr.employees e 
left outer join hr.employees m on e.manager_id = m.employee_id
left outer join hr.jobs j on e.job_id = j.job_id
left outer join hr.departments d on e.employee_id = d.department_id
left outer join hr.locations l on d.location_id = l.location_id
left outer join hr.countries c on l.country_id = c.country_id
left outer join hr.regions r on c.region_id = r.region_id;

# 31. Write a SQL query to find all employees that have worked in 
# the past at job position “AC_ACCOUNT” and at some time later at job 
# position “AC_MGR”. Display the employees’ names and current job title.  
#  Hint: first self-join JOB_HISTORY table, then apply filtering and finally 
# join the result with EMPLOYEES and JOBS tables. 

select concat(e.first_name, ' ', e.last_name) as 'Employee name',
	j.job_title as 'Job title'
from hr.job_history jh1
join hr.employees e on (jh1.employee_id = e.employee_id)
join hr.jobs j on (e.job_id = j.job_id)
join hr.job_history jh2 on (jh1.employee_id = jh2.employee_id and
	((jh1.job_id = 'AC_ACCOUNT' and jh2.job_id = 'AC_MGR') or 
		(jh2.job_id = 'AC_ACCOUNT' and j.job_id = 'AC_MGR'))
    and jh1.start_date < jh2.start_date);


