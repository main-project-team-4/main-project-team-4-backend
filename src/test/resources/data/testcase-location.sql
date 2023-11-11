insert into member (nickname, username, image) values ('hwassell0', 1, 'http://dummyimage.com/216x202.png/dddddd/000000');
insert into member (nickname, username, image) values ('dblasoni1', 2, 'http://dummyimage.com/236x229.png/dddddd/000000');
insert into member (nickname, username, image) values ('nleigh2', 3, 'http://dummyimage.com/176x176.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('fabramow3', 4, 'http://dummyimage.com/208x209.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('dtegler4', 5, 'http://dummyimage.com/166x235.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('qwer322', 6, 'http://dummyimage.com/166x235.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('tyy3u6', 7, 'http://dummyimage.com/166x235.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('hujd944gb', 8, 'http://dummyimage.com/166x235.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('hujd944gb', 9, 'http://dummyimage.com/166x235.png/cc0000/ffffff');

insert into shop (seller_id, intro, name) values (1, 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'jbiggen0');
insert into shop (seller_id, intro, name) values (2, 'Ut at dolor quis odio consequat varius. Integer ac leo.', 'cmaccurlye1');
insert into shop (seller_id, intro, name) values (3, 'Aliquam quis turpis eget elit sodales scelerisque.', 'santonin2');
insert into shop (seller_id, intro, name) values (4, 'Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 'cfrounks3');
insert into shop (seller_id, intro, name) values (5, 'Duis ac nibh.', 'caylett4');
insert into shop (seller_id, intro, name) values (6, 'eget elit sodales.', 'caylett4');
insert into shop (seller_id, intro, name) values (7, 'quis odio consequat.', 'caylett4');
insert into shop (seller_id, intro, name) values (8, 'Aenean auctor gravida sem.', 'caylett4');
insert into shop (seller_id, intro, name) values (9, 'eget elit sodales.', 'jbiggen0');

insert into item (id, name, price, comment, shop_id) values (1, 'jacket1', 10000, 'nice man shirt1', 1);
insert into item (id, name, price, comment, shop_id) values (2, 'jacket2', 5000, 'nice man shirt2', 2);
insert into item (id, name, price, comment, shop_id) values (3, 'jacket3', 5000, 'nice woman shirt3', 3);
insert into item (id, name, price, comment, shop_id) values (4, 'jacket4', 10000, 'nice woman shirt4', 4);
insert into item (id, name, price, comment, shop_id) values (5, 'jean1', 10000, 'nice man pants1', 5);
insert into item (id, name, price, comment, shop_id) values (6, 'jean2', 5000, 'nice man pants2', 6);
insert into item (id, name, price, comment, shop_id) values (7, 'jean3', 10000, 'nice woman pants3', 7);
insert into item (id, name, price, comment, shop_id) values (8, 'jean4', 5000, 'nice woman pants4', 8);

insert into member_location (latitude, longitude, member_id) values (10, 0, 1); -- 100
insert into member_location (latitude, longitude, member_id) values (20, 0, 2); -- 400
insert into member_location (latitude, longitude, member_id) values (30, 0, 3); -- 900
insert into member_location (latitude, longitude, member_id) values (40, 0, 4); -- 1600
insert into member_location (latitude, longitude, member_id) values (10, 10, 5); -- 200
insert into member_location (latitude, longitude, member_id) values (10, 20, 6); -- 500
insert into member_location (latitude, longitude, member_id) values (10, 30, 7); -- 1000
insert into member_location (latitude, longitude, member_id) values (10, 40, 8); -- 1700
insert into member_location (member_id) values (9);