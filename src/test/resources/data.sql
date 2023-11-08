-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
-- https://www.javainuse.com/onlineBcrypt

INSERT INTO local_user (email, first_name,  last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$F6dog.SC2MXkedpEvqENmOHl9Z3XLq/B.DzhIFALXFSQoCHsPSwve', 'UserA', true),
    ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$VfaXEnWDQEP27YNzwYdb7uAgv1WqBE.Xv0Fgl2ktYqHnqtlVQrq7m', 'UserB', false);
--    ('UserC@junit.com', 'UserC-FirstName', 'UserC-LastName', '$2a$10$W2FbTInH64eo7UcZC0/rYeIFJSZN.cd9GkX.sMVdhoJMjhTvssjvC', 'UserC', false);

--INSERT INTO address(address_line_1, city, country, user_id)
--    VALUES ('123 Tester Hill', 'Testerton', 'England', 1)
--    , ('312 Spring Boot', 'Hibernate', 'England', 3);
--
--INSERT INTO product (name, short_description, long_description, price)
--    VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50)
--    , ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56)
--    , ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74)
--    , ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69)
--    , ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);
--
--INSERT INTO inventory (product_id, quantity)
--    VALUES (1, 5)
--    , (2, 8)
--    , (3, 12)
--    , (4, 73)
--    , (5, 2);
--
--INSERT INTO web_order (address_id, user_id)
--    VALUES (1, 1)
--    , (1, 1)
--    , (1, 1)
--    , (2, 3)
--    , (2, 3);
--
--INSERT INTO web_order_quantities (order_id, product_id, quantity)
--    VALUES (1, 1, 5)
--    , (1, 2, 5)
--    , (2, 3, 5)
--    , (2, 2, 5)
--    , (2, 5, 5)
--    , (3, 3, 5)
--    , (4, 4, 5)
--    , (4, 2, 5)
--    , (5, 3, 5)
--    , (5, 1, 5);
