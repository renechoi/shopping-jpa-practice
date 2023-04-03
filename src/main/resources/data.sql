INSERT INTO item(item_amount, item_detail, item_name, item_sell_status, price, registered_at, updated_at)
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



INSERT INTO user_account(name, email, address, password, role_type,  created_by, modified_by,created_at, modified_at)
VALUES ("kim", "admin@admin.com", "서울시 강서구", "{noop}1234", "ADMIN", "kim", "kim", now(),now()),
("lee", "user@user.com", "서울시 강남구", "{noop}1234", "USER", "lee", "lee", now(),now()),
("yoo", "yoo@user.com", "서울시 금천구 가산동", "{noop}1234", "SELLER", "yoo", "yoo", now(),now()),
("jin", "jin@user.com", "서울시 서초구", "{noop}1234", "USER", "jin", "jin", now(),now()),
("park", "park@user.com", "서울시 종로구", "{noop}1234", "SELLER", "park", "park", now(),now());

