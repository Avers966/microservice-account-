INSERT INTO account.user_authority (user_id)
select account."user".id from account."user" left join account.user_authority
    on account."user".id = account.user_authority.user_id
where user_id is null;

UPDATE account.user_authority set authority_id =
                                 (select account.authority.id from account.authority
                                  where authority.authority like 'POST')
where user_authority.authority_id is null;