create table if not exists users
(
    id varchar(36) not null
        constraint users_pkey
            primary key,
    username varchar(64),
    email varchar(255) not null
        constraint users_email_key
            unique,
    password varchar(255) not null,
    birthday date,
    gender varchar(16),
    height smallint,
    weight smallint,
    enabled boolean
);

create table if not exists roles
(
    id varchar(36) not null
        constraint roles_pk
            primary key,
    role varchar(32) not null
);

create unique index if not exists roles_role_uindex
    on roles (role);

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists users_roles
(
    role_id varchar(36) not null
        constraint users_roles_roles_id_fk
            references roles
            on delete cascade,
    user_id varchar(36) not null
        constraint users_roles_users_id_fk
            references users
            on delete cascade
);

create table if not exists ingredients
(
    id varchar(36) not null
        constraint ingredients_pkey
            primary key,
    name varchar(30)
);

create table if not exists recipes
(
    id varchar(36) not null
        constraint recipes_pk
            primary key,
    name varchar(255) not null,
    calories integer,
    proteins real,
    fats real,
    carbohydrates real,
    rating real,
    reviews_number integer,
    owner_id varchar(36)
        constraint recipes_users_id_fk
            references users
            on delete set null,
    cooking_method varchar(64),
    cooking_time integer,
    price integer,
    cuisine varchar(64)
);

create unique index if not exists recipes_id_uindex
    on recipes (id);

create unique index if not exists table_name_id_uindex
    on recipes (id);

create table if not exists tags
(
    name varchar(255) not null
        constraint tags_pk
            primary key
);

create unique index if not exists tags_name_uindex
    on tags (name);

create table if not exists recipe_steps
(
    id varchar(36) not null
        constraint recipe_steps_pk
            primary key,
    description varchar(2048),
    picture varchar(36),
    recipe_id varchar(36)
        constraint recipe_steps_recipe_id_fk
            references recipes (id)
            on delete cascade,
    index integer
);

create table if not exists ration_categories
(
    id varchar(36) not null
        constraint ration_categories_pk
            primary key,
    name varchar(255),
    owner_id varchar(36)
        constraint ration_categories_users_id_fk
            references users
            on delete cascade
);

create table if not exists ration_items
(
    id varchar(36) not null
        constraint ration_items_pk
            primary key,
    date date,
    category_id varchar(36)
        constraint ration_items_ration_categories_id_fk
            references ration_categories
            on delete cascade,
    owner_id varchar(36)
        constraint ration_items_users_id_fk
            references users
            on delete cascade,
    recipe_id varchar(36)
        constraint ration_items_recipes_id_fk
            references recipes (id)
            on delete cascade
);

create table if not exists tags_recipes
(
    tag_name varchar(36)
        constraint tags_recipes_tags_name_fk
            references tags
            on delete cascade,
    recipe_id varchar(36)
        constraint tags_recipes_recipes_id_fk
            references recipes
            on delete cascade
);

create table if not exists ingredients_recipes
(
    ingredient_id varchar(36)
        constraint ingredients_recipes_ingredients_id_fk
            references ingredients
            on delete cascade,
    recipe_id varchar(36)
        constraint ingredients_recipes_recipes_id_fk
            references recipes
            on delete cascade,
    value_type varchar(16),
    value real
);

create table if not exists recipe_reviews
(
    id varchar(36) not null
        constraint recipe_reviews_pk
            primary key,
    user_id varchar(36)
        constraint recipe_reviews_users_id_fk
            references users
            on delete set null,
    recipe_id varchar(36)
        constraint recipe_reviews_recipe_id_fk
            references recipes (id)
            on delete cascade,
    created_on date,
    rating real,
    review varchar(1024)
);

INSERT INTO public.roles (id, role) VALUES ('8600dac9-a123-4e6e-91b1-14276ffca3f2', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO public.roles (id, role) VALUES ('ade2c8f3-2565-44e0-b0da-77dea5398802', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO public.roles (id, role) VALUES ('cbc7c5c7-5da8-4b46-935e-caa5a27f2b33', 'ROLE_MODERATOR') ON CONFLICT DO NOTHING;