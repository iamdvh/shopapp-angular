create database shopapp;
use shopapp;

create table users (
	id int primary key auto_increment,
    fullname varchar(100) default "",
    phone_number varchar(10) not null,
    address varchar(200) default '',
    password varchar(100) not null default '',
    created_at datetime,
    updated_at datetime,
    is_active tinyint(1) default 1,
    date_of_birth date,
    facebook_account_id int default 0,
    google_account_id int default 0
);
alter table users add column role_id int;

create table roles (
	id int primary key auto_increment,
    name varchar(20)
);
alter table users add constraint fk_user_role foreign key(role_id) references roles(id);
create table tokens (
		id int primary key auto_increment,
        token varchar(255) unique not null,
        token_type varchar(50) not null,
        expiration_date datetime,
        revoked tinyint(1) not null,
        expired tinyint(1) not null,
        user_id int
);

alter table tokens add constraint fk_token_user foreign key(user_id) references users(id);

-- login faceboook/google
create table social_accounts (
	id int primary key auto_increment,
	token_type varchar(50) not null comment 'Tên nhà social network',
	provider_id varchar(50) not null,
	email varchar(150) not null comment 'email tài khoản',
	name varchar(100) not null comment 'Tên người dùng',
	user_id int
);
alter table social_accounts add constraint fk_social_account_user foreign key(user_id) references users(id);

create table categories (
	id int primary key auto_increment,
	name varchar(100) not null comment 'Tên danh mục sản phẩm'
);

create table products (
	id int primary key auto_increment,
	name varchar(350) comment 'Tên sản phẩm',
    price float not null check(price >=0),
    thumbnail varchar(300) default '',
    description longtext default '',
	created_at datetime,
    updated_at datetime,
    category_id int
);

create table product_images (
	id int primary key auto_increment,
	product_id int
);

alter table product_images add column image_url varchar(300);
alter table products add constraint fk_product_category foreign key(category_id) references categories(id);
alter table product_images add constraint fk_product_image_product foreign key(product_id) references products(id) on delete cascade;


create table orders (
	id int primary key auto_increment,
	user_id int,
    fullname varchar(100)  default '',
    email varchar(100)  default '',
    phone_number varchar(20) not null,
    address varchar(200) not null,
    note varchar(100) default '',
    order_date datetime default current_timestamp,
    status 	enum ('pending', 'processing', 'shipped', 'delivered', 'cancelled') comment 'trạng thái đơn hàng',
    total_money float check (total_money >= 0),
    shipping_method varchar(100),
    shipping_address varchar(200),
    tracking_number varchar(100),
    payment_method varchar(100),
    active tinyint(1),
    shipping_date date
);
alter table orders add constraint fk_orders_user foreign key(user_id) references users(id);
create table order_details (
	id int primary key auto_increment,
	product_id int,
    price float check (price >= 0),
    number_of_product int check (number_of_product > 0),
    total_money float check (total_money >= 0),
    color varchar(20) default ''
);
alter table order_details add constraint fk_order_detail_product foreign key(product_id) references products(id);
alter table order_details add column order_id int;
alter table order_details add constraint fk_order_detail_order foreign key(order_id) references orders(id);

