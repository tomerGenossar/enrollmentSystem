DROP TABLE IF EXISTS TBL_PUPILS;

CREATE TABLE TBL_PUPILS (
  Id long AUTO_INCREMENT  PRIMARY KEY,
  Lat double,
  Lon double,
 first_name VARCHAR(250) NOT NULL
);

INSERT INTO TBL_PUPILS (Lat,Lon,first_name ) VALUES
  ('33.664888', '34.207917','tomer');