CREATE TABLE employees (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR2(50),
    position VARCHAR2(50),
    salary_per_hour NUMBER(8, 2)
);

CREATE TABLE payroll (
    employee_id NUMBER,
    hours_worked NUMBER(5, 2),
    deductions NUMBER(8, 2),
    final_salary NUMBER(10, 2),
    CONSTRAINT fk_employee
        FOREIGN KEY (employee_id) 
        REFERENCES employees(id)
);