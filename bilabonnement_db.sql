DROP DATABASE bilabonnement_db;

CREATE DATABASE IF NOT EXISTS bilabonnement_db;
USE bilabonnement_db;

CREATE TABLE IF NOT EXISTS cars
(
    vehicle_id       INT PRIMARY KEY AUTO_INCREMENT,
    chassis_number   VARCHAR(50),
    brand            VARCHAR(50),
    model            VARCHAR(50),
    equipment_level  ENUM ('BASE', 'COMFORT', 'SPORT', 'PREMIUM', 'ADVANCED'),
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
    email          VARCHAR(200) UNIQUE ,
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
    subscription           ENUM ('LIMITED', 'UNLIMITED'),
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
    customer_id              INT,

    CONSTRAINT fk_pre_sale_car FOREIGN KEY (vehicle_id) REFERENCES cars (vehicle_id),
    CONSTRAINT fk_pre_sale_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id)

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

VALUES ('CAR001', 'Audi', 'A3', 'BASE', 155000, 72000, 115, 23000, 'LC-A3-25', 'IRK-A1', '2025-01-10', 225000),
       ('CAR002', 'BMW', '320i', 'COMFORT', 225000, 112000, 128, 17000, 'LC-320-25', 'IRK-B1', '2025-01-14', 305000),
       ('CAR003', 'Mercedes', 'C200', 'SPORT', 265000, 132000, 142, 13500, 'LC-C2-25', 'IRK-C1', '2025-02-01', 365000),
       ('CAR004', 'Toyota', 'Yaris', 'PREMIUM', 135000, 52000, 95, 52000, 'LC-YA-24', 'IRK-D1', '2024-11-01', 185000),
       ('CAR005', 'Volvo', 'XC60', 'ADVANCED', 355000, 182000, 158, 11000, 'LC-XC-25', 'IRK-E1', '2025-02-10', 525000),
       ('CAR006', 'Skoda', 'Octavia', 'COMFORT', 195000, 82000, 118, 32000, 'LC-OCT-24', 'IRK-F1', '2024-12-11',
        275000),
       ('CAR007', 'Tesla', 'Model 3', 'PREMIUM', 330000, 0, 0, 14000, 'LC-TM3-25', 'IRK-G1', '2025-01-05', 445000),
       ('CAR008', 'Volkswagen', 'Golf', 'COMFORT', 185000, 78000, 120, 25000, 'LC-GOLF-25', 'IRK-H1', '2025-11-15',
        263000),
       ('CAR009', 'Peugeot', '208', 'BASE', 140000, 52000, 98, 18000, 'LC-208-25', 'IRK-I1', '2025-11-28', 192000),
       ('CAR010', 'Kia', 'Ceed', 'PREMIUM', 195000, 83000, 110, 22000, 'LC-CEED-25', 'IRK-J1', '2025-12-10', 278000),
       ('CAR011', 'Hyundai', 'Ioniq 5', 'ADVANCED', 360000, 0, 0, 9000, 'LC-ION5-25', 'IRK-K1', '2025-12-20', 465000),
       ('CAR012', 'Ford', 'Focus', 'SPORT', 175000, 70000, 125, 27000, 'LC-FOC-26', 'IRK-L1', '2026-01-05', 249000);
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
       ('Ida', 'Møller', '88888888', 'DK8901234', 'ida@mail.com', 'Gade 8', 7000, '7.th', 'Denmark'),
       ('Mikkel', 'Overgård', '16161616', 'DK9012345', 'mikkel@mail.com', 'Gade 9', 8000, '8.tv', 'Denmark'),
       ('Stine', 'Bruun', '17171717', 'DK0123456', 'stine@mail.com', 'Gade 10', 8200, '3.th', 'Denmark'),
       ('Kasper', 'Dam', '18181818', 'DK1122334', 'kasper@mail.com', 'Gade 11', 9000, '2.tv', 'Denmark'),
       ('Line', 'Koch', '19191919', 'DK5566778', 'line@mail.com', 'Gade 12', 9200, 'st.', 'Denmark');
;


/* ---------------- RENTER (5 renters – dækker creditScore ENUMs) ---------------- */

INSERT INTO renters (credit_score, ssn, customer_id)
VALUES ('APPROVED', '010101-1111', 1),
       ('PENDING', '020202-2222', 2),
       ('REJECTED', '030303-3333', 3),
       ('APPROVED', '040404-4444', 4),
       ('APPROVED', '050505-5555', 5),
       ('PENDING', '060606-6666', 6),
       ('APPROVED', '070707-7777', 7),
       ('APPROVED', '080808-8888', 9),
       ('REJECTED', '090909-9999', 10);


/* ---------------- LEASECONTRACTS (datoer flyttet til nov/dec 2025+) ---------------- */

/* ---------------- LEASECONTRACTS – konsistente med state machine ---------------- */

INSERT INTO lease_contracts (leasing_contract_terms, lease_contract_date, start_date, end_date,
                             rental_price, subscription, approved_date, deposit_payed_date,
                             full_amount_payed_date, renter_id, vehicle_id)
VALUES
    -- 1: Audi A3 (vehicle 1) – booking i fremtiden, IKKE godkendt endnu (READY_FOR_RENT)
    ('Standard kontrakt', '2025-11-20', '2026-01-10', '2026-12-31', 4500.00, 'UNLIMITED',
     NULL, NULL, NULL, 1, 1),

    -- 2: BMW 320i (vehicle 2) – leasingforløb afsluttet, bil er RETURNED og derefter SOLD
    ('Korttidsleasing', '2025-02-15', '2025-03-01', '2025-11-30', 3500.00, 'LIMITED',
     '2025-02-20 09:00:00', '2025-02-21 14:00:00', '2025-02-21 14:05:00', 2, 2),

    -- 3: Mercedes C200 (vehicle 3) – leasing afsluttet, bil er nu klar til salg (READY_FOR_SALE)
    ('Erhvervsleasing', '2025-03-10', '2025-04-01', '2025-09-30', 4000.00, 'UNLIMITED',
     '2025-03-15 11:00:00', '2025-03-16 09:00:00', NULL, 3, 3),

    -- 4: Toyota Yaris (vehicle 4) – fremtidig kontrakt, ikke godkendt (PREPARATION_FOR_RENT)
    ('Standard kontrakt', '2025-11-18', '2026-02-01', '2026-12-31', 3600.00, 'UNLIMITED',
     NULL, NULL, NULL, 4, 4),

    -- 5: Volvo XC60 (vehicle 5) – leasing afsluttet, bil er solgt
    ('Familieleasing', '2025-03-10', '2025-04-01', '2025-09-15', 5000.00, 'UNLIMITED',
     '2025-03-20 10:30:00', '2025-03-21 09:15:00', NULL, 5, 5),

    -- 6: Skoda Octavia (vehicle 6) – booking i fremtiden, ikke godkendt (READY_FOR_RENT)
    ('Standard kontrakt', '2025-12-01', '2026-01-15', '2026-12-31', 3900.00, 'LIMITED',
     NULL, NULL, NULL, 1, 6),

    -- 7: Tesla Model 3 (vehicle 7) – tidligere lejet, nu solgt
    ('Elbil kontrakt', '2025-02-10', '2025-03-01', '2025-08-01', 4800.00, 'UNLIMITED',
     '2025-02-15 10:00:00', '2025-02-16 09:30:00', NULL, 6, 7),

    -- 8: VW Golf (vehicle 8) – aktiv korttidsleasing, bilen er RENTED nu
    ('Bybil korttidsleasing', '2025-12-05', '2025-12-20', '2026-12-19', 3200.00, 'LIMITED',
     '2025-12-06 11:45:00', '2025-12-07 14:00:00', NULL, 7, 8),

    -- 9: Ford Focus (vehicle 12) – kampagneleasing, godkendt og aktiv
    ('Kampagneleasing', '2026-01-05', '2026-01-20', '2027-01-20', 3100.00, 'LIMITED',
     '2026-01-10 13:00:00', '2026-01-11 09:00:00', NULL, 9, 12);


/* ---------------- DAMAGEREPORTS – nu bundet til RETURNED og før salg ---------------- */

INSERT INTO damage_reports (total_price, total_damage_price, report_date, late_return,
                            total_km, has_payed, leasing_contract_id)
VALUES
    -- Kontrakt 2 (BMW 320i, vehicle 2) – RETURNED 2025-11-30, solgt senere
    (8000, 7000, '2025-11-30', FALSE, 14500, '2025-12-03 14:00:00', 2),
    (4500, 4200, '2025-12-02', FALSE, 14500, '2025-12-05 15:30:00', 2),

    -- Kontrakt 3 (Mercedes C200, vehicle 3) – RETURNED 2025-10-01, ikke solgt endnu
    (6000, 5500, '2025-10-02', TRUE, 13200, '2025-10-10 16:00:00', 3),
    (3000, 2800, '2025-10-05', TRUE, 13250, NULL,                      3),

    -- Kontrakt 5 (Volvo XC60, vehicle 5) – RETURNED 2025-09-15, solgt senere
    (5000, 4500, '2025-09-16', FALSE, 17800, '2025-09-25 11:10:00', 5),
    (9200, 9000, '2025-09-20', FALSE, 17950, NULL,                    5),

    -- Kontrakt 7 (Tesla Model 3, vehicle 7) – RETURNED 2025-08-01, solgt senere
    (3800, 3500, '2025-08-02', FALSE, 16000, '2025-08-10 11:10:00', 7),
    (7600, 7300, '2025-08-10', FALSE, 16120, NULL,                    7);


/* ---------------- DAMAGEITEMS (ingen datoer) ---------------- */

INSERT INTO damage_items (description, damage_item_price, damage_report_id)
VALUES ('Ridse i dør', 2000, 1),
       ('Stenslag i rude', 3000, 1),
       ('Flænge i sæde', 1500, 2),
       ('Punktering', 1200, 3),
       ('Bule i bagkofanger', 1800, 3),
       ('Reparation af spejl', 900, 4),
       ('Lakskade på fronthjelm', 2300, 5),
       ('Ødelagt tågelygte', 1900, 5),
       ('Skrammer på fælge', 2500, 6),
       ('Pletter i kabine', 1300, 6),
       ('Reparation af bagrude', 2800, 7),
       ('Udskiftning af dæk', 1000, 7),
       ('Reparation af frontgrill', 2600, 8),
       ('Polering af karrosseri', 1200, 8);


/* ---------------- FINALSALES (datoer flyttet til nov/dec 2025) ---------------- */

/* ---------------- FINALSALES – kun biler med fuld rejse frem til SOLD ---------------- */

INSERT INTO final_sales (first_name, last_name, phone, email, address, zip, floor, country,
                         sale_date, buyer_type, currency, note, final_sale_price, vehicle_id)
VALUES
    -- BMW 320i (vehicle 2) – solgt efter endt leasing
    ('Nick', 'Adler', '12121212', 'nick@mail.com', 'Adresse Q', 6000, '5.mf', 'Denmark',
     '2025-12-10', 'PRIVATE', 'DKK', 'Tidligere leasingbil, nu solgt', 155000.00, 2),

    -- Volvo XC60 (vehicle 5) – solgt efter returnering
    ('Jonas', 'Bach', '99999999', 'jonas@mail.com', 'Adresse Z', 4000, '3.tv', 'Denmark',
     '2025-12-20', 'BCA', 'DKK', 'Solgt via BCA efter leasing', 220000.00, 5),

    -- Tesla Model 3 (vehicle 7) – solgt efter leasing og export
    ('Sarah', 'Nielsen', '14141414', 'sarah@mail.com', 'Adresse AA', 7100, '1.tv', 'Denmark',
     '2025-12-15', 'EXPORT', 'DKK', 'Eksporteret efter endt leasing', 250000.00, 7);


/* ---------------- PRE_SALE_AGREEMENTS (datoer flyttet til nov/dec 2025) ---------------- */

INSERT INTO pre_sale_agreements (limited_period, pre_sale_agreement_date, pickup_location, km_limit,
                                 extra_km_price, pre_sale_agreement_terms, currency, date_of_purchase,
                                 vehicle_id, pre_sale_agreements.customer_id)
VALUES (TRUE, '2025-11-02', 'Kbh', 15000, 1.5, 'Standard aftale', 'DKK', '2025-11-20', 1, 4),
       (FALSE, '2025-11-08', 'Aarhus', 30000, 2.0, 'Ingen km-begrænsning', 'EUR', '2025-11-25', 2, 5),
       (TRUE, '2025-11-15', 'Odense', 20000, 1.8, 'Erhvervsaftale', 'DKK', '2025-12-01', 3, 6),
       (FALSE, '2025-11-22', 'Aalborg', 25000, 2.1, 'Fleksibel aftale', 'EUR', '2025-12-05', 7, 7),
       (TRUE, '2025-11-30', 'Kbh', 18000, 1.6, 'Vinterkampagne', 'DKK', '2025-12-10', 4, 1),
       (FALSE, '2025-12-05', 'Odense', 35000, 2.3, 'Fuld service inkl.', 'EUR', '2025-12-18', 8, 2),
       (TRUE, '2025-12-20', 'Aalborg', 20000, 1.9, 'Firmabils-aftale', 'DKK', '2026-01-05', 9, 3),
       (FALSE, '2026-01-10', 'Aarhus', 40000, 2.4, 'Lang pre-sale aftale', 'EUR', '2026-01-15', 10, 10);



/* ---------------- STATUS_HISTORIES – opdateret så alle flows følger state machine ---------------- */

INSERT INTO status_histories (vehicle_id, status, timestamp)
VALUES
-- 1: Audi A3 – klar til udlejning (READY_FOR_RENT), endnu ikke lejet
(1, 'PURCHASED',            '2025-01-10 08:00:00'),
(1, 'PREPARATION_FOR_RENT', '2025-11-15 09:00:00'),
(1, 'READY_FOR_RENT',       '2025-11-20 10:00:00'),

-- 2: BMW 320i – én leasing, returneret, til salg og solgt
(2, 'PURCHASED',            '2025-01-14 08:00:00'),
(2, 'PREPARATION_FOR_RENT', '2025-02-01 09:00:00'),
(2, 'READY_FOR_RENT',       '2025-02-10 09:30:00'),
(2, 'RENTED',               '2025-03-01 10:00:00'),
(2, 'RETURNED',             '2025-11-30 16:00:00'),
(2, 'PREPARATION_FOR_SALE', '2025-12-01 09:00:00'),
(2, 'READY_FOR_SALE',       '2025-12-03 10:00:00'),
(2, 'SOLD',                 '2025-12-05 14:00:00'),

-- 3: Mercedes C200 – én leasing, returneret, under salgsforberedelse (READY_FOR_SALE)
(3, 'PURCHASED',            '2025-02-01 08:10:00'),
(3, 'PREPARATION_FOR_RENT', '2025-03-01 09:00:00'),
(3, 'READY_FOR_RENT',       '2025-03-10 10:00:00'),
(3, 'RENTED',               '2025-04-01 12:30:00'),
(3, 'RETURNED',             '2025-10-01 16:40:00'),
(3, 'PREPARATION_FOR_SALE', '2025-10-10 10:30:00'),
(3, 'READY_FOR_SALE',       '2025-11-01 11:15:00'),

-- 4: Toyota Yaris – i klargøring til første udlejning
(4, 'PURCHASED',            '2024-11-01 08:00:00'),
(4, 'PREPARATION_FOR_RENT', '2025-11-28 10:15:00'),

-- 5: Volvo XC60 – én leasing, returneret, til salg og solgt
(5, 'PURCHASED',            '2025-02-10 09:00:00'),
(5, 'PREPARATION_FOR_RENT', '2025-03-01 09:45:00'),
(5, 'READY_FOR_RENT',       '2025-03-10 10:30:00'),
(5, 'RENTED',               '2025-04-01 09:00:00'),
(5, 'RETURNED',             '2025-09-15 14:25:00'),
(5, 'PREPARATION_FOR_SALE', '2025-10-01 10:45:00'),
(5, 'READY_FOR_SALE',       '2025-10-20 11:30:00'),
(5, 'SOLD',                 '2025-12-15 13:00:00'),

-- 6: Skoda Octavia – klar til udlejning, aldrig lejet endnu
(6, 'PURCHASED',            '2024-12-11 08:45:00'),
(6, 'PREPARATION_FOR_RENT', '2025-12-22 09:30:00'),
(6, 'READY_FOR_RENT',       '2026-01-02 10:00:00'),

-- 7: Tesla Model 3 – én leasing, returneret, til salg og solgt
(7, 'PURCHASED',            '2025-01-05 08:00:00'),
(7, 'PREPARATION_FOR_RENT', '2025-01-20 09:00:00'),
(7, 'READY_FOR_RENT',       '2025-02-01 09:30:00'),
(7, 'RENTED',               '2025-03-01 10:00:00'),
(7, 'RETURNED',             '2025-08-01 16:00:00'),
(7, 'PREPARATION_FOR_SALE', '2025-09-01 09:00:00'),
(7, 'READY_FOR_SALE',       '2025-10-01 10:00:00'),
(7, 'SOLD',                 '2025-12-10 14:00:00'),

-- 8: VW Golf – aktiv leasing, bilen er RENTED nu
(8, 'PURCHASED',            '2025-11-16 09:00:00'),
(8, 'PREPARATION_FOR_RENT', '2025-12-05 13:30:00'),
(8, 'READY_FOR_RENT',       '2025-12-15 10:00:00'),
(8, 'RENTED',               '2025-12-20 15:00:00'),

-- 9: Peugeot 208 – klar til udlejning, men ingen lejer endnu
(9, 'PURCHASED',            '2025-11-29 10:00:00'),
(9, 'PREPARATION_FOR_RENT', '2025-12-18 11:15:00'),
(9, 'READY_FOR_RENT',       '2026-01-07 09:45:00'),

-- 10: Kia Ceed – kun PURCHASED indtil videre
(10, 'PURCHASED',           '2025-12-11 08:30:00'),

-- 11: Hyundai Ioniq 5 – i klargøring til udlejning
(11, 'PURCHASED',           '2025-12-21 09:10:00'),
(11, 'PREPARATION_FOR_RENT','2026-01-05 12:00:00'),

-- 12: Ford Focus – nyindkøbt og senere lejet (kontrakt 9)
(12, 'PURCHASED',           '2026-01-06 08:05:00'),
(12, 'PREPARATION_FOR_RENT','2026-01-10 09:00:00'),
(12, 'READY_FOR_RENT',      '2026-01-15 10:00:00'),
(12, 'RENTED',              '2026-01-20 10:00:00');

