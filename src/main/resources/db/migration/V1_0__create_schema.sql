CREATE SCHEMA IF NOT EXISTS farm_mvc;

insert into farm_mvc.roles(name)
values('ADMIN'),
      ('USER');

-- После добавления юзера админа
update farm_mvc.users_roles
set role_id = 1
where user_id = 1;

insert into farm_mvc.regions(latitude, longitude, name)
values(45.111, 78.000, 'Алматинская область');

insert into farm_mvc.posts(address, content, created_on, photo_url, title, updated_on, created_by, region_id)
VALUES ('TEST', 'Test', CURRENT_TIMESTAMP, 'https://storage.googleapis.com/mol-farm-images/test_farm.jpg', 'Tester', CURRENT_TIMESTAMP, 10, 1);