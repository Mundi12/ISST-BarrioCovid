INSERT INTO usuarios (id, nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (1, 'Perfumería Julia','{bcrypt}$2a$04$tb4/B9kdGCgX4Ue31vYRFeDzEBeV4z3brcX2iBxFtru04KInxWG76','juliaperfumerias@gmail.com','C. de Alcalá, 5
28014 Madrid','655432515','ROLE_VEND',false);
INSERT INTO usuarios (id, nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (2, 'Supermercado BM','{bcrypt}$2a$04$IyiAAjwITzQNciuFX1RbTueFVZKR8wmXQXY5ACXK6emza0GoLBOiO','supermarket.bm@gmail.com','C. de la Cruz, 23, 28012 Madrid','655432518','ROLE_VEND',false);
INSERT INTO usuarios (id, nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (3, 'Mercería La Antigua','{bcrypt}$2a$04$pIfSYD5IbWt4PotocEh3AOe6gM4ZMxNZQ1BvSissj0lt9QPGMFj.G','merceria.antigua@gmail.com','C. de Tetuán, 1
28013 Madrid','655432515','ROLE_VEND',false);
INSERT INTO usuarios (id, nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (4, 'Farmacia Trébol','{bcrypt}$2a$04$hTHncTUc.C1LfK3MLTcdWeUSgfN7BRw9uKdGT8o5egGUeUQOY63zK','trebol@gmail.com','C. de Preciados, 14, 28013 Madrid','655432518','ROLE_VEND',false);
INSERT INTO usuarios (id, nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (5, 'Lucia Garcia','{bcrypt}$2a$04$hWh/4lIP5oMlQA9JXgJmju6GlHjYCaYv4wR72Rb3/zJwbrzUzWMNa','lucia.garcia@gmail.com','Calle Alcalá,15, 3A','655432518','ROLE_COMP',true);

INSERT INTO usuarios (id,nombre,contraseña,email,direccion,telefono,tipo,esvulnerable) VALUES (6,'Pepe Jiménez','{bcrypt}$2a$04$SuWcMJlUCEp7wxSi9YYop.z5bFHxx4wMbUkr4VvzHX5KrYYcIIV4y','pepe.jimenez@gmail.com','Calle Alcalá, 60, 1B','678455321','ROLE_VOL',false);
 
INSERT INTO productos (id,nombre,precio,cantidad,usuario_id) VALUES (1,'Patata',2,3,2);
INSERT INTO productos (id,nombre,precio,cantidad,usuario_id) VALUES (2,'Chocolate',2.5,5,2);
INSERT INTO productos (id,nombre,precio,cantidad,usuario_id) VALUES (3,'Coca Cola',1.75,4,2);
 
 
 
INSERT INTO authorities(username,authority) values ('Perfumería Julia','ROLE_VEND');
INSERT INTO authorities(username,authority) values ('Supermercado BM','ROLE_VEND');
INSERT INTO authorities(username,authority) values ('Mercería La Antigua','ROLE_VEND');
INSERT INTO authorities(username,authority) values ('Farmacia Trébol','ROLE_VEND');
INSERT INTO authorities(username,authority) values ('Lucia Garcia','ROLE_COMP');
INSERT INTO authorities(username,authority) values ('Pepe Jiménez','ROLE_VOL');
