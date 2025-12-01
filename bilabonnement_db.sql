DROP DATABASE bilabonnement_db;

CREATE DATABASE IF NOT EXISTS bilabonnement_db;
USE bilabonnement_db;

CREATE TABLE IF NOT EXISTS Car(
                                  vehicle_ID INT PRIMARY KEY AUTO_INCREMENT,
                                  chassisNumber VARCHAR(50),
                                  brand VARCHAR (50),
                                  model VARCHAR(50),
                                  equipmentLevel ENUM('Base', 'Comfort', 'Sport', 'Premium', 'Advanced'),
                                  steelPrice DECIMAL(10,2),
                                  registrationTax DECIMAL(10,2),
                                  co2Emission INT,
                                  mileage INT,
                                  leasingCode VARCHAR(30),
                                  irkCode VARCHAR(30),
                                  dateOfPurchase DATE,
                                  purchasePrice DECIMAL(10,2)

);

CREATE TABLE IF NOT EXISTS Customer(
                                       customer_ID INT PRIMARY KEY AUTO_INCREMENT,
                                       firstName VARCHAR(50),
                                       lastName VARCHAR(50),
                                       phone VARCHAR(20),
                                       licenseNumber VARCHAR (11),
                                       email VARCHAR(200),
                                       address VARCHAR(200),
                                       zip INT,
                                       floor VARCHAR(5),
                                       country VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS Renter(
                                     renter_ID INT PRIMARY KEY AUTO_INCREMENT,
                                     creditScore ENUM('APPROVED','PENDING', 'REJECTED'),
                                     SSN VARCHAR(11),

                                     customer_ID INT,

                                     CONSTRAINT fk_renter_customer FOREIGN KEY (customer_ID) REFERENCES Customer(customer_ID)

);

CREATE TABLE IF NOT EXISTS LeaseContract(
                              leasingContract_ID INT PRIMARY KEY AUTO_INCREMENT,
                              leasingContractTerms TEXT,
                              leaseContractDate DATE,
                              startDate DATE,
                              endDate DATE,
                              rentalPrice DECIMAL(10,2),
                              subscription ENUM('Limited', 'Unlimited'),
                              approvedDate DATETIME,
                              depositPayedDate DATETIME,
                              fullAmountPayedDate DATETIME,

                              renter_ID INT,
                              vehicle_ID INT,

                              CONSTRAINT fk_lease_renter FOREIGN KEY (renter_ID) REFERENCES Renter(renter_ID),
                              CONSTRAINT fk_lease_car FOREIGN KEY (vehicle_ID) REFERENCES Car(vehicle_ID)

);

CREATE TABLE IF NOT EXISTS DamageReport(
                             damageReport_ID INT PRIMARY KEY AUTO_INCREMENT,
                             totalPrice DECIMAL(10,2),
                             totalDamagePrice DECIMAL(10,2),
                             reportDate DATE,
                             lateReturn BOOLEAN,
                             totalKm INT,
                             hasPayed DATETIME,

                             leasingContract_ID INT,

                             CONSTRAINT fk_damageReport_lease FOREIGN KEY (leasingContract_ID) REFERENCES LeaseContract(leasingContract_ID)

);

CREATE TABLE IF NOT EXISTS DamageItem(
                                         damageItem_ID INT PRIMARY KEY AUTO_INCREMENT,
                                         description VARCHAR(300),
                                         damageItemPrice DECIMAL(7,2),

                                         damageReport_ID INT,

                                         CONSTRAINT fk_damageItem_report FOREIGN KEY (damageReport_ID) REFERENCES DamageReport(damageReport_ID)
);

CREATE TABLE IF NOT EXISTS FinalSale(
                          finalSale_ID INT PRIMARY KEY AUTO_INCREMENT,
                          firstName VARCHAR(50),
                          lastName VARCHAR(50),
                          phone VARCHAR(20),
                          email VARCHAR(200),
                          address VARCHAR(200),
                          zip INT,
                          floor VARCHAR(5),
                          country VARCHAR(30),
                          saleDate DATE,
                          buyerType ENUM('PREBUYER', 'WHOLESALER', 'AUCTION', 'BCA', 'PRIVATE', 'EXPORT'),
                          currency ENUM('EUR', 'DKK'),
                          note VARCHAR(200),
                          finalSalePrice DECIMAL(10,2),

                          vehicle_ID INT,

                          CONSTRAINT fk_finalsale_car FOREIGN KEY (vehicle_ID) REFERENCES Car(vehicle_ID)
);

CREATE TABLE IF NOT EXISTS PreSaleAgreement(
                                 preSale_ID INT PRIMARY KEY AUTO_INCREMENT,
                                 limitedPeriod BOOLEAN,
                                 preSaleAgreementDate DATE,
                                 pickupLocation VARCHAR(100),
                                 kmLimit INT,
                                 extraKmPrice DECIMAL(5,2),
                                 preSaleAgreementTerms TEXT,
                                 currency ENUM('EUR', 'DKK'),
                                 dateOfPurchase DATE,

                                 vehicle_ID INT,
                                 preBuyer_ID INT,

                                 CONSTRAINT fk_presale_car FOREIGN KEY (vehicle_ID) REFERENCES Car(vehicle_ID),
                                 CONSTRAINT fk_presale_customer FOREIGN KEY (preBuyer_ID) REFERENCES Customer(customer_ID)

);

CREATE TABLE IF NOT EXISTS StatusHistory(
                              statusHistory_ID INT PRIMARY KEY AUTO_INCREMENT,
                              status ENUM ('PURCHASED', 'PREPARATION_FOR_RENT', 'READY_FOR_RENT', 'RENTED', 'RETURNED', 'PREPARATION_FOR_SALE', 'READY_FOR_SALE', 'SOLD'),
                              timestamp DATETIME,

                              vehicle_ID INT,

                              CONSTRAINT fk_status_car FOREIGN KEY (vehicle_ID) REFERENCES Car(vehicle_ID)

);



/* ---------------- CAR (7 biler: Base, Comfort, Sport, Premium, Advanced) ---------------- */

INSERT INTO Car (
    chassisNumber, brand, model, equipmentLevel,
    steelPrice, registrationTax, co2Emission, mileage,
    leasingCode, irkCode, dateOfPurchase, purchasePrice
) VALUES
      ('CAR001', 'Audi', 'A3', 'Base',     150000,  70000, 110, 20000, 'LC-A3',  'IRK-A', '2022-01-10', 220000),
      ('CAR002', 'BMW',  '320i','Comfort', 220000, 110000, 130, 15000, 'LC-320', 'IRK-B', '2023-02-14', 300000),
      ('CAR003', 'Mercedes','C200','Sport',260000, 130000, 140, 12000, 'LC-C2',  'IRK-C', '2023-05-20', 360000),
      ('CAR004', 'Toyota','Yaris','Premium',130000,  50000,  90, 50000, 'LC-YA',  'IRK-D', '2021-09-01', 180000),
      ('CAR005', 'Volvo','XC60','Advanced',350000, 180000, 160,  8000, 'LC-XC',  'IRK-E', '2024-01-15', 520000),
      ('CAR006', 'Skoda','Octavia','Comfort',190000, 80000, 120, 30000, 'LC-OCT', 'IRK-F', '2022-11-11', 270000),
      ('CAR007', 'Tesla','Model 3','Premium',320000,120000,   0, 10000, 'LC-TM3', 'IRK-G', '2024-03-01', 440000);



/* ---------------- CUSTOMER (8 kunder) ---------------- */

INSERT INTO Customer (
    firstName, lastName, phone, licenseNumber,
    email, address, zip, floor, country
) VALUES
      ('Lars',   'Jensen',    '11111111', 'DK1234567', 'lars@mail.com',   'Gade 1', 1000, '1.tv', 'Denmark'),
      ('Maria',  'Hansen',    '22222222', 'DK2345678', 'maria@mail.com',  'Gade 2', 2000, '2.th', 'Denmark'),
      ('Omar',   'Ali',       '33333333', 'DK3456789', 'omar@mail.com',   'Gade 3', 2100, 'st.',  'Denmark'),
      ('Sofie',  'Poulsen',   '44444444', 'DK4567890', 'sofie@mail.com',  'Gade 4', 3000, '3.tv', 'Denmark'),
      ('Jesper', 'Mortensen', '55555555', 'DK5678901', 'jesper@mail.com', 'Gade 5', 4000, '4.mf', 'Denmark'),
      ('Emma',   'Lind',      '66666666', 'DK6789012', 'emma@mail.com',   'Gade 6', 5000, '5.th', 'Denmark'),
      ('Noah',   'Grøn',      '77777777', 'DK7890123', 'noah@mail.com',   'Gade 7', 6000, '6.tv', 'Denmark'),
      ('Ida',    'Møller',    '88888888', 'DK8901234', 'ida@mail.com',    'Gade 8', 7000, '7.th', 'Denmark');



/* ---------------- RENTER (5 renters – dækker creditScore ENUMs) ---------------- */

INSERT INTO Renter (creditScore, SSN, customer_ID) VALUES
                                                       ('APPROVED', '010101-1111', 1),
                                                       ('PENDING',  '020202-2222', 2),
                                                       ('REJECTED', '030303-3333', 3),
                                                       ('APPROVED', '040404-4444', 4),
                                                       ('APPROVED', '050505-5555', 5);



/* ---------------- LEASECONTRACT (6 kontrakter, Limited & Unlimited) ---------------- */

INSERT INTO LeaseContract (
    leasingContractTerms, leaseContractDate, startDate, endDate,
    rentalPrice, subscription, approvedDate, depositPayedDate,
    fullAmountPayedDate, renter_ID, vehicle_ID
) VALUES
      ('Standard kontrakt', '2024-01-10', '2024-02-01', '2027-02-01', 4500.00, 'Unlimited',
       '2024-01-15 10:00:00', '2024-01-18 12:00:00', NULL, 1, 1),

      ('Korttidsleasing', '2024-03-05', '2024-03-15', '2025-03-15', 3500.00, 'Limited',
       '2024-03-06 09:00:00', '2024-03-06 11:00:00', '2024-03-06 11:00:00', 2, 2),

      ('Korttidsleasing', '2024-03-05', '2024-03-15', '2025-03-15', 3500.00, 'Limited',
       NULL, NULL, NULL, 3, 3),

      ('Standard kontrakt', '2024-03-05', '2024-03-15', '2025-03-15', 3600.00, 'Unlimited',
       NULL, NULL, NULL, 4, 4),

      ('Lang kontrakt', '2024-04-01', '2024-05-01', '2028-05-01', 4200.00, 'Unlimited',
       '2024-04-05 10:30:00', '2024-04-06 09:15:00', NULL, 5, 5),

      ('Standard kontrakt', '2024-05-10', '2024-06-01', '2027-06-01', 3900.00, 'Limited',
       '2024-05-12 11:00:00', NULL, NULL, 1, 6);



/* ---------------- DAMAGEREPORT (4 rapporter – lateReturn TRUE/FALSE) ---------------- */

INSERT INTO DamageReport (
    totalPrice, totalDamagePrice, reportDate, lateReturn,
    totalKm, hasPayed, leasingContract_ID
) VALUES
      (8000, 7000, '2024-07-01', TRUE,  150, '2024-07-10 14:00:00', 1),
      (5000, 4500, '2024-08-20', FALSE,  80, NULL,                  2),
      (6000, 5500, '2024-09-01', TRUE,  200, '2024-09-10 16:00:00', 3),
      (3000, 2800, '2024-10-15', FALSE,  90, NULL,                  5);



/* ---------------- DAMAGEITEM (flere items pr. rapport) ---------------- */

INSERT INTO DamageItem (description, damageItemPrice, damageReport_ID) VALUES
                                                                           ('Ridse i dør',           2000, 1),
                                                                           ('Stenslag i rude',       3000, 1),
                                                                           ('Flænge i sæde',         1500, 2),
                                                                           ('Punktering',            1200, 3),
                                                                           ('Bule i bagkofanger',    1800, 3),
                                                                           ('Reparation af spejl',    900, 4);



/* ---------------- FINALSALE (flere buyerType + EUR & DKK) ---------------- */

INSERT INTO FinalSale (
    firstName, lastName, phone, email, address, zip, floor, country,
    saleDate, buyerType, currency, note, finalSalePrice, vehicle_ID
) VALUES
      ('Henrik', 'Madsen', '77777777', 'henrik@mail.com', 'Adresse X', 2600, 'st',   'Denmark',
       '2024-10-01', 'PREBUYER',   'DKK', 'Forhåndskøb',        150000.00, 3),

      ('Tina',   'Holm',   '88888888', 'tina@mail.com',   'Adresse Y', 3000, '2.th', 'Denmark',
       '2024-09-20', 'WHOLESALER', 'EUR', 'Engroshandel',       12000.00, 4),

      ('Jonas',  'Bach',   '99999999', 'jonas@mail.com',  'Adresse Z', 4000, '3.tv', 'Denmark',
       '2024-11-15', 'AUCTION',    'DKK', 'Solgt på auktion',   90000.00, 5),

      ('Eva',    'Kruse',  '10101010', 'eva@mail.com',    'Adresse W', 5000, '4.tv', 'Denmark',
       '2024-12-01', 'BCA',        'EUR', 'Solgt via BCA',      11000.00, 3),

      ('Nick',   'Adler',  '12121212', 'nick@mail.com',   'Adresse Q', 6000, '5.mf', 'Denmark',
       '2024-12-20', 'PRIVATE',    'DKK', 'Privat salg',       100000.00, 2),

      ('Karl',   'Lund',   '13131313', 'karl@mail.com',   'Adresse P', 7000, '6.th', 'Denmark',
       '2024-12-22', 'EXPORT',     'EUR', 'Eksporteret',         8000.00, 1),

      ('Sarah',  'Nielsen','14141414','sarah@mail.com',  'Adresse AA',7100, '1.tv', 'Denmark',
       '2024-12-24', 'EXPORT',     'DKK', 'Solgt til eksport',  95000.00, 6),

      ('Oliver', 'Friis',  '15151515','oliver@mail.com', 'Adresse BB',7200, '2.th', 'Denmark',
       '2024-12-28', 'PRIVATE',    'EUR', 'Privat salg til EU', 13000.00, 7);



/* ---------------- PRESALEAGREEMENT (TRUE/FALSE + EUR & DKK) ---------------- */

INSERT INTO PreSaleAgreement (
    limitedPeriod, preSaleAgreementDate, pickupLocation, kmLimit,
    extraKmPrice, preSaleAgreementTerms, currency, dateOfPurchase,
    vehicle_ID, preBuyer_ID
) VALUES
      (TRUE,  '2024-06-01', 'Kbh',    15000, 1.5, 'Standard aftale',       'DKK', '2024-12-01', 1, 4),
      (FALSE, '2024-07-10', 'Aarhus', 30000, 2.0, 'Ingen km-begrænsning',  'EUR', '2024-11-20', 2, 5),
      (TRUE,  '2024-08-01', 'Odense', 20000, 1.8, 'Erhvervsaftale',        'DKK', '2024-12-05', 3, 6),
      (FALSE, '2024-09-15', 'Aalborg',25000, 2.1, 'Fleksibel aftale',      'EUR', '2024-11-30', 7, 7);



/* ---------------- STATUSHISTORY (status-flow for flere biler) ---------------- */

INSERT INTO StatusHistory (status, timestamp, vehicle_ID) VALUES
                                                              -- Bil 1 fuldt flow
                                                              ('PURCHASED',             '2022-01-01 10:00:00', 1),
                                                              ('PREPARATION_FOR_RENT',  '2022-01-05 09:00:00', 1),
                                                              ('READY_FOR_RENT',        '2022-01-10 08:00:00', 1),
                                                              ('RENTED',                '2022-02-01 12:00:00', 1),
                                                              ('RETURNED',              '2022-06-01 12:00:00', 1),
                                                              ('PREPARATION_FOR_SALE',  '2023-01-01 09:00:00', 1),
                                                              ('READY_FOR_SALE',        '2023-01-10 09:00:00', 1),
                                                              ('SOLD',                  '2023-02-01 14:00:00', 1),

                                                              -- Bil 2 kortere flow
                                                              ('PURCHASED',             '2023-02-01 10:00:00', 2),
                                                              ('READY_FOR_RENT',        '2023-02-10 09:00:00', 2),
                                                              ('RENTED',                '2023-03-01 12:00:00', 2),

                                                              -- Bil 3 køb -> klar til salg -> solgt
                                                              ('PURCHASED',             '2023-05-01 11:00:00', 3),
                                                              ('READY_FOR_SALE',        '2024-01-01 15:00:00', 3),
                                                              ('SOLD',                  '2024-02-01 10:30:00', 3);



/* ---------------- QUICK CHECKS ---------------- */

SELECT * FROM Car;
SELECT * FROM Customer;
SELECT * FROM Renter;
SELECT * FROM LeaseContract;
SELECT * FROM DamageReport;
SELECT * FROM DamageItem;
SELECT * FROM FinalSale;
SELECT * FROM PreSaleAgreement;
SELECT * FROM StatusHistory;
