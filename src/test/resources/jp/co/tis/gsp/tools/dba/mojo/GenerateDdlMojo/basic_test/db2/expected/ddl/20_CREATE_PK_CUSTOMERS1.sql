ALTER TABLE ORDERS
ADD CONSTRAINT PK_CUSTOMERS1 PRIMARY KEY
(
  ORDER_ID
);
CREATE UNIQUE INDEX PK_CUSTOMERS1 ON ORDERS
(
  ORDER_ID
);