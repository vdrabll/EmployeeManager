CREATE CAST (varchar AS auth_roles) WITH INOUT AS IMPLICIT;

INSERT INTO roles (id) VALUES ('EMPLOYEE');
INSERT INTO roles (id) VALUES ('CHIEF');

INSERT INTO employee (is_working_now, full_name, email, role_id) VALUES (TRUE, 'Симонова Мария Кайна', 'Mary@sber.ru', 'EMPLOYEE');
INSERT INTO employee (is_working_now, full_name, email, role_id) VALUES (TRUE, '<Стоматин Петр Петрович>', 'ILoveTwirin@google.com', 'EMPLOYEE');
INSERT INTO employee (is_working_now, full_name, email, role_id) VALUES (TRUE, 'Полежайкина Жанна Дмитриевна', 'jelannay@sber.ru', 'CHIEF');


INSERT INTO department (name, location) VALUES ('Отдел продаж', 'Москва');
INSERT INTO department (name, location) VALUES ('Отдел разработки', 'Новосибирск');
INSERT INTO department (name, location) VALUES ('Финансовый отдел', 'Москва');

-- Создание таблицы должностей
INSERT INTO position (grade, salary, name) VALUES (1, 50000, 'Младший специалист');
INSERT INTO position (grade, salary, name) VALUES (2, 70000, 'Специалист');
INSERT INTO position (grade, salary, name) VALUES (3, 90000, 'Ведущий специалист');
INSERT INTO position (grade, salary, name) VALUES (4, 120000, 'Руководитель отдела');

INSERT INTO position_history (employee_id, start_date, end_date, position_id) VALUES (1, '2022-01-01', NULL, 1);
INSERT INTO position_history (employee_id, start_date, end_date, position_id) VALUES (2, '2022-02-01', NULL, 2);

INSERT INTO project (name, description) VALUES ('Проект A', 'Первый проект компании');
INSERT INTO project (name, description) VALUES ('Проект B', 'Второй проект компании');

INSERT INTO schedule (employee_id, date, start_time, end_time, location) VALUES (1, '2022-01-01', '2022-01-01 09:00:00', '2022-01-01 18:00:00', 'OFFICE');
INSERT INTO schedule (employee_id, date, start_time, end_time, location) VALUES (2, '2022-01-01', '2022-01-01 09:00:00', '2022-01-01 18:00:00', 'OFFICE');

-- Создание таблицы связи сотрудники-отделы
INSERT INTO employees_departments (employee_id, department_id) VALUES (1, 1);
INSERT INTO employees_departments (employee_id, department_id) VALUES (2, 2);

-- Создание таблицы отпусков
INSERT INTO leave (start_date, end_date, employee_id, type, status) VALUES ('2022-01-01', '2022-01-10', 1, 'SICK', 'AGREED');
INSERT INTO leave (start_date, end_date, employee_id, type, status) VALUES ('2022-01-01', '2022-01-10', 2, 'FAMILY_CIRCUMSTANCE', 'AGREED');

-- Создание таблицы связей сотрудник-проект
INSERT INTO employees_projects (employee_id, project_id) VALUES (1, 1);
INSERT INTO employees_projects (employee_id, project_id) VALUES (2, 2);

-- Создание таблицы истории зарплат
INSERT INTO salary_history (employee_id, salary_date, amount, type) VALUES (1, '2022-01-01', 50000, 'SALARY');
INSERT INTO salary_history (employee_id, salary_date, amount, type) VALUES (2, '2022-01-01', 70000, 'SALARY');

-- Создание таблицы задач
INSERT INTO task (name, description, priority, employee_id, deadline, estimate, status, type, project_id) VALUES ('Задача 1', 'Описание первой задачи', 'HIGH', 1, '2022-01-01', 10, 'IN_WORK', 'Разработка', 1);
INSERT INTO task (name, description, priority, employee_id, deadline, estimate, status, type, project_id) VALUES ('Задача 2', 'Описание второй задачи', 'MEDIUM', 2, '2022-01-01', 5, 'IN_WORK', 'Аналитика', 2);