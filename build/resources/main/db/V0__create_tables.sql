CREATE TABLE IF NOT EXISTS organization(
    org_name VARCHAR(50) NOT NULL,
    INN bigint NOT NULL PRIMARY KEY,
    payment_account INT NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    prod_name VARCHAR(50) NOT NULL,
    inner_code INT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS invoice(
    num INT PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    organization_INN bigint REFERENCES organization(INN) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS invoice_position(
    price INT NOT NULL,
    product_inner_code INT REFERENCES product(inner_code) ON UPDATE CASCADE ON DELETE CASCADE,
    amount INT NOT NULL,
    invoice_num INT REFERENCES invoice(num) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT id PRIMARY KEY (product_inner_code, invoice_num)
);

