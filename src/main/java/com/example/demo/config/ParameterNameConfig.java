package com.example.demo.config;

public class ParameterNameConfig {
    public static class Message {
        public static final String MESSAGE = "msg";
        public static final String TOkEN = "token";
        public static final String STATUS_CODE = "statusCode";
        public static final String IS_FIRST = "first";
    }

    public static class Member {
        public static final String ID = "member_id";
        public static final String USERNAME = "member_username";
        public static final String NICKNAME = "member_nickname";
        public static final String IMAGE = "member_image";
    }

    public static class Item {
        public static final String ID = "item_id";
        public static final String CREATED_AT = "item_created_at";
        public static final String COMMENT = "item_comment";
        public static final String MAIN_IMAGE = "item_main_image";
        public static final String SUB_IMAGE = "item_sub_image";
        public static final String NAME = "item_name";
        public static final String STATE = "item_state";
        public static final String PRICE = "item_price";
    }

    public static class CategoryLarge {
        public static final String ID = "category_l_id";
        public static final String NAME = "category_l_name";
    }

    public static class CategoryMiddle {
        public static final String ID = "category_m_id";
        public static final String NAME = "category_m_name";
    }

    public static class Shop {
        public static final String ID = "shop_id";
        public static final String NAME = "shop_name";
        public static final String INTRO = "shop_intro";
    }

    public static class Follow {
        public static final String IS_FOLLOWING = "is_following";
        public static final String NUM_FOLLOWERS = "num_followers";
        public static final String NUM_FOLLOWINGS = "num_followings";
    }

    public static class Wish {
        public static final String IS_WISHING = "is_wishing";
    }

    public static class Review {
        public static final String ID = "review_id";
        public static final String CREATED_AT = "review_created_at";
        public static final String COMMENT = "review_comment";
        public static final String RATING = "review_rating";
        public static final String RATING_AVG = "review_rating_avg";
    }

    public static class Location {
        public static final String NAME = "location_name";
        public static final String LATITUDE = "location_latitude";
        public static final String LONGITUDE = "location_longitude";
    }
}
