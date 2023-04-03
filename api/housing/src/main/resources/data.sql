INSERT INTO HousingType (label) VALUES('apartment');
INSERT INTO HousingType (label) VALUES('house');

INSERT INTO Housing (
                     surface,
                     nbRooms,
                     street,
                     postalCode,
                     city,
                     price,
                     landlordId,
                     typeId)
VALUES (60, 2, '9 rue du Chat qui Pêche', '75005', 'Paris', 350000.00, 1, 1);
INSERT INTO Housing (
    surface,
    nbRooms,
    street,
    postalCode,
    city,
    price,
    landlordId,
    typeId)
VALUES (60, 2, '10 rue du Chat qui Pêche', '75005', 'Paris', 350000.00, 1, 1);
INSERT INTO Housing (
    surface,
    nbRooms,
    street,
    postalCode,
    city,
    price,
    landlordId,
    typeId)
VALUES (60, 2, '11 rue du Chat qui Pêche', '75005', 'Paris', 350000.00, 1, 1);

-- INSERT INTO Rental (startDate, endDate, housing_id) VALUES('2023-04-02', '2023-05-02', 1);
-- INSERT INTO Rental (startDate, endDate, housing_id) VALUES('2023-05-02', '2023-06-02', 1);
