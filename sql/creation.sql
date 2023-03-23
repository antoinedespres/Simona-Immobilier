-- simona-persons

CREATE TABLE Role(
   IdRole SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(IdRole)
);

CREATE TABLE Person(
   IdPerson SERIAL,
   LastName VARCHAR(50),
   FirstName VARCHAR(50),
   Login VARCHAR(50),
   Password VARCHAR(50),
   IdRole INT NOT NULL,
   PRIMARY KEY(IdPerson),
   FOREIGN KEY(IdRole) REFERENCES Role(IdRole)
);

-- simona-housings

CREATE TABLE HousingType(
   IdType SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(IdType)
);

CREATE TABLE Housing(
   IdPerson SERIAL,
   Surface INT,
   NbRooms INT,
   Way VARCHAR(50),
   PostCode VARCHAR(50),
   City VARCHAR(50),
   Price CURRENCY,
   IdPerson_1 INT NOT NULL,
   IdType INT NOT NULL,
   PRIMARY KEY(IdPerson),
   FOREIGN KEY(IdPerson_1) REFERENCES Person(IdPerson),
   FOREIGN KEY(IdType) REFERENCES HousingType(IdType)
);

-- simona-rentals

CREATE TABLE Rental(
   IdRental SERIAL,
   StartDate VARCHAR(50),
   EndDate DATE,
   IdPerson INT NOT NULL,
   IdPerson_1 INT NOT NULL,
   PRIMARY KEY(IdRental),
   FOREIGN KEY(IdPerson) REFERENCES Housing(IdPerson),
   FOREIGN KEY(IdPerson_1) REFERENCES Person(IdPerson)
);