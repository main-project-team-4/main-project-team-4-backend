insert into item (id, name, price, comment) values (1, 'jacket1', 10000, 'nice man shirt1');
insert into item (id, name, price, comment) values (2, 'jacket2', 5000, 'nice man shirt2');
insert into item (id, name, price, comment) values (3, 'jacket3', 5000, 'nice woman shirt3');
insert into item (id, name, price, comment) values (4, 'jacket4', 10000, 'nice woman shirt4');
insert into item (id, name, price, comment) values (5, 'jean1', 10000, 'nice man pants1');
insert into item (id, name, price, comment) values (6, 'jean2', 5000, 'nice man pants2');
insert into item (id, name, price, comment) values (7, 'jean3', 10000, 'nice woman pants3');
insert into item (id, name, price, comment) values (8, 'jean4', 5000, 'nice woman pants4');

insert into item_location (latitude, longitude, item_id) values (10, 0, 1); -- 100
insert into item_location (latitude, longitude, item_id) values (20, 0, 2); -- 400
insert into item_location (latitude, longitude, item_id) values (30, 0, 3); -- 900
insert into item_location (latitude, longitude, item_id) values (40, 0, 4); -- 1600
insert into item_location (latitude, longitude, item_id) values (10, 10, 5); -- 200
insert into item_location (latitude, longitude, item_id) values (10, 20, 6); -- 500
insert into item_location (latitude, longitude, item_id) values (10, 30, 7); -- 1000
insert into item_location (latitude, longitude, item_id) values (10, 40, 8); -- 1700