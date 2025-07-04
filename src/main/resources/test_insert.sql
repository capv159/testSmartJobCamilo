INSERT INTO user_test (id,name,email,password,modified,created,lastlogin,token,isactive) 
VALUES('aaf924e3-bb19-4e3a-9b73-cd5970c7f3db','usuario nombre','aaaa@mail','312456','20240202','20240202','20240202','token usuario',1);

INSERT INTO phone_test (number,citycode,countrycode,user_id) 
VALUES('300630','001','57','aaf924e3-bb19-4e3a-9b73-cd5970c7f3db');


INSERT INTO parameter_test (name,valueparameter) 
VALUES('password_pattern','^\d{3}[a-zA-Z]{3}$');


