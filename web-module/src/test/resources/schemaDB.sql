-- create schema database for gift certificate system;

DROP TABLE IF EXISTS gift_certificate_tag, tag, gift_certificate;

CREATE TABLE gift_certificate
(
    id               bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name             text,
    description      text,
    price            numeric,
    duration         bigint,
    create_date      timestamp with time zone,
    last_update_date timestamp with time zone,
    CONSTRAINT gift_certificate_pkey PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id   bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
);

CREATE TABLE gift_certificate_tag
(
    id_gift_certificate bigint NOT NULL,
    id_tag              bigint NOT NULL,
    CONSTRAINT foreign_key_gift_certificate FOREIGN KEY (id_gift_certificate)
        REFERENCES gift_certificate (id),
    CONSTRAINT foreign_key_tag FOREIGN KEY (id_tag)
        REFERENCES tag (id)
);