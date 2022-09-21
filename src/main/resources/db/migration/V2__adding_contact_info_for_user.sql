alter table t_users
    add column phone varchar(10) default null unique;

alter table t_users
    add constraint check_min_length_for_phone check (length(t_users.phone) >= 8);

alter table t_users
    add column email varchar(310) default null unique;

alter table t_users
    add constraint check_min_length_for_email check (length(t_users.email) >= 20);
