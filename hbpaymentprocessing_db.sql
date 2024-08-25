use master 
go 

create database HBPaymentProcessing
go 

use HBPaymentProcessing 
go 

create table account(
	id bigint identity(1,1) primary key not null,
	address nvarchar(250) null,
	created_date datetime not null,
	deleted_date datetime not null,
	email varchar(250) not null,
	full_name nvarchar(250) not null,
	id_card varchar(12) not null,
	modified_date datetime not null,
	phone_number varchar(250) null,
	pwd varchar(250) not null,
	account_status_id bigint null,
	role_id bigint null,
)
go
/****** Object: table account_number_code Script Date: 2024-08-25 17:48:00 ******/

create table account_number_code(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	number_code varchar(12) not null,
	account_id bigint null,
	account_number_code_status_id int null,
)
go
/****** Object: table account_number_code_status Script Date: 2024-08-25 17:48:00 ******/

create table account_number_code_status(
	id int identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	name varchar(15) not null,
)
go
/****** Object: table account_status Script Date: 2024-08-25 17:48:00 ******/

create table account_status(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	name varchar(15) not null,
)
go
/****** Object: table account_transactionn Script Date: 2024-08-25 17:48:00 ******/

create table account_transactionn(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	description varchar(250) not null,
	invoice_code varchar(20) not null,
	modified_date datetime not null,
	payment_date datetime not null,
	receiver_account_number_code varchar(12) not null,
	sender_account_number_code varchar(12) not null,
	total float not null,
	transaction_code varchar(21) not null,
	account_id bigint null,
	transaction_status_id bigint null,
	transaction_tag_id bigint null,
)
go
/****** Object: table account_transactionn_status Script Date: 2024-08-25 17:48:00 ******/

create table account_transactionn_status(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	name varchar(15) not null,
)
go
/****** Object: table account_transactionn_tag Script Date: 2024-08-25 17:48:00 ******/

create table account_transactionn_tag(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	name varchar(13) not null,
)
go
/****** Object: table role Script Date: 2024-08-25 17:48:00 ******/

create table role(
	id bigint identity(1,1) primary key not null,
	created_date datetime not null,
	deleted_date datetime not null,
	modified_date datetime not null,
	name varchar(20) not null,
)
go

alter table account add constraint FK2i9knjg6sx8h6tqh1vmul1dgy foreign key(account_status_id)
references account_status (id)
go

alter table account add constraint FKd4vb66o896tay3yy52oqxr9w0 foreign key(role_id)
references role (id)
go

alter table account_number_code add constraint FK117j9u7imqyt3vi2exolypqe3 foreign key(account_id)
references account (id)
go

alter table account_number_code add constraint FK9c0e679eqhtb2ss4u0f17l599 foreign key(account_number_code_status_id)
references account_number_code_status (id)
go

alter table account_transactionn add constraint FKb33ttpsfwfutptg8gd5qtdw86 foreign key(transaction_tag_id)
references account_transactionn_tag (id)
go

alter table account_transactionn add constraint FKepdd1pyi5jvn154x4uaw5ak02 foreign key(transaction_status_id)
references account_transactionn_status (id)
go

alter table account_transactionn add constraint FKepkpmr1g53ert5vnb283ssf0k foreign key(account_id)
references account (id)
go


/**/

insert account_number_code_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-28T00:00:01.000' AS DateTime), CAST(N'2023-02-28T00:00:01.000' AS DateTime), CAST(N'2023-02-28T00:00:01.000' AS DateTime), N'Active')
 insert account_number_code_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-28T00:00:02.000' AS DateTime), CAST(N'2023-02-28T00:00:02.000' AS DateTime), CAST(N'2023-02-28T00:00:02.000' AS DateTime), N'Inactive')


 insert account_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-27T00:00:00.000' AS DateTime), CAST(N'2023-02-27T00:00:00.000' AS DateTime), CAST(N'2023-02-27T00:00:00.000' AS DateTime), N'Active')
 insert account_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-27T00:00:01.000' AS DateTime), CAST(N'2023-02-27T00:00:01.000' AS DateTime), CAST(N'2023-02-27T00:00:01.000' AS DateTime), N'Inactive')

 insert role (created_date, deleted_date, modified_date, name) values (CAST(N'2023-01-25T00:00:00.000' AS DateTime), CAST(N'2023-01-25T00:00:00.000' AS DateTime), CAST(N'2023-01-25T00:00:00.000' AS DateTime), N'Personal')
 insert role (created_date, deleted_date, modified_date, name) values (CAST(N'2023-01-26T00:00:00.000' AS DateTime), CAST(N'2023-01-26T00:00:00.000' AS DateTime), CAST(N'2023-01-26T00:00:00.000' AS DateTime), N'Enterprise')

 insert account_transactionn_tag (created_date, deleted_date, modified_date, name) values (CAST(N'2023-01-26T00:00:00.000' AS DateTime), CAST(N'2023-01-26T00:00:00.000' AS DateTime), CAST(N'2023-01-26T00:00:00.000' AS DateTime), N'Shopping')
 insert account_transactionn_tag (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), N'Entertainment')
 insert account_transactionn_tag (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-27T00:00:00.000' AS DateTime), CAST(N'2023-02-27T00:00:00.000' AS DateTime), CAST(N'2023-02-27T00:00:00.000' AS DateTime), N'Hotel')
 insert account_transactionn_tag (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-28T00:00:00.000' AS DateTime), CAST(N'2023-02-28T00:00:00.000' AS DateTime), CAST(N'2023-02-28T00:00:00.000' AS DateTime), N'Bills')

 insert account_transactionn_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), N'Paid')
 insert account_transactionn_status (created_date, deleted_date, modified_date, name) values (CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), CAST(N'2023-02-26T00:00:00.000' AS DateTime), N'Unpaid')

 insert account (address, created_date, deleted_date, email, full_name, id_card, modified_date, phone_number, pwd, account_status_id, role_id) values (N'', CAST(N'2024-08-25T07:24:33.087' AS DateTime), CAST(N'2024-08-25T07:24:33.087' AS DateTime), N'ducminh1@gmail.com', N'Minh Đức', N'123655478985', CAST(N'2024-08-25T07:24:33.087' AS DateTime), N'', N'$2a$10$zzbi7D2WwWIu.WeLyi6XyuVHjB9A9wCEJ71kOfcwr0aMMTkBsId06', 1, 1)
 insert account (address, created_date, deleted_date, email, full_name, id_card, modified_date, phone_number, pwd, account_status_id, role_id) values (N'', CAST(N'2024-08-25T07:27:52.660' AS DateTime), CAST(N'2024-08-25T07:27:52.660' AS DateTime), N'ducminh@gmail.com', N'Minh Đức', N'123655478985', CAST(N'2024-08-25T07:27:52.660' AS DateTime), N'', N'$2a$10$Mj.1AdariAXn0s7O1PWEu.zDDcYX96TO6TukeCThM/Hk/L4/PFcEO', 1, 1)
 insert account (address, created_date, deleted_date, email, full_name, id_card, modified_date, phone_number, pwd, account_status_id, role_id) values (N'', CAST(N'2024-08-25T09:42:17.587' AS DateTime), CAST(N'2024-08-25T09:42:17.587' AS DateTime), N'ducminh2@gmail.com', N'Minh Đức', N'123655478985', CAST(N'2024-08-25T09:42:17.587' AS DateTime), N'', N'$2a$10$h27tZxgpJAkSu69SpOOQSeWbVSZ.Iy.dw69.toth57KwjkVxHDS.K', 1, 1)


 insert account_number_code (created_date, deleted_date, modified_date, number_code, account_id, account_number_code_status_id) values (CAST(N'2024-08-25T07:24:33.087' AS DateTime), CAST(N'2024-08-25T07:24:33.087' AS DateTime), CAST(N'2024-08-25T07:24:33.087' AS DateTime), N'INVABC123457', 1, 1)
 insert account_number_code (created_date, deleted_date, modified_date, number_code, account_id, account_number_code_status_id) values (CAST(N'2024-08-25T07:24:34.087' AS DateTime), CAST(N'2024-08-25T07:24:34.087' AS DateTime), CAST(N'2024-08-25T07:24:34.087' AS DateTime), N'INVABC123458', 1, 1)
 insert account_number_code (created_date, deleted_date, modified_date, number_code, account_id, account_number_code_status_id) values (CAST(N'2024-08-25T07:24:35.087' AS DateTime), CAST(N'2024-08-25T07:24:35.087' AS DateTime), CAST(N'2024-08-25T07:24:35.087' AS DateTime), N'INVABC123459', 2, 1)
 insert account_number_code (created_date, deleted_date, modified_date, number_code, account_id, account_number_code_status_id) values (CAST(N'2024-08-25T07:24:36.087' AS DateTime), CAST(N'2024-08-25T07:24:35.087' AS DateTime), CAST(N'2024-08-25T07:24:35.087' AS DateTime), N'INVABC123460', 2, 1)
