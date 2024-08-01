# create a database ads
create database ads;
use ads;

# create a table with an int id and a file_path variable for each element
create table advertisements (
	id int auto_increment Primary key,
    file_path varchar(255) not null
);

# insert all the file paths into the table 
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

# display everything in the table to ensure the above script worked correctly
Select * from advertisements;