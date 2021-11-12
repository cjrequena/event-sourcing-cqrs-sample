-- ******************** --
-- HSQL --
-- ******************** --
DROP TABLE IF EXISTS BANK_ACCOUNT;
CREATE TABLE BANK_ACCOUNT
(
  ACCOUNT_ID UUID PRIMARY KEY,
  OWNER VARCHAR(255) NOT NULL,
  BALANCE DECIMAL,
  CREATION_DATE DATE DEFAULT CURRENT_DATE,
  VERSION INTEGER
  --CONSTRAINT BANK_ACCOUNT_PK PRIMARY KEY (ACCOUNT_ID),
  --CONSTRAINT BANK_ACCOUNT_UNIQUE UNIQUE  (OWNER)
);
