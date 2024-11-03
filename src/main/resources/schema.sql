CREATE TABLE IF NOT EXISTS subscription
(
    id                varchar(255) PRIMARY KEY NOT NULL,
    is_active         bool         NOT NULL,
    subscription_type varchar(255) NULL,
    workflow_id       varchar(255) NULL
);