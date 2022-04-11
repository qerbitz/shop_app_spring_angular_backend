create table address (id bigint not null auto_increment, city varchar(255), country varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), primary key (id)) engine=InnoDB
create table category (id_category integer not null auto_increment, name varchar(255), primary key (id_category)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table order_item (id bigint not null auto_increment, quantity integer, order_id bigint, id_product integer, primary key (id)) engine=InnoDB
create table orders (id bigint not null auto_increment, order_date datetime(6), order_tracking_number varchar(255), status varchar(255), total_price decimal(19,2), total_quantity integer, primary key (id)) engine=InnoDB
create table product (id_product integer not null auto_increment, discount double precision, gender varchar(255), image varchar(255), name varchar(255), price double precision, season varchar(255), id_category integer not null, primary key (id_product)) engine=InnoDB
create table product_size (id_product_size integer not null auto_increment, quantity integer, sizer varchar(255), id_product integer not null, primary key (id_product_size)) engine=InnoDB
create table user (id bigint not null, authorities tinyblob, email varchar(255), first_name varchar(255), is_active bit not null, is_not_locked bit not null, join_date datetime(6), last_login_date datetime(6), last_login_date_display datetime(6), last_name varchar(255), password varchar(255), role varchar(255), user_id varchar(255), username varchar(255), primary key (id)) engine=InnoDB
alter table order_item add constraint FKt4dc2r9nbvbujrljv3e23iibt foreign key (order_id) references orders (id)
alter table order_item add constraint FKhpraxq28c9p8aljm2jebpun9s foreign key (id_product) references product (id_product)
alter table product add constraint FK5cxv31vuhc7v32omftlxa8k3c foreign key (id_category) references category (id_category)
alter table product_size add constraint FKqjb7hj89q67yvkwgsl3ia285e foreign key (id_product) references product (id_product)
create table address (id bigint not null auto_increment, city varchar(255), country varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), primary key (id)) engine=InnoDB
create table category (id_category integer not null auto_increment, name varchar(255), primary key (id_category)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table order_item (id bigint not null auto_increment, quantity integer, order_id bigint, id_product integer, primary key (id)) engine=InnoDB
create table orders (id bigint not null auto_increment, order_date datetime(6), order_tracking_number varchar(255), status varchar(255), total_price decimal(19,2), total_quantity integer, primary key (id)) engine=InnoDB
create table product (id_product integer not null auto_increment, discount double precision, gender varchar(255), image varchar(255), name varchar(255), price double precision, season varchar(255), id_category integer not null, primary key (id_product)) engine=InnoDB
create table product_size (id_product_size integer not null auto_increment, quantity integer, sizer varchar(255), id_product integer not null, primary key (id_product_size)) engine=InnoDB
create table user (id bigint not null, authorities tinyblob, email varchar(255), first_name varchar(255), is_active bit not null, is_not_locked bit not null, join_date datetime(6), last_login_date datetime(6), last_login_date_display datetime(6), last_name varchar(255), password varchar(255), role varchar(255), user_id varchar(255), username varchar(255), primary key (id)) engine=InnoDB
alter table order_item add constraint FKt4dc2r9nbvbujrljv3e23iibt foreign key (order_id) references orders (id)
alter table order_item add constraint FKhpraxq28c9p8aljm2jebpun9s foreign key (id_product) references product (id_product)
alter table product add constraint FK5cxv31vuhc7v32omftlxa8k3c foreign key (id_category) references category (id_category)
alter table product_size add constraint FKqjb7hj89q67yvkwgsl3ia285e foreign key (id_product) references product (id_product)
