ALTER TABLE PERMISSION_UNIT_REQUEST
ADD 
FOREIGN KEY (
  REQUEST_ID
) REFERENCES REQUEST (
  REQUEST_ID
)
