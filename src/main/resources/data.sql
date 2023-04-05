INSERT INTO item(stock_amount, item_detail, item_name, item_sell_status, price, registered_at, updated_at)
VALUES (1000, '상품 상세1', '상품1', 'SELL', 10000, NOW(), NOW()),
       (500, '상품 상세3', '상품2', 'SELL', 30000, NOW(), NOW()),
       (300, '상품 상세5', '상품3', 'SELL', 50000, NOW(), NOW()),
       (2000, '상품 상세7', '상품4', 'SELL', 20000, NOW(), NOW()),
       (800, '상품 상세10', '상품5', 'SELL', 40000, NOW(), NOW()),
       (100, '상품 상세15', '상품6', 'SELL', 60000, NOW(), NOW()),
       (2000, '상품 상세10', '상품7', 'SELL', 15000, NOW(), NOW()),
       (300, '상품 상세2', '상품8', 'SELL', 12000, NOW(), NOW()),
       (300, '상품 상세', '상품9', 'SELL', 21000, NOW(), NOW()),
       (300, '상품 상세', '상품10', 'SELL', 150000, NOW(), NOW());



INSERT INTO user_account(name, email, address, password, role_type, created_by, modified_by, created_at, modified_at)
VALUES ('kim', 'admin@admin.com', '서울시 강서구', '{noop}1234', 'ADMIN', 'kim', 'kim', NOW(), NOW()),
       ('lee', 'user@user.com', '서울시 강남구', '{noop}1234', 'USER', 'lee', 'lee', NOW(), NOW()),
       ('yoo', 'yoo@user.com', '서울시 금천구 가산동', '{noop}1234', 'SELLER', 'yoo', 'yoo', NOW(), NOW()),
       ('jin', 'jin@user.com', '서울시 서초구', '{noop}1234', 'USER', 'jin', 'jin', NOW(), NOW()),
       ('park', 'park@user.com', '서울시 종로구', '{noop}1234', 'SELLER', 'park', 'park', NOW(), NOW());

INSERT INTO cart(user_account_id)
VALUES (3),
       (2),
       (1);


INSERT INTO item(created_at, created_by, modified_at, modified_by, item_detail, item_name, item_sell_status, price,
                 registered_at, stock_amount, updated_at)
VALUES
    (NOW(), 'kim', NOW(), 'kim', 'detail', 'test item', 'SELL', 12345, NOW(), 1000, NOW()),
    (NOW(), 'lee', NOW(), 'lee', 'detail2', 'test item2', 'SELL', 333, NOW(), 333, NOW()),
    (NOW(), 'part', NOW(), 'park', 'detail3', 'test item3', 'SOLD_OUT', 444, NOW(), 444, NOW());

INSERT INTO item_image(created_at, created_by, modified_at, modified_by, image_name, image_url, original_image_name, representative_image_yn, item_id) VALUES
                                                                                                                                                           (now(),'kim',now(),'kim','0ca18bae9e22771f0396e0df78521d93.jpeg','/images/item/884614f6-ffdf-4320-b142-46c6ef8cd966.jpeg','884614f6-ffdf-4320-b142-46c6ef8cd966.jpeg','Y',11),
                                                                                                                                                           (now(),'lee',now(),'lee','1b5df61b9033c78572d3daa71265e266.jpeg','/images/item/d5ceeeef-0893-43da-9b09-805db08688eb.jpeg','d5ceeeef-0893-43da-9b09-805db08688eb.jpeg','N',11),
                                                                                                                                                           (now(),'park',now(),'park','Jeans_for_men.jpeg','/images/item/c532b8ed-5756-49c3-aa35-d223a53099e6.jpeg','c532b8ed-5756-49c3-aa35-d223a53099e6.jpeg','N',11);