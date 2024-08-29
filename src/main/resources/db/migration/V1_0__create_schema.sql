CREATE SCHEMA IF NOT EXISTS farm_mvc;

insert into farm_mvc.roles(name)
values('ADMIN'),
      ('USER');

-- После добавления юзера админа
update farm_mvc.users_roles
set role_id = 1
where user_id = 1;