create table authorities(
   username varchar_ignorecase(50) not null,
   authority varchar_ignorecase(200) not null,
   constraint fk_authorities_users foreign key (username) references usuarios(nombre)
);
