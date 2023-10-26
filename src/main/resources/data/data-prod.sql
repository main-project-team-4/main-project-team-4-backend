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

insert into member (nickname, username, image) values ('영앤런치', 'imyoungman', 'https://watermark.lovepik.com/photo/20211201/large/lovepik-foreign-cute-baby-picture_501346111.jpg');
insert into member (nickname, username, image) values ('티끌모아티끌', 'tiggleistiny', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Hausstaub_auf_einem_Finger.jpg/220px-Hausstaub_auf_einem_Finger.jpg');
insert into member (nickname, username, image) values ('응애나아기새', 'bowimbabybird', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVctWAqN2hgi5qgz9rQIyIBVjaBB72PeCyKg&usqp=CAU');

insert into member_location (member_id, name, latitude, longitude) values (1, '대구광역시 달서구 월성동', '35.8274237', '128.5268175');
insert into member_location (member_id, name, latitude, longitude) values (2, '경기도 시흥시 은행동', '37.4320048', '126.8089019');
insert into member_location (member_id, name, latitude, longitude) values (3, '경기도 부천시 상동', '37.5012143', '126.7541474');

insert into shop (seller_id, intro, name) values (1, '작년에 쓴 유아용품 팔아용', '영앤런치');
insert into shop (seller_id, intro, name) values (2, '티끌모아 차비 벌려고 만든 상점', '티끌모아티끌');
insert into shop (seller_id, intro, name) values (3, '엄마가 먹이를 안줘서 직접 시장에 뛰어든 아기새 상점입니다.', '응애나아기새');

insert into item (created_at, comment, main_image_url, name, price, state, category_mid_id, shop_id) values ('2023-10-17 12:51:00', '아기용 털 비니 팔아용. 작년에 엄마가 사와서 써봤는데 엄청 따뜻해용.', 'https://ae01.alicdn.com/kf/H7f10862df90a4871b21eda46c3c33acdM.jpg_640x640Q90.jpg_.webp', '유아용(만 1-2세) 털 비니', 20000, 0, 13, 1);
insert into item (created_at, comment, main_image_url, name, price, state, category_mid_id, shop_id) values ('2023-10-19 18:12:22', '짱구는못말려 양말 팔아요. 새 상품이고, 개당 가격입니다. 네고X', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/2419397117/B.jpg?909000000', '짱구 양말', 2000, 0, 15, 2);
insert into item (created_at, comment, main_image_url, name, price, state, category_mid_id, shop_id) values ('2023-10-20 07:33:12', '가죽 자켓 팔아요. 작년에 구입하고 실착용 3번 미만입니다. 상태 S급', 'https://www.number23.co.kr/shopimages/number23/mobile/0/1069200_represent?1599302269', '블랙 가죽자켓', 180000, 0, 6, 3);

insert into item_location (item_id, name, latitude, longitude) values (1, '대구광역시 달서구 월성동', '35.8274237', '128.5268175');
insert into item_location (item_id, name, latitude, longitude) values (2, '경기도 시흥시 은행동', '37.4320048', '126.8089019');
insert into item_location (item_id, name, latitude, longitude) values (3, '경기도 부천시 상동', '37.5012143', '126.7541474');

insert into item_sub_images (item_id, sub_image_url) values (1, 'https://ae01.alicdn.com/kf/He277af26913c42f9bbaf45114712c64e8.jpg_640x640Q90.jpg_.webp');
insert into item_sub_images (item_id, sub_image_url) values (2, 'https://i.namu.wiki/i/fKLm5pOJx_sB2xaDffEIIyP1gxA5SXAAsWQa60DvdziNPM6SEm08K3NXL7F-EVPdF2_h3oHx8R49hSNBcJs7-w.webp');
insert into item_sub_images (item_id, sub_image_url) values (3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwOap9yJnlHQdWusSbcewafITjSsvpuqqJLQ1W-2o&s');

insert into review (created_at, comment, rating, item_id, member_id, shop_id) values ('2023-01-11 18:54:41', '사이즈도 잘 맞고 청결한 상태로 주셔서 좋았습니다.', 1, 2, 1, 1);

insert into trade (item_id, member_id, seller_id) values (2, 1, 2);
insert into trade (item_id, member_id, seller_id) values (1, 2, 1);

insert into follow (member_id, shop_id) values (1, 2);
insert into follow (member_id, shop_id) values (2, 1);

insert into wish (item_id, member_id) values (3, 1);
