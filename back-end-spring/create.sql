create table maps (map_level integer not null, map_id bigserial not null, record_id bigint not null, map_after TEXT not null, map_before TEXT not null, primary key (map_id));
create table records (pointweight integer, score integer, date timestamp(6), id_user bigint not null, record_id bigserial not null, primary key (record_id));
create table users (id bigserial not null, email varchar(255) not null, password varchar(255), username varchar(255) not null, primary key (id));
alter table if exists maps add constraint FK188isox9y4mjt0o32jmpis4e5 foreign key (record_id) references records;
alter table if exists records add constraint FK72tdbt2irpftr00ly14v1gpoi foreign key (id_user) references users;
