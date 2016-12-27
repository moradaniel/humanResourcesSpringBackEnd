/*
 *
 * To find which tnsnames.ora is being used
 * c:\instantclient_11_2\tnsping XE_EROS
 *
 *
 *
 * http://www.oracle-base.com/articles/10g/Auditing_10gR2.php
 *
 *
 * initialization file init.ora
 *
 * sqlplus SYS/password@XE as sysdba
 *
 * SQL> show parameter audit;
 *
SQL> ALTER SYSTEM SET audit_trail=db,extended SCOPE=SPFILE;

System altered.

SQL> SHUTDOWN
Database closed.
Database dismounted.
ORACLE instance shut down.
SQL> STARTUP
ORACLE instance started.

Total System Global Area  289406976 bytes
Fixed Size                  1248600 bytes
Variable Size              71303848 bytes
Database Buffers          213909504 bytes
Redo Buffers                2945024 bytes
Database mounted.
Database opened.
SQL>
 *
 * SQL> AUDIT ALL BY CREDITOS BY ACCESS;
 * SQL> AUDIT SELECT TABLE, UPDATE TABLE, INSERT TABLE, DELETE TABLE BY CREDITOS BY ACCESS;
 * SQL> AUDIT EXECUTE PROCEDURE BY CREDITOS BY ACCESS;
 *
 *
 *
 * Ver vista DBA_COMMON_AUDIT_TRAIL
 *
 */

/*
 * Windows
 *
 * alter database datafile 'C:\oraclexe\oradata\XE\SYSTEM.DBF' resize 4G;
 * alter database datafile 'C:\oraclexe\oradata\XE\UNDO.DBF' resize 1024M;
 * alter database datafile 'C:\ORACLEXE\ORADATA\XE\SYSTEM.DBF'  autoextend on;
 *
 * Ubuntu
 *
 * alter database datafile '/u01/app/oracle/oradata/XE/system.dbf' resize 4G;
 * alter database datafile '/u01/app/oracle/oradata/XE/undotbs1.dbf' resize 1024M;
 * alter database datafile '/u01/app/oracle/oradata/XE/system.dbf'  autoextend on;
 *
 *
 *
 * Para deshabilitar audit
 *
 * SQL> ALTER SYSTEM SET audit_trail=none SCOPE=SPFILE;
 *
 *
 * In addition, the actions performed by administrators are recorded in the syslog audit trail.
 * */

-- START TRANSACTION;


ALTER SESSION SET CURRENT_SCHEMA = HUMAN_RESOURCES_TEST;

-- ----------------------------
-- Table structure for DEPARTMENT
-- ----------------------------
-- DROP TABLE "DEPARTMENT";
CREATE TABLE "DEPARTMENT" (
"ID" NUMBER(19) NOT NULL ,
"CODE" VARCHAR2(255 BYTE) NOT NULL ,
"NAME" VARCHAR2(255 BYTE) NOT NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for EMPLOYMENT
-- ----------------------------
CREATE TABLE "EMPLOYMENT" (
"ID" NUMBER(19) NOT NULL ,
"ID_PERSON" NUMBER(19) NOT NULL ,
"ID_DEPARTMENT" NUMBER(19) NOT NULL ,
"TITLE" VARCHAR2(255 BYTE) NOT NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for PERSON
-- ----------------------------
CREATE TABLE "PERSON" (
"ID" NUMBER(19) NOT NULL ,
"FIRST_NAME" VARCHAR2(255 BYTE) NOT NULL ,
"LAST_NAME" VARCHAR2(255 BYTE) NOT NULL ,
"CUIL" VARCHAR2(255 BYTE) NOT NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Indexes structure for table DEPARTMENT
-- ----------------------------

-- ----------------------------
-- Checks structure for table DEPARTMENT
-- ----------------------------
ALTER TABLE "DEPARTMENT" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "DEPARTMENT" ADD CHECK ("CODE" IS NOT NULL);
ALTER TABLE "DEPARTMENT" ADD CHECK ("NAME" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table DEPARTMENT
-- ----------------------------
ALTER TABLE "DEPARTMENT" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table EMPLOYMENT
-- ----------------------------

-- ----------------------------
-- Checks structure for table EMPLOYMENT
-- ----------------------------
ALTER TABLE "EMPLOYMENT" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "EMPLOYMENT" ADD CHECK ("ID_PERSON" IS NOT NULL);
ALTER TABLE "EMPLOYMENT" ADD CHECK ("ID_DEPARTMENT" IS NOT NULL);
ALTER TABLE "EMPLOYMENT" ADD CHECK ("TITLE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table EMPLOYMENT
-- ----------------------------
ALTER TABLE "EMPLOYMENT" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table PERSON
-- ----------------------------

-- ----------------------------
-- Checks structure for table PERSON
-- ----------------------------
ALTER TABLE "PERSON" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSON" ADD CHECK ("FIRST_NAME" IS NOT NULL);
ALTER TABLE "PERSON" ADD CHECK ("LAST_NAME" IS NOT NULL);
ALTER TABLE "PERSON" ADD CHECK ("CUIL" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PERSON
-- ----------------------------
ALTER TABLE "PERSON" ADD PRIMARY KEY ("ID");





-- ----------------------------
-- SEQUENCES
-- ----------------------------

	CREATE SEQUENCE PERSON_SEQ
	  START WITH 1
	  MAXVALUE 9999999999999999999999999999
	  MINVALUE 1
	  NOCYCLE
	  CACHE 20
	  NOORDER;


	CREATE OR REPLACE TRIGGER PERSON_TRG
	before insert ON PERSON for each row
	WHEN (
	new.id is null
	      )
	begin
	    select PERSON_SEQ.nextval into :new.id from dual;
	end;
	/


-- COMMIT;