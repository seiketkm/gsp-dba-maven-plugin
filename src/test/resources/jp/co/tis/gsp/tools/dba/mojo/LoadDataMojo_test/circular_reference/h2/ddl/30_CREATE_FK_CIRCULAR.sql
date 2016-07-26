ALTER TABLE PUBLIC.A_TABLE
ADD 
FOREIGN KEY (
  B_ID
) REFERENCES PUBLIC.B_TABLE (
  B_ID
);

ALTER TABLE PUBLIC.B_TABLE
ADD 
FOREIGN KEY (
  C_ID
) REFERENCES PUBLIC.A_TABLE (
  A_ID
);