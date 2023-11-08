insert into member (nickname, username, image) values ('hwassell0', 1, 'http://dummyimage.com/216x202.png/dddddd/000000');
insert into member (nickname, username, image) values ('dblasoni1', 2, 'http://dummyimage.com/236x229.png/dddddd/000000');
insert into member (nickname, username, image) values ('nleigh2', 3, 'http://dummyimage.com/176x176.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('fabramow3', 4, 'http://dummyimage.com/208x209.png/cc0000/ffffff');
insert into member (nickname, username, image) values ('dtegler4', 5, 'http://dummyimage.com/166x235.png/cc0000/ffffff');

insert into member_location (member_id, name, latitude, longitude) values (1, '7736 Lighthouse Bay Pass', '531', '487');
insert into member_location (member_id, name, latitude, longitude) values (2, '77647 Chive Pass', '699', '540');
insert into member_location (member_id, name, latitude, longitude) values (3, '5 Shoshone Avenue', '234', '455');
insert into member_location (member_id, name, latitude, longitude) values (4, '6 Garrison Lane', '651', '516');
insert into member_location (member_id, name, latitude, longitude) values (5, '45 Pine View Hill', '503', '447');

insert into shop (seller_id, intro, name) values (1, 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'jbiggen0');
insert into shop (seller_id, intro, name) values (2, 'Ut at dolor quis odio consequat varius. Integer ac leo.', 'cmaccurlye1');
insert into shop (seller_id, intro, name) values (3, 'Aliquam quis turpis eget elit sodales scelerisque.', 'santonin2');
insert into shop (seller_id, intro, name) values (4, 'Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 'cfrounks3');
insert into shop (seller_id, intro, name) values (5, 'Duis ac nibh.', 'caylett4');

insert into category_large (id, name) values (1, 'man');
insert into category_large (id, name) values (2, 'woman');

insert into category_mid (id, name, parent) VALUES (1, 'man shirt', 1);
insert into category_mid (id, name, parent) VALUES (2, 'man pants', 1);
insert into category_mid (id, name, parent) VALUES (3, 'woman shirt', 2);
insert into category_mid (id, name, parent) VALUES (4, 'woman pants', 2);

insert into item (name, price, comment, category_mid_id, shop_id) values ('jacket1', 10000, 'nice man shirt1', 1, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jacket2', 5000, 'nice man shirt2', 1, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jacket3', 5000, 'nice woman shirt3', 3, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jacket4', 10000, 'nice woman shirt4', 3, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jean1', 10000, 'nice man pants1', 2, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jean2', 5000, 'nice man pants2', 2, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jean3', 10000, 'nice woman pants3', 4, 1);
insert into item (name, price, comment, category_mid_id, shop_id) values ('jean4', 5000, 'nice woman pants4', 4, 1);

insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-07-10T11:25:59Z', 'Phasellus sit amet erat.', 4, 4, 1, 1);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-04-20T11:17:54Z', 'Donec vitae nisi.', 3, 1, 2, 2);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-01-05T12:29:30Z', 'Aenean sit amet justo.', 5, 1, 3, 3);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-03-31T10:59:25Z', 'Vivamus tortor.', 4, 8, 4, 4);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-03-07T10:14:58Z', 'In congue.', 1, 1, 5, 5);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-01-19T21:33:02Z', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.', 2, 8, 1, 1);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-02-18T00:32:56Z', 'Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', 5, 5, 2, 2);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-02-05T19:22:56Z', 'Vestibulum ac est lacinia nisi venenatis tristique.', 1, 1, 3, 3);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2022-12-09T03:48:45Z', 'Duis consequat dui nec nisi volutpat eleifend.', 1, 8, 4, 4);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2022-12-23T20:58:55Z', 'Duis consequat dui nec nisi volutpat eleifend.', 5, 6, 5, 5);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-04-17T22:07:02Z', 'Vivamus tortor.', 1, 1, 1, 1);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-04-06T16:54:30Z', 'Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante.', 4, 7, 2, 2);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2022-11-18T08:44:41Z', 'Maecenas ut massa quis augue luctus tincidunt.', 5, 4, 3, 3);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2022-12-30T15:13:21Z', 'Etiam faucibus cursus urna.', 2, 3, 4, 4);
insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-08-21T02:48:26Z', 'Maecenas tincidunt lacus at velit.', 1, 1, 5, 5);