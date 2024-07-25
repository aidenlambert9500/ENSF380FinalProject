create database ads;
use ads;

create table advertisements (
	id int auto_increment Primary key,
    file_path varchar(255) not null,
	title varchar(255) not null
);

Insert into advertisements (file_path , title) 
values ('/path/to/testfile.png', 'Test Advertisement');

Insert into advertisements (file_path , title) 
values ('/path/to/2ndtestfile.pdf', 'Test PDF');


Select * from advertisements;