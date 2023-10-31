insert into member (nickname, username, image) values ('hwassell0', 1, 'http://dummyimage.com/216x202.png/dddddd/000000');
insert into member_location (member_id, name, latitude, longitude) values (1, '7736 Lighthouse Bay Pass', '531', '487');
insert into shop (seller_id, intro, name) values (1, 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'jbiggen0');

insert into category_large (id, name) values (1, 'man');
insert into category_mid (id, name, parent) VALUES (1, 'man shirt', 1);

insert into item (name, price, comment, category_mid_id, created_at, shop_id, state, main_image_url) values ('jacket1', 10000, 'nice man shirt1', 1, '2019-10-18 20:38:19.000000', 1, 0, 'http://dummyimage.com/121x111.png/dddddd/000000');