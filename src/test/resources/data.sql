-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
-- https://www.javainuse.com/onlineBcrypt

INSERT INTO local_user (email, first_name,  last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$F6dog.SC2MXkedpEvqENmOHl9Z3XLq/B.DzhIFALXFSQoCHsPSwve', 'UserA', true),
    ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$VfaXEnWDQEP27YNzwYdb7uAgv1WqBE.Xv0Fgl2ktYqHnqtlVQrq7m', 'UserB', false);
