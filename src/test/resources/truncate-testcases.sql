-- 제약 조건 무효화
SET REFERENTIAL_INTEGRITY FALSE;

truncate table wish restart identity;
truncate table follow restart identity;
truncate table trade restart identity;
truncate table review restart identity;
truncate table item_sub_images restart identity;
truncate table item_location restart identity;
truncate table member_location restart identity;
truncate table shop restart identity;
truncate table member restart identity;
truncate table item restart identity;
truncate table category_mid restart identity;
truncate table category_large restart identity;

-- 제약 조건 재설정
SET REFERENTIAL_INTEGRITY TRUE;
