

-- http://www.dotnetspeak.com/oracle/truncate-all-tables-in-oracle-and-sql-server/

-- SELECT
-- 'Alter Table ' || c.table_name || ' disable constraint ' ||  c.constraint_name || ';'
-- FROM user_constraints p
-- JOIN user_constraints c ON(p.constraint_name=c.r_constraint_name)
-- WHERE (p.constraint_type = 'P' OR p.constraint_type = 'U')
-- AND c.constraint_type = 'R'
-- union all
-- select 'truncate table ' || table_name || ';'
-- from user_tables
-- union all
-- SELECT
-- 'Alter Table ' || c.table_name || ' enable constraint ' ||  c.constraint_name || ';'
-- FROM user_constraints p
-- JOIN user_constraints c ON(p.constraint_name=c.r_constraint_name)
-- WHERE (p.constraint_type = 'P' OR p.constraint_type = 'U')
-- AND c.constraint_type = 'R';

ALTER SESSION SET CURRENT_SCHEMA = HUMAN_RESOURCES_TEST;

Alter Table "SEC_ACCOUNT_ROLE" disable constraint "fk_AccountRole__Account";
Alter Table "SEC_ACCOUNT_ROLE" disable constraint "fk_AccountRole__Role";
Alter Table "SEC_PERMISSION_GRANTED" disable constraint "fk_PermGrnted_Role";
Alter Table "SEC_PERMISSION_BIT" disable constraint "fk_PermBit_PermissionContext";
Alter Table "SEC_PERMISSION_GRANTED" disable constraint "fk_PermGrnted_PermContext";
truncate table "SEC_ACCOUNT";
truncate table "SEC_ROLE";
truncate table "SEC_ACCOUNT_ROLE";
truncate table "SEC_ROLE_AGGR_ROLE";
truncate table "SEC_PERMISSION_CONTEXT";
truncate table "SEC_PERMISSION_BIT";
truncate table "SEC_PERMISSION_GRANTED";
truncate table "DEPARTMENT";
truncate table "EMPLOYMENT";
truncate table "PERSON";
Alter Table "SEC_ACCOUNT_ROLE" enable constraint "fk_AccountRole__Account";
Alter Table "SEC_ACCOUNT_ROLE" enable constraint "fk_AccountRole__Role";
Alter Table "SEC_PERMISSION_GRANTED" enable constraint "fk_PermGrnted_Role";
Alter Table "SEC_PERMISSION_BIT" enable constraint "fk_PermBit_PermissionContext";
Alter Table "SEC_PERMISSION_GRANTED" enable constraint "fk_PermGrnted_PermContext";