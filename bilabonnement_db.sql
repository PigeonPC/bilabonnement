DROP DATABASE bilabonnement_db;

CREATE DATABASE IF NOT EXISTS bilabonnement_db;
USE bilabonnement_db;

CREATE TABLE IF NOT EXISTS cars
(
    vehicle_id       INT PRIMARY KEY AUTO_INCREMENT,
    chassis_number   VARCHAR(50),
    brand            VARCHAR(50),
    model            VARCHAR(50),
    equipment_level  ENUM ('Base', 'Comfort', 'Sport', 'Premium', 'Advanced'),
    steel_price      DECIMAL(10, 2),
    registration_tax DECIMAL(10, 2),
    co2_emission     INT,
    mileage          INT,
    leasing_code     VARCHAR(30),
    irk_code         VARCHAR(30),
    date_of_purchase DATE,
    purchase_price   DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id    INT PRIMARY KEY AUTO_INCREMENT,
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    phone          VARCHAR(20),
    license_number VARCHAR(11),
    email          VARCHAR(200),
    address        VARCHAR(200),
    zip            INT,
    floor          VARCHAR(5),
    country        VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS renters
(
    renter_id    INT PRIMARY KEY AUTO_INCREMENT,
    credit_score ENUM ('APPROVED','PENDING', 'REJECTED'),
    ssn          VARCHAR(11),
    customer_id  INT,
    CONSTRAINT fk_renter_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE TABLE IF NOT EXISTS lease_contracts
(
    leasing_contract_id    INT PRIMARY KEY AUTO_INCREMENT,
    leasing_contract_terms TEXT,
    lease_contract_date    DATE,
    start_date             DATE,
    end_date               DATE,
    rental_price           DECIMAL(10, 2),
    subscription           ENUM ('Limited', 'Unlimited'),
    approved_date          DATETIME,
    deposit_payed_date     DATETIME,
    full_amount_payed_date DATETIME,

    renter_id              INT,
    vehicle_id             INT,

    CONSTRAINT fk_lease_renter FOREIGN KEY (renter_id) REFERENCES renters (renter_id),
    CONSTRAINT fk_lease_car FOREIGN KEY (vehicle_id) REFERENCES cars (vehicle_id)

);

CREATE TABLE IF NOT EXISTS damage_reports
(
    damage_report_id    INT PRIMARY KEY AUTO_INCREMENT,
    total_price         DECIMAL(10, 2),
    total_damage_price  DECIMAL(10, 2),
    report_date         DATE,
    late_return         BOOLEAN,
    total_km            INT,
    has_payed           DATETIME,

    leasing_contract_id INT,

    CONSTRAINT fk_damage_report_lease FOREIGN KEY (leasing_contract_id) REFERENCES lease_contracts (leasing_contract_id)

);

CREATE TABLE IF NOT EXISTS damage_items
(
    damage_item_id    INT PRIMARY KEY AUTO_INCREMENT,
    description       VARCHAR(300),
    damage_item_price DECIMAL(7, 2),

    damage_report_id  INT,

    CONSTRAINT fk_damage_item_report FOREIGN KEY (damage_report_id) REFERENCES damage_reports (damage_report_id)
);

CREATE TABLE IF NOT EXISTS final_sales
(
    final_sale_id    INT PRIMARY KEY AUTO_INCREMENT,
    first_name       VARCHAR(50),
    last_name        VARCHAR(50),
    phone            VARCHAR(20),
    email            VARCHAR(200),
    address          VARCHAR(200),
    zip              INT,
    floor            VARCHAR(5),
    country          VARCHAR(30),
    sale_date        DATE,
    buyer_type       ENUM ('PREBUYER', 'WHOLESALER', 'AUCTION', 'BCA', 'PRIVATE', 'EXPORT'),
    currency         ENUM ('EUR', 'DKK'),
    note             VARCHAR(200),
    final_sale_price DECIMAL(10, 2),

    vehicle_id       INT,

    CONSTRAINT fk_final_sale_car FOREIGN KEY (vehicle_id) REFERENCES cars (vehicle_id)
);

CREATE TABLE IF NOT EXISTS pre_sale_agreements
(
    pre_sale_id              INT PRIMARY KEY AUTO_INCREMENT,
    limited_period           BOOLEAN,
    pre_sale_agreement_date  DATE,
    pickup_location          VARCHAR(100),
    km_limit                 INT,
    extra_km_price           DECIMAL(5, 2),
    pre_sale_agreement_terms TEXT,
    currency                 ENUM ('EUR', 'DKK'),
    date_of_purchase         DATE,

    vehicle_id               INT,
    pre_buyer_id             INT,

    CONSTRAINT fk_pre_sale_car FOREIGN KEY (vehicle_id) REFERENCES cars (vehicle_id),
    CONSTRAINT fk_pre_sale_customer FOREIGN KEY (pre_buyer_id) REFERENCES customers (customer_id)

);

CREATE TABLE IF NOT EXISTS status_histories
(
    status_history_id INT PRIMARY KEY AUTO_INCREMENT,
    status            ENUM ('PURCHASED', 'PREPARATION_FOR_RENT', 'READY_FOR_RENT', 'RENTED', 'RETURNED', 'PREPARATION_FOR_SALE', 'READY_FOR_SALE', 'SOLD'),
    timestamp         DATETIME,

    vehicle_id        INT,

    CONSTRAINT fk_status_car FOREIGN KEY (vehicle_id) REFERENCES cars (vehicle_id)

);


/* ---------------- CAR  ---------------- */

INSERT INTO cars (chassis_number, brand, model, equipment_level,
                  steel_price, registration_tax, co2_emission, mileage,
                  leasing_code, irk_code, date_of_purchase, purchase_price)

VALUES ('CAR001', 'Audi', 'A3', 'Base', 155000, 72000, 115, 23000, 'LC-A3-25', 'IRK-A1', '2025-01-10', 225000),
       ('CAR002', 'BMW', '320i', 'Comfort', 225000, 112000, 128, 17000, 'LC-320-25', 'IRK-B1', '2025-01-14', 305000),
       ('CAR003', 'Mercedes', 'C200', 'Sport', 265000, 132000, 142, 13500, 'LC-C2-25', 'IRK-C1', '2025-02-01', 365000),
       ('CAR004', 'Toyota', 'Yaris', 'Premium', 135000, 52000, 95, 52000, 'LC-YA-24', 'IRK-D1', '2024-11-01', 185000),
       ('CAR005', 'Volvo', 'XC60', 'Advanced', 355000, 182000, 158, 11000, 'LC-XC-25', 'IRK-E1', '2025-02-10', 525000),
       ('CAR006', 'Skoda', 'Octavia', 'Comfort', 195000, 82000, 118, 32000, 'LC-OCT-24', 'IRK-F1', '2024-12-11',
        275000),
       ('CAR007', 'Tesla', 'Model 3', 'Premium', 330000, 0, 0, 14000, 'LC-TM3-25', 'IRK-G1', '2025-01-05', 445000)
;

/* ---------------- CUSTOMER (8 kunder – ingen datoer) ---------------- */

INSERT INTO customers (first_name, last_name, phone, license_number,
                       email, address, zip, floor, country)
VALUES ('Lars', 'Jensen', '11111111', 'DK1234567', 'lars@mail.com', 'Gade 1', 1000, '1.tv', 'Denmark'),
       ('Maria', 'Hansen', '22222222', 'DK2345678', 'maria@mail.com', 'Gade 2', 2000, '2.th', 'Denmark'),
       ('Omar', 'Ali', '33333333', 'DK3456789', 'omar@mail.com', 'Gade 3', 2100, 'st.', 'Denmark'),
       ('Sofie', 'Poulsen', '44444444', 'DK4567890', 'sofie@mail.com', 'Gade 4', 3000, '3.tv', 'Denmark'),
       ('Jesper', 'Mortensen', '55555555', 'DK5678901', 'jesper@mail.com', 'Gade 5', 4000, '4.mf', 'Denmark'),
       ('Emma', 'Lind', '66666666', 'DK6789012', 'emma@mail.com', 'Gade 6', 5000, '5.th', 'Denmark'),
       ('Noah', 'Grøn', '77777777', 'DK7890123', 'noah@mail.com', 'Gade 7', 6000, '6.tv', 'Denmark'),
       ('Ida', 'Møller', '88888888', 'DK8901234', 'ida@mail.com', 'Gade 8', 7000, '7.th', 'Denmark');


/* ---------------- RENTER (5 renters – dækker creditScore ENUMs) ---------------- */

INSERT INTO renters (credit_score, ssn, customer_id)
VALUES ('APPROVED', '010101-1111', 1),
       ('PENDING', '020202-2222', 2),
       ('REJECTED', '030303-3333', 3),
       ('APPROVED', '040404-4444', 4),
       ('APPROVED', '050505-5555', 5);


/* ---------------- LEASECONTRACTS (datoer flyttet til nov/dec 2025+) ---------------- */

INSERT INTO lease_contracts (leasing_contract_terms, lease_contract_date, start_date, end_date,
                             rental_price, subscription, approved_date, deposit_payed_date,
                             full_amount_payed_date, renter_id, vehicle_id)
VALUES
    -- 1: lang kontrakt, starter dec 2025, slutter dec 2028
    ('Standard kontrakt', '2025-11-01', '2025-12-01', '2028-12-01', 4500.00, 'Unlimited',
     '2025-11-05 10:00:00', '2025-11-08 12:00:00', NULL, 1, 1),

    -- 2: kort tids leasing, 1 år fra dec 2025
    ('Korttidsleasing', '2025-11-10', '2025-12-10', '2026-12-10', 3500.00, 'Limited',
     '2025-11-12 09:00:00', '2025-11-12 11:00:00', '2025-11-12 11:00:00', 2, 2),

    -- 3: endnu ikke approved
    ('Korttidsleasing', '2025-11-15', '2025-12-15', '2026-12-15', 3500.00, 'Limited',
     NULL, NULL, NULL, 3, 3),

    -- 4: unlimited, ikke approved endnu
    ('Standard kontrakt', '2025-11-18', '2025-12-20', '2027-12-20', 3600.00, 'Unlimited',
     NULL, NULL, NULL, 4, 4),

    -- 5: lang kontrakt, approved i slut nov
    ('Lang kontrakt', '2025-11-20', '2025-12-05', '2029-12-05', 4200.00, 'Unlimited',
     '2025-11-25 10:30:00', '2025-11-26 09:15:00', NULL, 5, 5),

    -- 6: standard, approved i starten af dec
    ('Standard kontrakt', '2025-11-25', '2025-12-10', '2028-12-10', 3900.00, 'Limited',
     '2025-12-01 11:00:00', NULL, NULL, 1, 6);


/* ---------------- DAMAGEREPORTS (datoer flyttet til nov/dec 2025) ---------------- */

INSERT INTO damage_reports (total_price, total_damage_price, report_date, late_return,
                            total_km, has_payed, leasing_contract_id)
VALUES (8000, 7000, '2025-11-10', TRUE, 150, '2025-11-20 14:00:00', 1),
       (5000, 4500, '2025-11-18', FALSE, 80, NULL, 2),
       (6000, 5500, '2025-11-25', TRUE, 200, '2025-12-02 16:00:00', 3),
       (3000, 2800, '2025-12-05', FALSE, 90, NULL, 5);


/* ---------------- DAMAGEITEMS (ingen datoer) ---------------- */

INSERT INTO damage_items (description, damage_item_price, damage_report_id)
VALUES ('Ridse i dør', 2000, 1),
       ('Stenslag i rude', 3000, 1),
       ('Flænge i sæde', 1500, 2),
       ('Punktering', 1200, 3),
       ('Bule i bagkofanger', 1800, 3),
       ('Reparation af spejl', 900, 4);


/* ---------------- FINALSALES (datoer flyttet til nov/dec 2025) ---------------- */

INSERT INTO final_sales (first_name, last_name, phone, email, address, zip, floor, country,
                         sale_date, buyer_type, currency, note, final_sale_price, vehicle_id)
VALUES ('Henrik', 'Madsen', '77777777', 'henrik@mail.com', 'Adresse X', 2600, 'st', 'Denmark',
        '2025-11-05', 'PREBUYER', 'DKK', 'Forhåndskøb', 150000.00, 3),

       ('Tina', 'Holm', '88888888', 'tina@mail.com', 'Adresse Y', 3000, '2.th', 'Denmark',
        '2025-11-10', 'WHOLESALER', 'EUR', 'Engroshandel', 12000.00, 4),

       ('Jonas', 'Bach', '99999999', 'jonas@mail.com', 'Adresse Z', 4000, '3.tv', 'Denmark',
        '2025-11-20', 'AUCTION', 'DKK', 'Solgt på auktion', 90000.00, 5),

       ('Eva', 'Kruse', '10101010', 'eva@mail.com', 'Adresse W', 5000, '4.tv', 'Denmark',
        '2025-11-25', 'BCA', 'EUR', 'Solgt via BCA', 11000.00, 3),

       ('Nick', 'Adler', '12121212', 'nick@mail.com', 'Adresse Q', 6000, '5.mf', 'Denmark',
        '2025-12-01', 'PRIVATE', 'DKK', 'Privat salg', 100000.00, 2),

       ('Karl', 'Lund', '13131313', 'karl@mail.com', 'Adresse P', 7000, '6.th', 'Denmark',
        '2025-12-05', 'EXPORT', 'EUR', 'Eksporteret', 8000.00, 1),

       ('Sarah', 'Nielsen', '14141414', 'sarah@mail.com', 'Adresse AA', 7100, '1.tv', 'Denmark',
        '2025-12-10', 'EXPORT', 'DKK', 'Solgt til eksport', 95000.00, 6),

       ('Oliver', 'Friis', '15151515', 'oliver@mail.com', 'Adresse BB', 7200, '2.th', 'Denmark',
        '2025-12-15', 'PRIVATE', 'EUR', 'Privat salg til EU', 13000.00, 7);


/* ---------------- PRE_SALE_AGREEMENTS (datoer flyttet til nov/dec 2025) ---------------- */

INSERT INTO pre_sale_agreements (limited_period, pre_sale_agreement_date, pickup_location, km_limit,
                                 extra_km_price, pre_sale_agreement_terms, currency, date_of_purchase,
                                 vehicle_id, pre_buyer_id)
VALUES (TRUE, '2025-11-02', 'Kbh', 15000, 1.5, 'Standard aftale', 'DKK', '2025-11-20', 1, 4),
       (FALSE, '2025-11-08', 'Aarhus', 30000, 2.0, 'Ingen km-begrænsning', 'EUR', '2025-11-25', 2, 5),
       (TRUE, '2025-11-15', 'Odense', 20000, 1.8, 'Erhvervsaftale', 'DKK', '2025-12-01', 3, 6),
       (FALSE, '2025-11-22', 'Aalborg', 25000, 2.1, 'Fleksibel aftale', 'EUR', '2025-12-05', 7, 7);


INSERT INTO status_histories (vehicle_id, status, timestamp) VALUES

-- AUDI A3 – Klar til udlejning
(1, 'PURCHASED',             '2025-11-05 08:00:00'),
(1, 'PREPARATION_FOR_RENT',  '2025-11-10 09:15:00'),
(1, 'READY_FOR_RENT',        '2025-11-15 10:00:00'),

-- BMW 320i – Udlejet nu
(2, 'PURCHASED',             '2025-11-08 08:30:00'),
(2, 'READY_FOR_RENT',        '2025-11-18 09:45:00'),
(2, 'RENTED',                '2025-12-01 14:20:00'),

-- Mercedes C200 – Returneret i december
(3, 'PURCHASED',             '2025-11-12 08:10:00'),
(3, 'RENTED',                '2025-11-25 12:30:00'),
(3, 'RETURNED',              '2025-12-10 16:40:00'),

-- Toyota Yaris – Under klargøring
(4, 'PURCHASED',             '2025-11-01 08:00:00'),
(4, 'PREPARATION_FOR_RENT',  '2025-11-28 10:15:00'),

-- Volvo XC60 – Klar til salg
(5, 'PURCHASED',             '2025-11-20 09:00:00'),
(5, 'PREPARATION_FOR_SALE',  '2025-11-28 10:45:00'),
(5, 'READY_FOR_SALE',        '2025-12-05 11:30:00'),

-- Skoda Octavia – Nyindkøbt
(6, 'PURCHASED',             '2025-11-27 08:45:00'),

-- Tesla Model 3 – Solgt i december
(7, 'PURCHASED',             '2025-11-03 08:00:00'),
(7, 'READY_FOR_SALE',        '2025-11-20 09:30:00'),
(7, 'SOLD',                  '2025-12-15 13:50:00');


/* ---------------- QUICK CHECKS ---------------- */

SELECT *
FROM cars;
SELECT *
FROM customers;
SELECT *
FROM renters;
SELECT *
FROM lease_contracts;
SELECT *
FROM damage_reports;
SELECT *
FROM damage_items;
SELECT *
FROM final_sales;
SELECT *
FROM pre_sale_agreements;
SELECT *
FROM status_histories;
