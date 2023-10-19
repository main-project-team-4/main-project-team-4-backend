insert into category_large (id, name) values (1, 'man');
insert into category_large (id, name) values (2, 'woman');

insert into category_mid (id, name, parent) VALUES (1, 'man shirt', 1);
insert into category_mid (id, name, parent) VALUES (2, 'man pants', 1);
insert into category_mid (id, name, parent) VALUES (3, 'woman shirt', 2);
insert into category_mid (id, name, parent) VALUES (4, 'woman pants', 2);

insert into item (name, price, comment, category_mid_id, created_at) values ('jacket1', 10000, 'nice man shirt1', 1, '2019-10-18 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jacket2', 5000, 'nice man shirt2', 1, '2019-10-17 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jacket3', 5000, 'nice woman shirt3', 3, '2019-10-13 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jacket4', 10000, 'nice woman shirt4', 3, '2019-10-19 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jean1', 10000, 'nice man pants1', 2, '2019-10-20 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jean2', 5000, 'nice man pants2', 2, '2019-10-21 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jean3', 10000, 'nice woman pants3', 4, '2019-06-18 20:38:19.000000');
insert into item (name, price, comment, category_mid_id, created_at) values ('jean4', 5000, 'nice woman pants4', 4, '2019-10-11 20:38:19.000000');
