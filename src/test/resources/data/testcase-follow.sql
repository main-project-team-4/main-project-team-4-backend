insert into member (id, nickname) values (1, 'iksadnorth');
insert into member (id, nickname) values (2, 'iksadsouth');
insert into member (id, nickname) values (3, 'iksadeast');
insert into member (id, nickname) values (4, 'iksadwest');

insert into shop (id, seller_id, intro, star_avg, name) values (1, 1, 'intro', 0, 'name');
insert into shop (id, seller_id, intro, star_avg, name) values (2, 2, 'intro', 0, 'name');
insert into shop (id, seller_id, intro, star_avg, name) values (3, 3, 'intro', 0, 'name');
insert into shop (id, seller_id, intro, star_avg, name) values (4, 4, 'intro', 0, 'name');

insert into follow (member_id, shop_id) values (1, 3);
insert into follow (member_id, shop_id) values (1, 4);
insert into follow (member_id, shop_id) values (2, 3);
insert into follow (member_id, shop_id) values (2, 4);
insert into follow (member_id, shop_id) values (3, 4);