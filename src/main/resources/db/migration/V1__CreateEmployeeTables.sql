CREATE TABLE EMPLOYEE (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(100) NOT NULL,
    middleName VARCHAR(100),
    lastName VARCHAR(100) NOT NULL,
    birthDate DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    ethnicity VARCHAR(20) NOT NULL,
    usCitizen BOOLEAN NOT NULL,
    email VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL
);

