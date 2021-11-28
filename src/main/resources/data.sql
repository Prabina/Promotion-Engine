INSERT INTO PROMOTION (id, name, promo_code, promo_type, discount, no_of_items) VALUES (1000, 'single fixed', 'PROMO4', 'SINGLE_ITEM_FIXED', 20, 2);
INSERT INTO PROMOTION (id, name, promo_code, promo_type, discount, no_of_items) VALUES (1001, 'multi fixed', 'PROMO5', 'MULTIPLE_ITEMS_FIXED', 20, 2);

INSERT INTO PRODUCT (sku, name, price, promotion_id) VALUES ('E', 'maggie', 60, 1000);
INSERT INTO PRODUCT (sku, name, price, promotion_id) VALUES ('F', 'Lays', 40, 1001);
INSERT INTO PRODUCT (sku, name, price, promotion_id) VALUES ('G', 'Coke', 40, 1001);