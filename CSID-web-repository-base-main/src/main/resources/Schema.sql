Create table repository (name varchar(32) NOT NULL, owner varchar(32), forks int, open_issues int, CONSTRAINT repository_pkey PRIMARY KEY (name));
Create Table statistiques (id serial, name varchar(50), entry_type varchar(32), date_time varchar(32), value int, CONSTRAINT statistiques_pkey PRIMARY KEY (id));