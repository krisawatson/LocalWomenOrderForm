USE localwomen;

-- Insert values into the advert_type table
INSERT INTO advert_type (name) VALUES ('Advert');
INSERT INTO advert_type (name) VALUES ('Feature');
INSERT INTO advert_type (name) VALUES ('Editorial');
INSERT INTO advert_type (name) VALUES ('Photo Shoot');
INSERT INTO advert_type (name) VALUES ('Front Cover');


-- Insert values into the advert_size table
INSERT INTO advert_size (name) VALUES ('Strap');
INSERT INTO advert_size (name) VALUES ('1/4');
INSERT INTO advert_size (name) VALUES ('1/2');
INSERT INTO advert_size (name) VALUES ('Full');
INSERT INTO advert_size (name) VALUES ('Spread');

-- Insert values into the publication table
INSERT INTO publication (name) VALUES ('North West / Donegal');
INSERT INTO publication (name) VALUES ('North Coast');
INSERT INTO publication (name) VALUES ('Mid Ulster');
INSERT INTO publication (name) VALUES ('Belfast');

-- Insert values into the role table
INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('USER');

-- Create a first user
INSERT INTO user (username, password, firstname, lastname, email, enabled, role_id) VALUES ('kris','$2a$10$RorPa6Wsf50Rk9CNSyeFpOGh/XH0iIBgCL01FkrQWyFCzYCCy58KO','Kris','Watson','treble00_01@hotmail.com',1,1);