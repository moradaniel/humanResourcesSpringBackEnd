

/*
http://stackoverflow.com/questions/51470/how-do-i-reset-a-sequence-in-oracle
*/

DECLARE
    l_val number;
BEGIN


-- RESET all sequences

    FOR US IN
        (SELECT US.SEQUENCE_NAME FROM USER_SEQUENCES US)
    LOOP
        execute immediate 'select ' || US.SEQUENCE_NAME || '.nextval from dual' INTO l_val;
        execute immediate 'alter sequence ' || US.SEQUENCE_NAME || ' increment by -' || l_val || ' minvalue 0';
        execute immediate 'select ' || US.SEQUENCE_NAME || '.nextval from dual' INTO l_val;
        execute immediate 'alter sequence ' || US.SEQUENCE_NAME || ' increment by 1 minvalue 0';
    END LOOP;



-- Empty all tables

-- disable all FK constraints from user schema


  for C1 in (select TABLE_NAME, CONSTRAINT_NAME from user_CONSTRAINTS where CONSTRAINT_TYPE = 'R' and STATUS = 'ENABLED')
  loop
      begin
         execute immediate 'ALTER TABLE '||C1.TABLE_NAME||' DISABLE CONSTRAINT '||C1.CONSTRAINT_NAME ;
         dbms_output.put_line('Disable '||c1.constraint_name||' on table '||c1.table_name);
      exception
         when others then
               dbms_output.put_line(sqlerrm||' '||c1.table_name);
      end;
   END LOOP;



-- truncate (empty) all tables from user schema

  for c2 in (select table_name from user_tables)
   loop
      begin
         execute immediate 'truncate table '||c2.table_name;
         dbms_output.put_line('truncate table '||c2.table_name);
      exception
         when others then
               dbms_output.put_line(sqlerrm||' '||c2.table_name);
      end;
   end LOOP;



-- enable all FK constraints from user schema

  for C3 in (select TABLE_NAME, CONSTRAINT_NAME from user_CONSTRAINTS where CONSTRAINT_TYPE = 'R' and STATUS = 'DISABLED')
  loop
      begin
         execute immediate 'ALTER TABLE '||C3.TABLE_NAME||' ENABLE CONSTRAINT '||C3.CONSTRAINT_NAME ;
         dbms_output.put_line('Enable '||c3.constraint_name || ' on table '||c3.table_name);
      exception
         when others then
               dbms_output.put_line(sqlerrm||' '||c3.table_name);
      end;
   end LOOP;

END;