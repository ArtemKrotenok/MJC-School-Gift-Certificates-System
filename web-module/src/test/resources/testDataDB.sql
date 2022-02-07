-- inset test data

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('race', 'description_1', 100, 5, '2021-07-22 15:55:13.241000 +00:00', '2021-12-22 15:55:17.574000 +00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('cinema', 'description_2', 200, 220, '2021-08-22 15:55:13.241000 +00:00', '2021-12-22 15:55:17.574000 +00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('beer club', 'description_3', 10, 120, '2021-09-22 15:55:13.241000 +00:00',
        '2021-12-22 15:55:17.574000 +00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('test', 'description_4', 30, 30, '2021-10-21 15:55:13.241000 +00:00', '2021-12-22 15:55:17.574000 +00:00');

INSERT INTO tag (name)
VALUES ('love');
INSERT INTO tag (name)
VALUES ('car');
INSERT INTO tag (name)
VALUES ('sport');
INSERT INTO tag (name)
VALUES ('quiz');
INSERT INTO tag (name)
VALUES ('quest');
INSERT INTO tag (name)
VALUES ('action');
INSERT INTO tag (name)
VALUES ('all');
INSERT INTO tag (name)
VALUES ('test');

INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (1, 2);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (1, 3);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (1, 6);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (1, 7);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (2, 1);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (2, 7);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (3, 4);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (3, 5);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (3, 6);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (3, 7);
INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag)
VALUES (4, 7);