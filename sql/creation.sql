-- simona-persons

CREATE TABLE Role(
   Id SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(Id)
);

CREATE TABLE Person(
   Id SERIAL,
   LastName VARCHAR(50),
   FirstName VARCHAR(50),
   Login VARCHAR(50),
   Password VARCHAR(50),
   IdRole INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(IdRole) REFERENCES Role(Id)
);

-- simona-housings

CREATE TABLE HousingType(
   Id SERIAL,
   Label VARCHAR(50),
   PRIMARY KEY(IdType)
);

CREATE TABLE Housing(
   Id SERIAL,
   Surface INT,
   NbRooms INT,
   Way VARCHAR(50),
   PostCode VARCHAR(50),
   City VARCHAR(50),
   Price MONEY,
   IdLandlord INT NOT NULL, -- FK Account
   IdType INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(IdType) REFERENCES HousingType(Id)
);

-- simona-rentals

CREATE TABLE Rental(
   Id SERIAL,
   StartDate DATE,
   EndDate DATE,
   IdTenant INT NOT NULL, -- FK Account
   IdHousing INT NOT NULL, -- FK Housing
   PRIMARY KEY(Id),
);