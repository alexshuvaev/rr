DROP TABLE IF EXISTS PRICE_ITEM;
DROP TABLE IF EXISTS SUPPLIER;

CREATE TABLE PRICE_ITEM
(
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    vendor        VARCHAR(64)    NOT NULL,
    number        VARCHAR(64)    NOT NULL,
    search_vendor VARCHAR(64)    NOT NULL,
    search_number VARCHAR(64)    NOT NULL,
    description   VARCHAR(512)   NOT NULL,
    price         DECIMAL(18, 2) NOT NULL,
    count         INTEGER        NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX search_vendor_idx ON PRICE_ITEM (search_vendor);
CREATE INDEX search_number_idx ON PRICE_ITEM (search_number);

CREATE TABLE SUPPLIER
(
    id                BIGINT      NOT NULL AUTO_INCREMENT,
    supplier_name     VARCHAR(64) NOT NULL,
    vendor_label      VARCHAR(64) NOT NULL,
    number_label      VARCHAR(64) NOT NULL,
    description_label VARCHAR(64) NOT NULL,
    price_label       VARCHAR(64) NOT NULL,
    count_label       VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);