create database ads;
use ads;

create table advertisements (
	id int auto_increment Primary key,
    file_path varchar(255) not null
);

Insert into advertisements (file_path) 
values ('data/advertisements/LeBron-Tide-Ad.png');

Insert into advertisements (file_path) 
values ('data/advertisements/Barbie-Ad.jpg');

Insert into advertisements (file_path) 
values ('data/advertisements/Cheezit-Ad.png');

Insert into advertisements (file_path) 
values ('data/advertisements/CocaCola-Ad.jpg');

Insert into advertisements (file_path) 
values ('data/advertisements/Ford-Truck-Ad.jpg');

Insert into advertisements (file_path) 
values ('data/advertisements/McDonalds-Ad.png');

Insert into advertisements (file_path) 
values ('data/advertisements/Nike-Ad.png');

Insert into advertisements (file_path) 
values ('data/advertisements/Nissan-Ad.jpg');

Select * from advertisements;