create database if not exists estimate_db
    default character set utf8mb4
    collate utf8mb4_unicode_ci;

use estimate_db;

/*
親TABLE:
すべての見積はここに属する。
基本はINSERTのみでUPDATE、DELETEを考慮しない。
 */
CREATE TABLE estimate
(
    estimate_id VARCHAR(255) NOT NULL PRIMARY KEY
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 下書き見積
create table draft_estimate
(
    estimate_id VARCHAR(255) NOT NULL PRIMARY KEY,
    created_at  timestamp    not null default current_timestamp(),
    foreign key (estimate_id) references estimate (estimate_id)
        on delete cascade
);

-- 内容情報
CREATE TABLE estimate_context
(
    estimate_id     VARCHAR(255) NOT NULL PRIMARY KEY,
    customer_id     VARCHAR(255) not null default '',
    expiration_date timestamp    not null default '1999-01-01 00:00:00',
    subject         VARCHAR(255) not null default '',
    memo            TEXT         not null,
    foreign key (estimate_id) references estimate (estimate_id)
        on delete cascade
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 明細
create table estimate_lines
(
    estimate_id           VARCHAR(255)   NOT NULL PRIMARY KEY,
    line_tax_class_policy VARCHAR(255)   not null,
    foreign key (estimate_id) references estimate (estimate_id)
        on delete cascade
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table estimate_line
(
    estimate_id           VARCHAR(255)   NOT NULL,
    line_no               int(3) not null,
    description           VARCHAR(255)   not null default '',
    quantity              decimal(10, 4) not null default 0,
    amount                decimal(10, 4) not null default 0,
    tax_rate              decimal(10, 4) not null default 0,
    tax_class             VARCHAR(20)    not null,
    primary key (estimate_id, line_no),
    foreign key (estimate_id) references estimate (estimate_id)
        on delete cascade
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
