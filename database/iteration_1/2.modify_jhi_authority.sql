insert into jhi_authority (name) values ('ROLE_PATIENT');
insert into jhi_authority (name) values ('ROLE_DOCTOR');

delete from jhi_user_authority where authority_name = 'ROLE_USER';
delete from jhi_user where login = 'user';
delete from jhi_authority where name = 'ROLE_USER';

