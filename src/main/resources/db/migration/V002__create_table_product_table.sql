create table product_table(
                              id serial primary key ,
                              product_name varchar(100),
                              description varchar(100),
                              price int,
                              category_id int,
                              foreign key (category_id) references categories(id)
);