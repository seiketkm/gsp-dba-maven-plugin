ALTER TABLE TEST_TBL1
ADD CONSTRAINT PK_TEST1 PRIMARY KEY
(
  TEST_TBL1_ID
);
CREATE UNIQUE INDEX PK_TEST1 ON TEST_TBL1
(
  TEST_TBL1_ID
);