ALTER TABLE account.user_role ALTER COLUMN id SET DEFAULT uuid_generate_v4();
INSERT INTO account.user_role (user_id)
select account."user".id from account."user" left join account.user_role on account."user".id = account.user_role.user_id
where user_id is null;

UPDATE account.user_role set role_id =
                                 (select account.role.id from account.role
                                  where role.role like 'ROLE_USER')
where role_id is null;