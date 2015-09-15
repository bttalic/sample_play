# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table image (
  id                        integer not null,
  public_id                 varchar(255),
  image_url                 varchar(255),
  secret_image_url          varchar(255),
  is_published              boolean,
  constraint pk_image primary key (id))
;

create table post (
  id                        integer not null,
  title                     varchar(255),
  content                   varchar(255),
  user_email                varchar(255),
  image_id                  integer,
  constraint uq_post_image_id unique (image_id),
  constraint pk_post primary key (id))
;

create table role (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_role primary key (id))
;

create table ApplicationUser (
  email                     varchar(255) not null,
  password                  varchar(255),
  role_id                   integer,
  image_id                  integer,
  constraint uq_ApplicationUser_image_id unique (image_id),
  constraint pk_ApplicationUser primary key (email))
;

create sequence image_seq;

create sequence post_seq;

create sequence role_seq;

create sequence ApplicationUser_seq;

alter table post add constraint fk_post_user_1 foreign key (user_email) references ApplicationUser (email) on delete restrict on update restrict;
create index ix_post_user_1 on post (user_email);
alter table post add constraint fk_post_image_2 foreign key (image_id) references image (id) on delete restrict on update restrict;
create index ix_post_image_2 on post (image_id);
alter table ApplicationUser add constraint fk_ApplicationUser_role_3 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_ApplicationUser_role_3 on ApplicationUser (role_id);
alter table ApplicationUser add constraint fk_ApplicationUser_image_4 foreign key (image_id) references image (id) on delete restrict on update restrict;
create index ix_ApplicationUser_image_4 on ApplicationUser (image_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists image;

drop table if exists post;

drop table if exists role;

drop table if exists ApplicationUser;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists image_seq;

drop sequence if exists post_seq;

drop sequence if exists role_seq;

drop sequence if exists ApplicationUser_seq;

