PRAGMA foreign_keys = ON;

----Authenication Area----

-- INDEPENDENT TABLES
CREATE TABLE IF NOT EXISTS AUTHORIZATION (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    LEVEL TEXT NOT NULL,
    UNIQUE(LEVEL)
);

CREATE TABLE IF NOT EXISTS USER (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    FIRST_NAME TEXT NOT NULL,
    MIDDLE_INIT CHAR(1),
    LAST_NAME TEXT NOT NULL,
    SUFFIX VARCHAR(6),
    STREET TEXT,
    CITY TEXT,
    STATE VARCHAR(2),
    ZIP VARCHAR(5),
    PHONE_NUMBER TEXT NOT NULL,
    DOB DATE NOT NULL,
    UNIQUE (FIRST_NAME, LAST_NAME, SUFFIX, DOB)
);

CREATE TABLE IF NOT EXISTS AUTHENTICATION (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    USER_NAME TEXT,
    PASSWORD TEXT NOT NULL,
    EMAIL TEXT NOT NULL,
    UNIQUE(EMAIL),
    UNIQUE(USER_NAME)
);

-- DEPENDENT TABLE
CREATE TABLE IF NOT EXISTS HAVE (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    USER INTEGER,
    RESTRICTION INTEGER NOT NULL,
    CREDENTIAL INTEGER NOT NULL,
    FOREIGN KEY (USER) REFERENCES USER (ID) ON DELETE CASCADE,
    FOREIGN KEY (RESTRICTION) REFERENCES AUTHORIZATION (ID) ON DELETE CASCADE,
    FOREIGN KEY (CREDENTIAL) REFERENCES AUTHENTICATION (ID) ON DELETE CASCADE
);

-- TRIGGERS
CREATE TRIGGER IF NOT EXISTS NEW_USER
AFTER INSERT ON USER
FOR EACH ROW
BEGIN
    INSERT INTO AUTHENTICATION (USER_NAME, PASSWORD, EMAIL)
    VALUES (
        '' || (617000000 + (NEW.ID % 1000000)),'PASSCODE1','' || (617000000 + (NEW.ID % 1000000)) || '@example.com'
    );
    INSERT INTO HAVE (USER, RESTRICTION, CREDENTIAL)
    VALUES (NEW.ID, 1, NEW.ID);
END;

CREATE TRIGGER IF NOT EXISTS REMOVE_USER
BEFORE DELETE ON USER
FOR EACH ROW
BEGIN
    DELETE FROM AUTHENTICATION
    WHERE EXISTS (
        SELECT 1 FROM HAVE
        WHERE HAVE.USER = OLD.ID AND HAVE.CREDENTIAL = AUTHENTICATION.ID
    );
END;

-- DATA ENTRY

INSERT INTO AUTHORIZATION(LEVEL) VALUES
('CUSTOMER'),
('WORKER'),
('ADMIN');

-- !!!USERS DUMMY DATA!!!
INSERT INTO USER(FIRST_NAME,MIDDLE_INIT,LAST_NAME,SUFFIX,DOB,PHONE_NUMBER,STREET,CITY,STATE,ZIP) VALUES
('JOHN','J','DOE',NULL,'1/23/1993','706-111-2222','888 ARBITRARY LN.','NEWMAN','GA','88888'),
('JOHN','J','DOE',NULL,'7/10/1995','706-111-2222','888 ARBITRARY LN.','NEWMAN','GA','88888'),
('JOHN','J','DOE','JR.','11/21/2020 ','742-111-2222','888 ARBITRARY LN.','NEWMAN','GA','88888'),
('JOHN','J','DOE','II','11/21/2020','742-111-2222','888 ARBITRARY LN.','NEWMAN','GA','88888'),
('JOANNA','M','DOE',NULL,'1/10/1996','734-111-2222','888 ARBITRARY LN.','NEWMAN','GA','88888'),
('ADUWAN',NULL,'MINUTES',NULL,'1/22/1897','745-111-2222','100 ARBITRARY LN.','NEWMAN','GA','88888'),
('WORKLIN',NULL,'QUIERMAN',NULL,'1/23/1777','721-111-2222','200 ARBITRARY LN.','NEWMAN','GA','88888'),
('NOWMY',NULL,'ELITEEMPLOYEE',NULL,'8/12/1952','767-111-2222','250 PROMOTION DRIVE.','WORKERS','NJ','36524'),
('WORK',NULL,'ERMAN',NULL,'1/23/1777','721-111-2222','100 WORKISHOME CT.','WORK','GA','10011'),
('JOHN',NULL,'WICK',NULL,'1/23/1993','706-111-2222','44 MILWALKIE ST ','MACON','GA','22111'),
('GRINCH',NULL,'THE GRINCH','JR.','11/21/2020 ','742-111-2222','101 CHRISTMAS AVE ','WHOVILLE','CN','61711'),
('SLOMO','M','JOE',NULL,'1/10/1996','734-111-2222','88 SLOWLANER DR.','SLOWPOKE','AL','61721'),
('REMOTE',NULL,'WORK','SR.','1/22/1987','745-111-2222','1800 REMOTE CIRCLE.','HOME','RE','61721'),
('REMOTE',NULL,'WORK','JR.','1/22/1897','745-111-2222','1800 REMOTE CIRCLE.','HOME','RE','61721'),
('AD',NULL,'MIN','IIIIVX','8/12/1952','767-111-2222','365 WORKISHOME CT.','WORK','GA','10011');

-- MODIFICATION
UPDATE HAVE  SET RESTRICTION = 2 WHERE ID  > 7;
UPDATE HAVE  SET RESTRICTION = 3  WHERE ID >= 12;

-- VIEWS
DROP VIEW IF EXISTS CUSTOMERS;
DROP VIEW IF EXISTS EMPLOYEES;

CREATE VIEW CUSTOMERS AS
    SELECT USER.ID,USER.FIRST_NAME,USER.LAST_NAME,USER.SUFFIX,USER.DOB,AUTHENTICATION.USER_NAME, AUTHENTICATION.PASSWORD, AUTHENTICATION.EMAIL, USER.PHONE_NUMBER,
    USER.STREET,USER.CITY,USER.STATE,USER.ZIP,AUTHORIZATION.LEVEL AS AUTHORIZATION_LEVEL
    FROM USER
    JOIN HAVE  ON HAVE.USER = USER.ID 
    JOIN AUTHENTICATION ON AUTHENTICATION.ID = HAVE.CREDENTIAL
    JOIN AUTHORIZATION ON AUTHORIZATION.ID = HAVE.RESTRICTION
    WHERE HAVE.RESTRICTION = 1;

CREATE VIEW EMPLOYEES AS
    SELECT USER.ID,USER.FIRST_NAME, USER.LAST_NAME, USER.SUFFIX,USER.DOB,AUTHENTICATION.USER_NAME, AUTHENTICATION.PASSWORD, AUTHENTICATION.EMAIL, USER.PHONE_NUMBER,
    USER.STREET,USER.CITY,USER.STATE,USER.ZIP,AUTHORIZATION.LEVEL AS AUTHORIZATION_LEVEL
    FROM USER
    JOIN HAVE  ON HAVE.USER = USER.ID 
    JOIN AUTHENTICATION ON AUTHENTICATION.ID = HAVE.CREDENTIAL
    JOIN AUTHORIZATION ON AUTHORIZATION.ID = HAVE.RESTRICTION
    WHERE HAVE.RESTRICTION = 2 OR HAVE.RESTRICTION = 3 ;

---- SERVICE AREA----

-- INDEPENDENT TABLES

CREATE TABLE IF NOT EXISTS SERVICE(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    SERVICE_NAME NOT NULL,
    PRICE NUMERIC(10, 2) CHECK (PRICE >= 0),
    TIME TEXT,
    DATE_OFFERED DATE NOT NULL,
    UNIQUE(SERVICE_NAME)
);

CREATE TABLE IF NOT EXISTS SKILL(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    CREDENTIALS TEXT NOT NULL
);

-- DEPENDENT TABLES

CREATE TABLE IF NOT EXISTS REQUIRES(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    SKILL INTEGER,
    SERVICE INTEGER,
    UNIQUE(SERVICE,SKILL),
    FOREIGN KEY (SKILL) REFERENCES SKILLS(ID),
    FOREIGN KEY (SERVICE) REFERENCES SERVICE(ID)
);

CREATE TABLE IF NOT EXISTS CERTIFIED(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    SKILL INTEGER,
    WORKER INTEGER,
    DATE_OBTAINED DATE,
    UNIQUE(SKILL,WORKER),
    FOREIGN KEY (SKILL) REFERENCES SKILL(ID),
    FOREIGN KEY (WORKER) REFERENCES EMPLOYEES(ID)
);

-- CREATE TABLE NOT EXISTS AVAILABILITY(
--     ID INTEGER PRIMARY KEY AUTOINCREMENT,
--     MONTH VARCHAR(3) NOT NULL,
--     DAY VARCHAR(2) NOT NULL,
--     YEAR VARCHAR(4) NOT NULL,
--     START_TIME NOT NULL TEXT,
--     END_TIME NOT NULL TEXT,
--     WORKER INTEGER,
--     FOREIGN KEY (WORKER) REFERENCES WORKERS(ID)
-- ); 

-- CREATE TABLE NOT EXISTS SCHEDULES(
--     ID INTEGER PRIMARY KEY AUTOINCREMENT,
--     AVAILABILITY INTEGER NOT NULL,
--     CLIENT INTEGER NOT NULL,
--     FOREIGN KEY (AVAILABILITY)REFRENCES ,
--     FOREIGN KEY (CLIENT) REFERENCES CUSTOMERS.ID
-- );

-- CREATE TABLE IF NOT EXISTS JOBS(
--     ID INTEGER PRIMARY KEY AUTOINCREMENT,
--     SCHEDULES INEGER NOT NULL,
--     REQUIREMENT INTEGER,
--     FOREIGN REQUIREMENT REFERENCES REQUIRES.ID
-- );


----TRIGGERS


----- DATA ENTRY

 
----VIEWS 
-- DROP VIEW IF EXISTS SKILLED_WORKER;
-- DROP VIEW IF EXISTS SERVICE_REQUIREMENTS;
-- DROP VIEW IF EXISTS JOBS;

-- CREATE VIEW SKILLED_WORKER AS SELECT WORKER.NAME,WORKER.PHONE_NUMBER,SKILLS.SKILLS_LEVEL,CERTIFIED.DATE_OBTAINED FROM CERTIFIED JOIN SKILLS ON SKILLS.ID = CERTIFIED.SKILL JOIN WORKER  ON WORKER.ID = CERTIFIED.WORKER GROUP BY SKILLS.SKILLS_LEVEL;
-- CREATE VIEW SERVICE_REQUIREMENTS AS SELECT SERVICE.SERVICE_NAME,SKILLS.SKILLS_LEVEL FROM REQUIRES JOIN SKILLS ON SKILLS.ID = REQUIRES.SKILL JOIN SERVICES  ON SERVICES.ID = REQUIRES.SERVICE GROUP BY SERVICES.SERVICE_NAME;