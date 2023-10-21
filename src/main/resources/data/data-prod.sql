insert into category_large (id, name) values (1, '여성의류');
insert into category_mid (id, name, parent) VALUES (1, '아우터', 1);
insert into category_mid (id, name, parent) VALUES (2, '상의', 1);
insert into category_mid (id, name, parent) VALUES (3, '하의', 1);
insert into category_mid (id, name, parent) VALUES (4, '원피스', 1);
insert into category_mid (id, name, parent) VALUES (5, '신발', 1);

insert into category_large (id, name) values (2, '남성의류');
insert into category_mid (id, name, parent) VALUES (6, '아우터', 2);
insert into category_mid (id, name, parent) VALUES (7, '상의', 2);
insert into category_mid (id, name, parent) VALUES (8, '하의', 2);
insert into category_mid (id, name, parent) VALUES (9, '신발', 2);

insert into category_large (id, name) values (3, '패션잡화');
insert into category_mid (id, name, parent) VALUES (10, '가방', 3);
insert into category_mid (id, name, parent) VALUES (11, '지갑', 3);
insert into category_mid (id, name, parent) VALUES (12, '시계', 3);
insert into category_mid (id, name, parent) VALUES (13, '모자', 3);
insert into category_mid (id, name, parent) VALUES (14, '안경/선글라스', 3);
insert into category_mid (id, name, parent) VALUES (15, '기타', 3);

insert into category_large (id, name) values (4, '쥬얼리');
insert into category_mid (id, name, parent) VALUES (16, '목걸이', 4);
insert into category_mid (id, name, parent) VALUES (17, '반지', 4);
insert into category_mid (id, name, parent) VALUES (18, '귀걸이/피어싱', 4);
insert into category_mid (id, name, parent) VALUES (19, '팔찌', 4);
insert into category_mid (id, name, parent) VALUES (20, '기타', 4);

insert into member (nickname, username, image) values ('hwassell0', 1, 'http://dummyimage.com/216x202.png/dddddd/000000');

insert into member_location (member_id, name, latitude, longitude) values (1, '7736 Lighthouse Bay Pass', '531', '487');

insert into shop (seller_id, intro, name) values (1, 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'jbiggen0');

insert into item (created_at, comment, main_image_url, name, price, state, category_mid_id, shop_id) values ('2022-10-14T15:32:42Z', 'Suspendisse accumsan tortor quis turpis.', 'http://dummyimage.com/109x187.png/ff4444/ffffff', 'Bay Leaf Ground', 14701, 1, 1, 2);

insert into item_location (item_id, name, latitude, longitude) values (1, '732 Dapin Plaza', '937', '137');

insert into item_sub_images (item_id, sub_image_url) values (1, 'http://dummyimage.com/225x132.png/dddddd/000000');

insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-01-11 18:54:41', 'Nullam molestie nibh in lectus. Pellentesque at nulla. Suspendisse potenti.', 1, 174, 1, 1);

insert into trade (item_id, member_id, seller_id) values (2, 1, 2);

insert into trade (item_id, member_id, seller_id) values (12, 12, 1);

insert into follow (member_id, shop_id) values (1, 12);

insert into follow (member_id, shop_id) values (2, 1);

insert into wish (item_id, member_id) values (102, 1);
