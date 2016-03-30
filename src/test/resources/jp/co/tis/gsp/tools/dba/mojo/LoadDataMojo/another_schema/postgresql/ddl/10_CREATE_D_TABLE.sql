CREATE TABLE gspanother.D_TABLE (
  D_ID BIGINT NOT NULL,
  NAME VARCHAR(100),
  TEST1 VARCHAR(100),
  TEST2 VARCHAR(500),
  TEST3 VARCHAR(500),
  PRIMARY KEY (D_ID)
);
COMMENT ON table gspanother.D_TABLE is 'D_TABLE';
COMMENT ON column gspanother.D_TABLE.D_ID is 'D_ID';
COMMENT ON column gspanother.D_TABLE.NAME is 'NAME';
COMMENT ON column gspanother.D_TABLE.TEST1 is 'TEST1';
COMMENT ON column gspanother.D_TABLE.TEST2 is 'TEST2';
COMMENT ON column gspanother.D_TABLE.TEST3 is 'TEST3';
CREATE SEQUENCE gspanother.D_ID_SEQ increment by 1 start with 1;
