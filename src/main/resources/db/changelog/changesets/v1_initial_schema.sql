create type leave_type as enum('VACATION', 'SICK', 'UNPAID', 'FAMILY_CIRCUMSTANCE');
create type leave_status as enum('ON_REVIEW', 'AGREED', 'REJECTED');
create type salary_type as enum('SALARY', 'ADVANCE', 'BONUS', 'LEAVE', 'VOCATION');
create type location_type as enum('OFFICE', 'HOME');
create type auth_roles as enum('CHIEF', 'EMPLOYEE');
create type task_priority as enum('HIGH', 'LOW', 'MEDIUM');
create type task_status as enum('BACKLOG','NOT_STARTED','IN_WORK','DONE','EXPIRED');

create table if not exists roles(
    id  auth_roles primary key
);

create table if not exists department(
         id                  bigserial primary key,
         name                varchar(255) not null unique ,
         location            varchar(255) not null
);

create table if not exists position(
        id                  bigserial primary key,
        grade               smallint not null,
        salary              numeric(38,2) not null,
        name                varchar(255) not null
);

create table if not exists employee(
        id                  bigserial primary key,
        is_working_now      boolean default true,
        full_name           varchar(100) not null,
        email               varchar(255) not null,
        role_id                auth_roles references roles(id)
);

create table if not exists position_history(
        id                  bigserial primary key,
        employee_id         bigint references employee (id),
        start_date          date not null,
        end_date            date,
        position_id         bigint references position (id)
);

create table if not exists project(
        id                  bigserial primary key,
        name                varchar(100) not null unique,
        description         varchar(255) not null
);

create table if not exists schedule(
         id                  bigserial primary key,
         employee_id         bigint references employee (id),
         date                date not null,
         start_time          timestamp not null,
         end_time            timestamp not null,
         location            location_type not null
);

create table if not exists employees_departments(
         employee_id          bigint references employee (id),
         department_id        bigint references department (id),
         constraint employees_departments_constraint primary key (employee_id, department_id)
);

create table if not exists leave(
         id                   bigserial primary key,
         start_date           date not null,
         end_date             date not null,
         employee_id          bigint references employee (id),
         type                 leave_type not null,
         status               leave_status not null
);

create table if not exists employees_projects (
        employee_id          bigint references employee  (id),
        project_id           bigint references project (id),
        constraint employee_project_constraint primary key (employee_id, project_id)
);

create table if not exists salary_history(
        id                   bigserial primary key,
        employee_id          bigint references employee (id),
        salary_date          date not null,
        amount               numeric(38,2) not null ,
        type                 salary_type not null
);

create table if not exists task(
        id                  bigserial primary key,
        name                varchar not null,
        description         varchar(500) not null,
        priority            task_priority,
        employee_id         bigint references employee (id),
        deadline            date not null,
        estimate            numeric,
        status              task_status not null default 'BACKLOG':: task_status,
        type                varchar,
        project_id          bigint references project (id)
);