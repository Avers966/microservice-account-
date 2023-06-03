ALTER TABLE account.user_role ALTER COLUMN user_id DROP NOT NULL;
ALTER TABLE account.user_role ALTER COLUMN role_id DROP NOT NULL;
ALTER TABLE account.user_role ALTER COLUMN id SET DATA TYPE UUID USING (uuid_generate_v4());
