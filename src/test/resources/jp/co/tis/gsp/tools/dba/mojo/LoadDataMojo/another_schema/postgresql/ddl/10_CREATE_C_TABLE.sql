CREATE TABLE gspanother.C_TABLE (
  C_ID BIGINT NOT NULL,
  NAME VARCHAR(100),
  TEST1 VARCHAR(100),
  TEST2 VARCHAR(500),
  TEST3 VARCHAR(500),
  PRIMARY KEY (C_ID)
);
COMMENT ON table gspanother.C_TABLE is 'C_TABLE';
COMMENT ON column gspanother.C_TABLE.C_ID is 'C_ID';
COMMENT ON column gspanother.C_TABLE.NAME is 'NAME';
COMMENT ON column gspanother.C_TABLE.TEST1 is 'TEST1';
COMMENT ON column gspanother.C_TABLE.TEST2 is 'TEST2';
COMMENT ON column gspanother.C_TABLE.TEST3 is 'TEST3';
CREATE SEQUENCE gspanother.C_ID_SEQ increment by 1 start with 1;
