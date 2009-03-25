insert into company (id, name) values (1, 'Pixel Perfect');
insert into company (id, name) values (2, 'e-Security');

insert into company_client (company_id, client_id) values (1, 2);

insert into member_account (id, username, password_hash, enabled) values (1, 'admin', 'Ss/jICpf9c9GeJj8WKqx1hUClEE=', 1); -- blank password
insert into member_account (id, username, password_hash, enabled, company_id) values (2, 'designer', 'cCP5P7Q9v8f34kcWDIfdmzhxmBQ=', 1, 1); -- password: designer
insert into member_account (id, username, password_hash, enabled, company_id) values (3, 'secureminded', 'UNa8X+yrHZPIwz+t0GqZrH8Ea4I=', 1, 2); -- password: secure
insert into member_account (id, username, password_hash, enabled, company_id) values (4, 'securefreak', 'DKyjgWBprCuff+G0MlyyFGTtSYs=', 1, 2); -- password: secure

insert into member_role (id, name, conditional) values (1, 'admin', false);
insert into member_role (id, name, conditional) values (2, 'member', false);
insert into member_role (id, name, conditional) values (3, 'client', true);
--insert into member_role (id, name, conditional) values (4, 'guest', true);

insert into member_account_role (account_id, member_of_role) values (1, 1);
insert into member_account_role (account_id, member_of_role) values (2, 2);
insert into member_account_role (account_id, member_of_role) values (3, 2);
insert into member_account_role (account_id, member_of_role) values (4, 2);

insert into member_role_group (role_id, member_of_role) values (1, 2);
