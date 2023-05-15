create
user "account";
alter
user "account" with PASSWORD 'account';
create schema "account";
alter
schema "account" owner to "account";
CREATE EXTENSION "uuid-ossp";