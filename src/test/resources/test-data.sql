   insert into sec_user values('admin','admin');
    insert into sec_user values('base','base');
    insert into sec_role values('ROLE_USER','common user privilege');
    insert into sec_role values('ROLE_ADMIN','administrator privilege');
    insert into sec_role_user values('base','ROLE_USER');
    insert into sec_role_user values('admin','ROLE_USER');
    insert into sec_role_user values('admin','ROLE_ADMIN');