

/*
http://stackoverflow.com/questions/51470/how-do-i-reset-a-sequence-in-oracle
*/

DECLARE
    l_val number;
BEGIN

   execute immediate 'ALTER SESSION SET CURRENT_SCHEMA = HUMAN_RESOURCES_TEST';

-- RESET all sequences

    FOR US IN
        (SELECT US.SEQUENCE_NAME FROM USER_SEQUENCES US)
    LOOP
        execute immediate 'select ' || US.SEQUENCE_NAME || '.nextval from dual' INTO l_val;
        execute immediate 'alter sequence ' || US.SEQUENCE_NAME || ' increment by -' || l_val || ' minvalue 0';
        execute immediate 'select ' || US.SEQUENCE_NAME || '.nextval from dual' INTO l_val;
        execute immediate 'alter sequence ' || US.SEQUENCE_NAME || ' increment by 1 minvalue 0';
    END LOOP;



END;