-- 1) EMPRESAS
INSERT INTO company (company_name, trade_name, cnpj)
VALUES
    ('Scuderia Ferrari','Ferrari','11111111111111'),
    ('Vasco da Gama','Vasco','22222222222222'),
    ('sqlutions co.','sqlutions','33333333333333');

-- 2) FUNCIONARIOS
INSERT INTO employee (name, birth_date, sex, blood_type, company_id)
VALUES
    ('Davi','1990-01-01','M','O+', 1),
    ('Augusto','1990-01-02','M','O+', 2),
    ('Tiago','1990-01-03','M','O+', 3),
    ('Cainã','1990-01-04','M','O+', 1),
    ('Celso','1990-01-05','M','O+', 2),
    ('Devanir','1990-01-06','M','O+', 3),
    ('Gloria','1990-01-07','M','O+', 1),
    ('Joao','1990-01-08','M','O+', 2),
    ('Lucas','1990-01-09','M','O+', 3),
    ('Bryan','1990-01-10','M','O+', 1),
    ('Enzo','1990-01-11','M','O+', 2),
    ('Nadalete','1990-01-12','M','O+', 3),
    ('Mineda','1990-01-13','M','O+', 1),
    ('Daniel Floriano','1990-01-14','M','O+', 2),
    ('Carlota','1990-01-15','M','O+', 3),
    ('Sim','1990-01-16','M','O+', 1),
    ('Batman','1990-01-17','M','O+', 2),
    ('Yuri Alberto','1990-01-18','M','O+', 3),
    ('Ayrton Senna','1990-01-19','M','O+', 1),
    ('Eu','1990-01-20','M','O+', 2);


-- 3) CARGOS
INSERT INTO role (name)
VALUES
    ('Desenvolvedor'),
    ('Analista'),
    ('Gerente'),
    ('Tester'),
    ('Designer'),
    ('DevOps'),
    ('Arquiteto'),
    ('Scrum Master'),
    ('Product Owner'),
    ('Analista de Dados');

-- 4) CONTRATOS
INSERT INTO contract (start_date, end_date, company_id, employee_id, role_id)
VALUES
    ('2023-01-01', '2023-12-31', 1, 1, 1),
    ('2023-02-01', '2023-11-30', 2, 2, 2),
    ('2023-03-01', '2023-10-31', 3, 3, 3),
    ('2023-04-01', '2023-09-30', 1, 4, 10),
    ('2023-05-01', '2023-08-31', 2, 5, 2),
    ('2023-06-01', '2023-07-31', 3, 6, 4),
    ('2023-07-01', '2023-06-30', 1, 7, 1),
    ('2023-08-01', '2023-05-31', 2, 8, 6),
    ('2023-09-01', '2023-04-30', 3, 9, 3),
    ('2023-10-01', '2023-03-31', 1, 10, 5),
    ('2023-11-01', '2023-02-28', 2, 11, 2),
    ('2023-12-01', '2023-01-31', 3, 12, 3),
    ('2023-01-15', '2023-12-15', 1, 13, 7),
    ('2023-02-15', '2023-11-15', 2, 14, 2),
    ('2023-03-15', '2023-10-15', 3, 15, 10),
    ('2023-04-15', '2023-09-15', 1, 16, 1),
    ('2023-05-15', '2023-08-15', 2, 17, 8),
    ('2023-06-15', '2023-07-15', 3, 18, 3),
    ('2023-07-15', '2023-06-15', 1, 19, 1),
    ('2023-08-15', '2023-05-15', 2, 20, 9);

-- 5) MOVIMENTACOES FOR 3 MONTHS (2023-06-01 THROUGH 2023-08-31)
INSERT INTO clock_in (date_time, direction, employee_id)
VALUES
    -- JUNE 2023
    ('2023-06-01 08:00:00','Entrada',1),
    ('2023-06-01 12:00:00','Saida',2),
    ('2023-06-01 17:00:00','Entrada',3),
    ('2023-06-02 08:00:00','Entrada',4),
    ('2023-06-02 12:00:00','Saida',5),
    ('2023-06-02 17:00:00','Entrada',6),
    ('2023-06-03 08:00:00','Entrada',7),
    ('2023-06-03 12:00:00','Saida',8),
    ('2023-06-03 17:00:00','Entrada',9),
    ('2023-06-04 08:00:00','Entrada',10),
    ('2023-06-04 12:00:00','Saida',11),
    ('2023-06-04 17:00:00','Entrada',12),
    ('2023-06-05 08:00:00','Entrada',13),
    ('2023-06-05 12:00:00','Saida',14),
    ('2023-06-05 17:00:00','Entrada',15),
    ('2023-06-06 08:00:00','Entrada',16),
    ('2023-06-06 12:00:00','Saida',17),
    ('2023-06-06 17:00:00','Entrada',18),
    ('2023-06-07 08:00:00','Entrada',19),
    ('2023-06-07 12:00:00','Saida',20),
    ('2023-06-07 17:00:00','Entrada',1),
    ('2023-06-08 08:00:00','Entrada',2),
    ('2023-06-08 12:00:00','Saida',3),
    ('2023-06-08 17:00:00','Entrada',4),
    ('2023-06-09 08:00:00','Entrada',5),
    ('2023-06-09 12:00:00','Saida',6),
    ('2023-06-09 17:00:00','Entrada',7),
    ('2023-06-10 08:00:00','Entrada',8),
    ('2023-06-10 12:00:00','Saida',9),
    ('2023-06-10 17:00:00','Entrada',10),
    ('2023-06-11 08:00:00','Entrada',11),
    ('2023-06-11 12:00:00','Saida',12),
    ('2023-06-11 17:00:00','Entrada',13),
    ('2023-06-12 08:00:00','Entrada',14),
    ('2023-06-12 12:00:00','Saida',15),
    ('2023-06-12 17:00:00','Entrada',16),
    ('2023-06-13 08:00:00','Entrada',17),
    ('2023-06-13 12:00:00','Saida',18),
    ('2023-06-13 17:00:00','Entrada',19),
    ('2023-06-14 08:00:00','Entrada',20),
    ('2023-06-14 12:00:00','Saida',1),
    ('2023-06-14 17:00:00','Entrada',2),
    ('2023-06-15 08:00:00','Entrada',3),
    ('2023-06-15 12:00:00','Saida',4),
    ('2023-06-15 17:00:00','Entrada',5),
    ('2023-06-16 08:00:00','Entrada',6),
    ('2023-06-16 12:00:00','Saida',7),
    ('2023-06-16 17:00:00','Entrada',8),
    ('2023-06-17 08:00:00','Entrada',9),
    ('2023-06-17 12:00:00','Saida',10),
    ('2023-06-17 17:00:00','Entrada',11),
    ('2023-06-18 08:00:00','Entrada',12),
    ('2023-06-18 12:00:00','Saida',13),
    ('2023-06-18 17:00:00','Entrada',14),
    ('2023-06-19 08:00:00','Entrada',15),
    ('2023-06-19 12:00:00','Saida',16),
    ('2023-06-19 17:00:00','Entrada',17),
    ('2023-06-20 08:00:00','Entrada',18),
    ('2023-06-20 12:00:00','Saida',19),
    ('2023-06-20 17:00:00','Entrada',20),
    ('2023-06-21 08:00:00','Entrada',1),
    ('2023-06-21 12:00:00','Saida',2),
    ('2023-06-21 17:00:00','Entrada',3),
    ('2023-06-22 08:00:00','Entrada',4),
    ('2023-06-22 12:00:00','Saida',5),
    ('2023-06-22 17:00:00','Entrada',6),
    ('2023-06-23 08:00:00','Entrada',7),
    ('2023-06-23 12:00:00','Saida',8),
    ('2023-06-23 17:00:00','Entrada',9),
    ('2023-06-24 08:00:00','Entrada',10),
    ('2023-06-24 12:00:00','Saida',11),
    ('2023-06-24 17:00:00','Entrada',12),
    ('2023-06-25 08:00:00','Entrada',13),
    ('2023-06-25 12:00:00','Saida',14),
    ('2023-06-25 17:00:00','Entrada',15),
    ('2023-06-26 08:00:00','Entrada',16),
    ('2023-06-26 12:00:00','Saida',17),
    ('2023-06-26 17:00:00','Entrada',18),
    ('2023-06-27 08:00:00','Entrada',19),
    ('2023-06-27 12:00:00','Saida',20),
    ('2023-06-27 17:00:00','Entrada',1),
    ('2023-06-28 08:00:00','Entrada',2),
    ('2023-06-28 12:00:00','Saida',3),
    ('2023-06-28 17:00:00','Entrada',4),
    ('2023-06-29 08:00:00','Entrada',5),
    ('2023-06-29 12:00:00','Saida',6),
    ('2023-06-29 17:00:00','Entrada',7),
    ('2023-06-30 08:00:00','Entrada',8),
    ('2023-06-30 12:00:00','Saida',9),
    ('2023-06-30 17:00:00','Entrada',10),
    ('2023-06-30 17:00:00','Entrada',11),
    -- JULY 2023
    ('2023-07-01 08:00:00','Entrada',12),
    ('2023-07-01 12:00:00','Saida',13),
    ('2023-07-01 17:00:00','Entrada',14),
    ('2023-07-02 08:00:00','Entrada',15),
    ('2023-07-02 12:00:00','Saida',16),
    ('2023-07-02 17:00:00','Entrada',17),
    ('2023-07-03 08:00:00','Entrada',18),
    ('2023-07-03 12:00:00','Saida',19),
    ('2023-07-03 17:00:00','Entrada',20),
    ('2023-07-04 08:00:00','Entrada',1),
    ('2023-07-04 12:00:00','Saida',2),
    ('2023-07-04 17:00:00','Entrada',3),
    ('2023-07-05 08:00:00','Entrada',4),
    ('2023-07-05 12:00:00','Saida',5),
    ('2023-07-05 17:00:00','Entrada',6),
    ('2023-07-06 08:00:00','Entrada',7),
    ('2023-07-06 12:00:00','Saida',8),
    ('2023-07-06 17:00:00','Entrada',9),
    ('2023-07-07 08:00:00','Entrada',10),
    ('2023-07-07 12:00:00','Saida',11),
    ('2023-07-07 17:00:00','Entrada',12),
    ('2023-07-08 08:00:00','Entrada',13),
    ('2023-07-08 12:00:00','Saida',14),
    ('2023-07-08 17:00:00','Entrada',15),
    ('2023-07-09 08:00:00','Entrada',16),
    ('2023-07-09 12:00:00','Saida',17),
    ('2023-07-09 17:00:00','Entrada',18),
    ('2023-07-10 08:00:00','Entrada',19),
    ('2023-07-10 12:00:00','Saida',20),
    ('2023-07-10 17:00:00','Entrada',1),
    ('2023-07-11 08:00:00','Entrada',2),
    ('2023-07-11 12:00:00','Saida',3),
    ('2023-07-11 17:00:00','Entrada',4),
    ('2023-07-12 08:00:00','Entrada',5),
    ('2023-07-12 12:00:00','Saida',6),
    ('2023-07-12 17:00:00','Entrada',7),
    ('2023-07-13 08:00:00','Entrada',8),
    ('2023-07-13 12:00:00','Saida',9),
    ('2023-07-13 17:00:00','Entrada',10),
    ('2023-07-14 08:00:00','Entrada',11),
    ('2023-07-14 12:00:00','Saida',12),
    ('2023-07-14 17:00:00','Entrada',13),
    ('2023-07-15 08:00:00','Entrada',14),
    ('2023-07-15 12:00:00','Saida',15),
    ('2023-07-15 17:00:00','Entrada',16),
    ('2023-07-16 08:00:00','Entrada',17),
    ('2023-07-16 12:00:00','Saida',18),
    ('2023-07-16 17:00:00','Entrada',19),
    ('2023-07-17 08:00:00','Entrada',20),
    ('2023-07-17 12:00:00','Saida',1),
    ('2023-07-17 17:00:00','Entrada',2),
    ('2023-07-18 08:00:00','Entrada',3),
    ('2023-07-18 12:00:00','Saida',4),
    ('2023-07-18 17:00:00','Entrada',5),
    ('2023-07-19 08:00:00','Entrada',6),
    ('2023-07-19 12:00:00','Saida',7),
    ('2023-07-19 17:00:00','Entrada',8),
    ('2023-07-20 08:00:00','Entrada',9),
    ('2023-07-20 12:00:00','Saida',10),
    ('2023-07-20 17:00:00','Entrada',11),
    ('2023-07-21 08:00:00','Entrada',12),
    ('2023-07-21 12:00:00','Saida',13),
    ('2023-07-21 17:00:00','Entrada',14),
    ('2023-07-22 08:00:00','Entrada',15),
    ('2023-07-22 12:00:00','Saida',16),
    ('2023-07-22 17:00:00','Entrada',17),
    ('2023-07-23 08:00:00','Entrada',18),
    ('2023-07-23 12:00:00','Saida',19),
    ('2023-07-23 17:00:00','Entrada',20),
    ('2023-07-24 08:00:00','Entrada',1),
    ('2023-07-24 12:00:00','Saida',2),
    ('2023-07-24 17:00:00','Entrada',3),
    ('2023-07-25 08:00:00','Entrada',4),
    ('2023-07-25 12:00:00','Saida',5),
    ('2023-07-25 17:00:00','Entrada',6),
    ('2023-07-26 08:00:00','Entrada',7),
    ('2023-07-26 12:00:00','Saida',8),
    ('2023-07-26 17:00:00','Entrada',9),
    ('2023-07-27 08:00:00','Entrada',10),
    ('2023-07-27 12:00:00','Saida',11),
    ('2023-07-27 17:00:00','Entrada',12),
    ('2023-07-28 08:00:00','Entrada',13),
    ('2023-07-28 12:00:00','Saida',14),
    ('2023-07-28 17:00:00','Entrada',15),
    ('2023-07-29 08:00:00','Entrada',16),
    ('2023-07-29 12:00:00','Saida',17),
    ('2023-07-29 17:00:00','Entrada',18),
    ('2023-07-30 08:00:00','Entrada',19),
    ('2023-07-30 12:00:00','Saida',20),
    ('2023-07-30 17:00:00','Entrada',1),
    ('2023-07-31 08:00:00','Entrada',2),
    ('2023-07-31 12:00:00','Saida',3),
    ('2023-07-31 17:00:00','Entrada',4),
    -- AUGUST 2023
    ('2023-08-01 08:00:00','Entrada',5),
    ('2023-08-01 12:00:00','Saida',6),
    ('2023-08-01 17:00:00','Entrada',7),
    ('2023-08-02 08:00:00','Entrada',8),
    ('2023-08-02 12:00:00','Saida',9),
    ('2023-08-02 17:00:00','Entrada',10),
    ('2023-08-03 08:00:00','Entrada',11),
    ('2023-08-03 12:00:00','Saida',12),
    ('2023-08-03 17:00:00','Entrada',13),
    ('2023-08-04 08:00:00','Entrada',14),
    ('2023-08-04 12:00:00','Saida',15),
    ('2023-08-04 17:00:00','Entrada',16),
    ('2023-08-05 08:00:00','Entrada',17),
    ('2023-08-05 12:00:00','Saida',18),
    ('2023-08-05 17:00:00','Entrada',19),
    ('2023-08-06 08:00:00','Entrada',20),
    ('2023-08-06 12:00:00','Saida',1),
    ('2023-08-06 17:00:00','Entrada',2),
    ('2023-08-07 08:00:00','Entrada',3),
    ('2023-08-07 12:00:00','Saida',4),
    ('2023-08-07 17:00:00','Entrada',5),
    ('2023-08-08 08:00:00','Entrada',6),
    ('2023-08-08 12:00:00','Saida',7),
    ('2023-08-08 17:00:00','Entrada',8),
    ('2023-08-09 08:00:00','Entrada',9),
    ('2023-08-09 12:00:00','Saida',10),
    ('2023-08-09 17:00:00','Entrada',11),
    ('2023-08-10 08:00:00','Entrada',12),
    ('2023-08-10 12:00:00','Saida',13),
    ('2023-08-10 17:00:00','Entrada',14),
    ('2023-08-11 08:00:00','Entrada',15),
    ('2023-08-11 12:00:00','Saida',16),
    ('2023-08-11 17:00:00','Entrada',17),
    ('2023-08-12 08:00:00','Entrada',18),
    ('2023-08-12 12:00:00','Saida',19),
    ('2023-08-12 17:00:00','Entrada',20),
    ('2023-08-13 08:00:00','Entrada',1),
    ('2023-08-13 12:00:00','Saida',2),
    ('2023-08-13 17:00:00','Entrada',3),
    ('2023-08-14 08:00:00','Entrada',4),
    ('2023-08-14 12:00:00','Saida',5),
    ('2023-08-14 17:00:00','Entrada',6),
    ('2023-08-15 08:00:00','Entrada',7),
    ('2023-08-15 12:00:00','Saida',8),
    ('2023-08-15 17:00:00','Entrada',9),
    ('2023-08-16 08:00:00','Entrada',10),
    ('2023-08-16 12:00:00','Saida',11),
    ('2023-08-16 17:00:00','Entrada',12),
    ('2023-08-17 08:00:00','Entrada',13),
    ('2023-08-17 12:00:00','Saida',14),
    ('2023-08-17 17:00:00','Entrada',15),
    ('2023-08-18 08:00:00','Entrada',16),
    ('2023-08-18 12:00:00','Saida',17),
    ('2023-08-18 17:00:00','Entrada',18),
    ('2023-08-19 08:00:00','Entrada',19),
    ('2023-08-19 12:00:00','Saida',20),
    ('2023-08-19 17:00:00','Entrada',1),
    ('2023-08-20 08:00:00','Entrada',2),
    ('2023-08-20 12:00:00','Saida',3),
    ('2023-08-20 17:00:00','Entrada',4),
    ('2023-08-21 08:00:00','Entrada',5),
    ('2023-08-21 12:00:00','Saida',6),
    ('2023-08-21 17:00:00','Entrada',7),
    ('2023-08-22 08:00:00','Entrada',8),
    ('2023-08-22 12:00:00','Saida',9),
    ('2023-08-22 17:00:00','Entrada',10),
    ('2023-08-23 08:00:00','Entrada',11),
    ('2023-08-23 12:00:00','Saida',12),
    ('2023-08-23 17:00:00','Entrada',13),
    ('2023-08-24 08:00:00','Entrada',14),
    ('2023-08-24 12:00:00','Saida',15),
    ('2023-08-24 17:00:00','Entrada',16),
    ('2023-08-25 08:00:00','Entrada',17),
    ('2023-08-25 12:00:00','Saida',18),
    ('2023-08-25 17:00:00','Entrada',19),
    ('2023-08-26 08:00:00','Entrada',20),
    ('2023-08-26 12:00:00','Saida',1),
    ('2023-08-26 17:00:00','Entrada',2),
    ('2023-08-27 08:00:00','Entrada',3),
    ('2023-08-27 12:00:00','Saida',4),
    ('2023-08-27 17:00:00','Entrada',5),
    ('2023-08-28 08:00:00','Entrada',6),
    ('2023-08-28 12:00:00','Saida',7),
    ('2023-08-28 17:00:00','Entrada',8),
    ('2023-08-29 08:00:00','Entrada',9),
    ('2023-08-29 12:00:00','Saida',10),
    ('2023-08-29 17:00:00','Entrada',11),
    ('2023-08-30 08:00:00','Entrada',12),
    ('2023-08-30 12:00:00','Saida',13),
    ('2023-08-30 17:00:00','Entrada',14),
    ('2023-08-31 08:00:00','Entrada',18),
    ('2023-08-31 12:00:00','Saida',19),
    ('2023-08-31 17:00:00','Entrada',20);