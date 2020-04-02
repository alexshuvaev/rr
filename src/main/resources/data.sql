DELETE
FROM PRICE_ITEM;
DELETE
FROM SUPPLIER;

INSERT INTO SUPPLIER (id, supplier_name, vendor_label, number_label, description_label, price_label, count_label)
VALUES (1, 'ООО "Доставим в срок"', 'Бренд', 'Каталожный номер', 'Описание', 'Цена, руб.', 'Наличие');