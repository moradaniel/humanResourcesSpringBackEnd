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

-- SECURITY -------------------------------------------------------------------------------------------------

CREATE TABLE "SEC_ACCOUNT"
(	"ID" NUMBER(10,0) NOT NULL ENABLE,
   "NAME" VARCHAR2(255 CHAR) NOT NULL ENABLE,
   "PASSWORD" VARCHAR2(255) NOT NULL ENABLE,
   "ENABLED" NUMBER(1,0) DEFAULT 1 NOT NULL ENABLE,
   "UNLOCKED" NUMBER(1,0) DEFAULT 1 NOT NULL ENABLE,
   "EXPIRE" TIMESTAMP (7),
   "EXPIREPASSWORD" TIMESTAMP (7),
  PRIMARY KEY ("ID"));

CREATE SEQUENCE SEC_ACCOUNT_SEQ
START WITH 1
MAXVALUE 9999999999999999999999999999
MINVALUE 1
NOCYCLE
CACHE 20
NOORDER;


CREATE OR REPLACE TRIGGER SEC_ACCOUNT_TRG
before insert ON SEC_ACCOUNT for each row
WHEN (
  new.id is null
)
  begin
    select SEC_ACCOUNT_SEQ.nextval into :new.id from dual;
  end;
/

CREATE TABLE "SEC_ROLE"
(	"ID" NUMBER(10,0) NOT NULL ENABLE,
   "NAME" VARCHAR2(255 CHAR) NOT NULL ENABLE,
   "DESCRIPTION" VARCHAR2(500) NOT NULL ENABLE,
   "ENABLED" NUMBER(1,0) DEFAULT 1 NOT NULL ENABLE,
   "SORTORDER" NUMBER(10,0) DEFAULT 1 NOT NULL ENABLE,
  PRIMARY KEY ("ID")
);


CREATE SEQUENCE SEC_ROLE_SEQ
START WITH 1
MAXVALUE 9999999999999999999999999999
MINVALUE 1
NOCYCLE
CACHE 20
NOORDER;


CREATE OR REPLACE TRIGGER SEC_ROLE_TRG
before insert ON SEC_ROLE for each row
WHEN (
  new.id is null
)
  begin
    select SEC_ROLE_SEQ.nextval into :new.id from dual;
  end;
/


-- ---------------------------------------------------------------------------------

CREATE TABLE SEC_ACCOUNT_ROLE
(
  "ID" NUMBER(10,0) NOT NULL ENABLE,
  SORTORDER  NUMBER(10)                         DEFAULT 1                     NOT NULL,
  ACCOUNTID  NUMBER(10)                         NOT NULL,
  ROLEID     NUMBER(10)                         NOT NULL,
  PRIMARY KEY ("ID")
);


CREATE INDEX "fk_AccountRole__Role" ON SEC_ACCOUNT_ROLE(ROLEID);


CREATE INDEX "idx_accountRoleSortOrder" ON SEC_ACCOUNT_ROLE
(SORTORDER);

CREATE UNIQUE INDEX "accountRoleId" ON "SEC_ACCOUNT_ROLE" ("ACCOUNTID", "ROLEID");
CREATE UNIQUE INDEX "accountId" ON "SEC_ACCOUNT_ROLE" ("ACCOUNTID", "SORTORDER");



ALTER TABLE SEC_ACCOUNT_ROLE ADD (
  CONSTRAINT "fk_AccountRole__Account"
  FOREIGN KEY (ACCOUNTID)
  REFERENCES SEC_ACCOUNT (ID)
  ENABLE VALIDATE,
  CONSTRAINT "fk_AccountRole__Role"
  FOREIGN KEY (ROLEID)
  REFERENCES SEC_ROLE (ID)
  ENABLE VALIDATE);


CREATE SEQUENCE SEC_ACCOUNT_ROLE_SEQ
START WITH 1
MAXVALUE 9999999999999999999999999999
MINVALUE 1
NOCYCLE
CACHE 20
NOORDER;


CREATE OR REPLACE TRIGGER SEC_ACCOUNT_ROLE_TRG
before insert ON SEC_ACCOUNT_ROLE for each row
WHEN (
  new.id is null
)
  begin
    select SEC_ACCOUNT_ROLE_SEQ.nextval into :new.id from dual;
  end;
/



-- ---------------------------------------------------------------------------------

CREATE TABLE "SEC_ROLE_AGGR_ROLE"
(	"ROLEID" NUMBER(10,0),
   "AGGRROLEID" NUMBER(10,0),
   "SORTORDER" NUMBER(10,0) NOT NULL
);

CREATE INDEX "fk_RoleAggrRole__AggrRole" ON "SEC_ROLE_AGGR_ROLE" ("AGGRROLEID") ;

CREATE INDEX "idx_aggrRoleSortOrder" ON "SEC_ROLE_AGGR_ROLE" ("SORTORDER");




ALTER TABLE SEC_ROLE_AGGR_ROLE ADD (
  CONSTRAINT SEC_ROLE_AGGR_ROLE_PK
  PRIMARY KEY
    (ROLEID, AGGRROLEID)
  );



CREATE TABLE "SEC_PERMISSION_CONTEXT"
(
  "ID" NUMBER(10,0) NOT NULL ENABLE,
  "NAME" VARCHAR2(255) NOT NULL ENABLE,
  "DESCRIPTION" VARCHAR2(500),
  "SORTORDER" NUMBER(10,0),
  "ENABLED" NUMBER(1,0) NOT NULL ENABLE,
  PRIMARY KEY ("ID"));

CREATE UNIQUE INDEX "idx_name" ON SEC_PERMISSION_CONTEXT(NAME);
CREATE INDEX "idx_permissionContextSortOrder" ON SEC_PERMISSION_CONTEXT(SORTORDER);

CREATE SEQUENCE SEC_PERMISSION_CONTEXT_SEQ
START WITH 1
MAXVALUE 9999999999999999999999999999
MINVALUE 1
NOCYCLE
CACHE 20
NOORDER;


CREATE OR REPLACE TRIGGER SEC_PERMISSION_CONTEXT_TRG
before insert ON SEC_PERMISSION_CONTEXT for each row
WHEN (
  new.id is null
)
  begin
    select SEC_PERMISSION_CONTEXT_SEQ.nextval into :new.id from dual;
  end;
/


CREATE TABLE SEC_PERMISSION_BIT
(
  CONTEXTID    NUMBER(10)                       NOT NULL,
  POSITION     NUMBER(11)                       NOT NULL,
  NAME         VARCHAR2(255 BYTE)               NOT NULL,
  DESCRIPTION  VARCHAR2(500 BYTE),
  SORTORDER    NUMBER(11)
);



CREATE UNIQUE INDEX "idx_contextId" ON SEC_PERMISSION_BIT
(CONTEXTID, NAME);


CREATE INDEX "idx_permissionBitName" ON SEC_PERMISSION_BIT
(NAME);


CREATE INDEX "idx_permissionBitSortOrder" ON SEC_PERMISSION_BIT
(SORTORDER);


ALTER TABLE SEC_PERMISSION_BIT ADD (
  PRIMARY KEY
    (CONTEXTID, POSITION));

ALTER TABLE SEC_PERMISSION_BIT ADD (
  CONSTRAINT "fk_PermBit_PermissionContext"
  FOREIGN KEY (CONTEXTID)
  REFERENCES SEC_PERMISSION_CONTEXT (ID));




CREATE TABLE SEC_PERMISSION_GRANTED
(
  ROLEID       NUMBER(10)                       NOT NULL,
  CONTEXTID    NUMBER(10)                       NOT NULL,
  PERMISSIONS  NUMBER(20)                       NOT NULL,
  DENY         NUMBER(1)                        NOT NULL
);



CREATE INDEX "fk_PermissionGranted__Permissi" ON SEC_PERMISSION_GRANTED
(CONTEXTID);


ALTER TABLE SEC_PERMISSION_GRANTED ADD (
  PRIMARY KEY
    (ROLEID, CONTEXTID, DENY)
  );

ALTER TABLE SEC_PERMISSION_GRANTED ADD (
  CONSTRAINT "fk_PermGrnted_PermContext"
  FOREIGN KEY (CONTEXTID)
  REFERENCES SEC_PERMISSION_CONTEXT (ID)
  ENABLE VALIDATE,
  CONSTRAINT "fk_PermGrnted_Role"
  FOREIGN KEY (ROLEID)
  REFERENCES SEC_ROLE (ID)
  ENABLE VALIDATE);
-- ---------------------------------------------------------------------------------



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
-- BEGIN PERSON
-- ----------------------------


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


-- ----------------------------
-- END PERSON
-- ----------------------------


-- COMMIT;