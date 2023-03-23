-- simona-persons

CREATE TABLE Role(
   Id SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(Id)
);

CREATE TABLE Person(
   IdPerson SERIAL,
   LastName VARCHAR(50),
   FirstName VARCHAR(50),
   Login VARCHAR(50),
   Password VARCHAR(50),
   IdRole INT NOT NULL,
   PRIMARY KEY(IdPerson),
   FOREIGN KEY(IdRole) REFERENCES Role(Id)
);

-- simona-housings

CREATE TABLE HousingType(
   IdType SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(IdType)
);

CREATE TABLE Housing(
   IdHousing SERIAL,
   Surface INT,
   NbRooms INT,
   Way VARCHAR(50),
   PostCode VARCHAR(50),
   City VARCHAR(50),
   Price MONEY,
   IdPerson INT NOT NULL,
   IdType INT NOT NULL,
   PRIMARY KEY(IdHousing),
   FOREIGN KEY(IdType) REFERENCES HousingType(Id)
);

-- simona-rentals

CREATE TABLE Rental(
   IdRental SERIAL,
   StartDate VARCHAR(50),
   EndDate DATE,
   IdPerson INT NOT NULL,
   IdHousing INT NOT NULL,
   PRIMARY KEY(IdRental),
);