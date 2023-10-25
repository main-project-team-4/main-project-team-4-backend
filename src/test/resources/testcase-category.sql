insert into category_large (id, name) values (1, 'man');
insert into category_large (id, name) values (2, 'woman');

insert into category_mid (id, name, parent) VALUES (1, 'man shirt', 1);
insert into category_mid (id, name, parent) VALUES (2, 'man pants', 1);
insert into category_mid (id, name, parent) VALUES (3, 'woman shirt', 2);
insert into category_mid (id, name, parent) VALUES (4, 'woman pants', 2);

insert into item (name, price, comment, category_mid_id, state) values ('jacket1', 10000, 'nice man shirt1', 1, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jacket2', 5000, 'nice man shirt2', 1, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jacket3', 5000, 'nice woman shirt3', 3, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jacket4', 10000, 'nice woman shirt4', 3, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jean1', 10000, 'nice man pants1', 2, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jean2', 5000, 'nice man pants2', 2, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jean3', 10000, 'nice woman pants3', 4, 0);
insert into item (name, price, comment, category_mid_id, state) values ('jean4', 5000, 'nice woman pants4', 4, 0);
