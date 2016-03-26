CREATE TABLE PRODUCT (
  PRODUCT_ID BIGINT NOT NULL,
  PRODUCT_NAME VARCHAR(100),
  PRICE BIGINT,
  ORDER_ID BIGINT NOT NULL,
  ORDER_DETAIL_ID BIGINT NOT NULL
);
COMMENT ON table PRODUCT is '商品';
COMMENT ON column PRODUCT.PRODUCT_ID is '商品ID';
COMMENT ON column PRODUCT.PRODUCT_NAME is '商品名';
COMMENT ON column PRODUCT.PRICE is '価格';
COMMENT ON column PRODUCT.ORDER_ID is '注文ID';
COMMENT ON column PRODUCT.ORDER_DETAIL_ID is '明細ID';
