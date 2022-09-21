alter table t_users
    drop constraint check_min_length_for_email;

alter table t_users
    add constraint check_min_length_for_email check (length(t_users.email) >= 8);
