INSERT INTO ad_img (`created_at`, `path`) VALUES (NOW(), 'test1');
INSERT INTO ad_img (`created_at`,`path`) VALUES (NOW(), 'test2');

INSERT INTO USER_ACCOUNT (`created_at`,`email`,`name`,`password`,`phone_num`) VALUES (NOW(),'iksadnorth@gmail.com','박정훈','q1w2e3r4','010-1234-5678');

INSERT INTO SELLER (`created_at`,`email`,`name`,`password`,`phone_num`,`company_name`) VALUES (NOW(),'iksadsouth@gmail.com','김정훈','q1w2e3r4','010-1111-2222','goupang');

INSERT INTO MANAGER (`created_at`,`email`,`name`,`password`,`phone_num`) VALUES (NOW(),'iksadeast@gmail.com','이정훈','q1w2e3r4','010-3333-4444');
