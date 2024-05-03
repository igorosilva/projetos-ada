create table ALUNO(
	matricula serial primary key,
	nome varchar(50) not null,
	email varchar(100) not null,
	mensalidade decimal(10,2) not null check(mensalidade >= 0)
);

create table UNIDADE_ACADEMICA(
	codigo serial primary key,
	nome varchar(50) not null
);

create table CONTRATADO(
	matricula serial primary key,
	codigo_unidade_academica serial,
	nome varchar(50) not null,
	email varchar(100) not null,
	salario decimal(10,2) not null check(salario >= 0),
	data_inicio date not null default current_date,
	foreign key(codigo_unidade_academica) references UNIDADE_ACADEMICA(codigo)
);

create table PROFESSOR(
	matricula serial primary key,
	formacao varchar(50) not null,
	foreign key(matricula) references CONTRATADO(matricula) on delete cascade
);

create table TURMA(
	numero serial primary key,
	codcred int not null,
	nome_disciplina varchar(50) not null,
	horario time not null,
	matricula_professor serial,
	foreign key(matricula_professor) references professor(matricula),
	unique(numero, codcred)
);

create table ALUNO_TURMA(
	id serial primary key,
	aluno_matricula serial,
	turma_numero serial,
	turma_codcred int,
	semestre int not null check(semestre >= 1),
	foreign key (aluno_matricula) references aluno(matricula),
	foreign key (turma_numero) references turma(numero),
	unique (aluno_matricula, turma_numero, turma_codcred, semestre)
);

create table FUNCIONARIO(
	matricula serial primary key,
	funcao varchar(50) not null,
	foreign key(matricula) references CONTRATADO(matricula) on delete cascade
);

create table DEPENDENTE(
	id serial primary key,
	matricula_contratado serial,
	nome varchar(50) not null,
	data_nascimento date not null default current_date,
	cpf varchar(14) not null,
	foreign key(matricula_contratado) references CONTRATADO(matricula) on delete cascade,
	unique(cpf)
);

-- Views
create view DETALHES_AULA as
	select at.aluno_matricula as matricula_aluno, 
		a.nome as "Aluno",
		c.nome as "Professor",
		t.nome_disciplina as "Disciplina", 
		t.horario as "Horario"
	from ALUNO_TURMA at
		inner join ALUNO a on at.aluno_matricula = a.matricula
		inner join TURMA t on at.turma_numero = t.numero
		left join PROFESSOR p on t.matricula_professor = p.matricula
        left join CONTRATADO c on p.matricula = c.matricula;

create view CONTRATADOS as
	select c.matricula as matricula, 
		'Professor' as tipo_contratado,
		c.nome as "Nome", 
		p.formacao as formacao_professor,
		c.salario as salario,
		ua.codigo as codigo_unidade,
		ua.nome as nome_unidade
	from CONTRATADO c
		inner join UNIDADE_ACADEMICA ua on c.codigo_unidade_academica = ua.codigo
		inner join PROFESSOR p on c.matricula = p.matricula
	union all
	select f.matricula as matricula,
		'Funcionario' as tipo_contratado,
		c.nome as "Nome",
		f.funcao as funcao_funcionario,
		c.salario as salario,
		ua.codigo as codigo_unidade,
		ua.nome as nome_unidade
	from CONTRATADO c
		inner join UNIDADE_ACADEMICA ua on c.codigo_unidade_academica = ua.codigo
		inner join FUNCIONARIO f on c.matricula = f.matricula;

create view DETALHES_DEPENDENTES as
	select c.matricula as matricula,
		c.nome as "Contratado",
		d.nome as "Dependente",
		d.cpf as "CPF do Dependente",
		d.data_nascimento as "Data Nascimento Dependente"
	from CONTRATADO c
		inner join DEPENDENTE d on c.matricula = d.matricula_contratado;


-- Inserts
insert into ALUNO 
(matricula, nome, email, mensalidade) 
values
    (1, 'Wesley Silva', 'wesley@email.com', 500.00),
    (2, 'Samatha Santos', 'samantha@email.com', 550.00),
    (3, 'Roberto Oliveira', 'roberto@email.com', 600.00);

insert into UNIDADE_ACADEMICA 
(codigo, nome) 
values
    (1, 'Departamento de Informática'),
    (2, 'Departamento de Matemática'),
    (3, 'Departamento de Ciências Sociais');
   
insert into CONTRATADO 
(matricula, codigo_unidade_academica, nome, email, salario, data_inicio) 
values
    (1, 1, 'Gabriel Pereira', 'gabriel@email.com', 3500.00, '2022-05-09'),
    (2, 2, 'Matheus Santos', 'matheus@email.com', 3800.00, '2023-04-11'),
    (3, 3, 'Mariana Lima', 'mariana@email.com', 3200.00, '2024-01-01'),
    (4, 1, 'Ana Oliveira', 'ana@email.com', 2800.00, '2021-05-02'),
    (5, 2, 'Carlos Santos', 'carlos@email.com', 3200.00, '2022-07-01'),
    (6, 3, 'Leonardo Silva', 'leonardo@email.com', 3000.00, '2020-08-08');

insert into PROFESSOR 
(matricula, formacao) 
values
    (1, 'Doutorado em Informática'),
    (2, 'Mestrado em Matemática'),
    (3, 'Doutorado em Sociologia');

insert into TURMA 
(numero, codcred, nome_disciplina, horario, matricula_professor) 
values
    (1, 1, 'Introdução à Programação', '08:00:00', 1),
    (2, 2, 'Cálculo I', '10:00:00', 2),
    (3, 3, 'Teoria Sociológica', '13:00:00', 3);

insert into ALUNO_TURMA 
(id, aluno_matricula, turma_numero, turma_codcred, semestre) 
values
    (1, 1, 1, 101, 1),
    (2, 2, 2, 102, 1),
    (3, 3, 3, 103, 1);

insert into FUNCIONARIO 
(matricula, funcao) 
values
    (4, 'Bibliotecário'),
    (5, 'Assistente Administrativo'),
    (6, 'Auxiliar de Limpeza');

insert into DEPENDENTE 
(id, matricula_contratado, nome, data_nascimento, cpf) 
values
    (1, 1, 'Mauricio Silva', '2010-05-15', '123.456.789-44'),
    (2, 3, 'Giovanna Oliveira', '2012-08-20', '987.654.321-00'),
    (3, 2, 'João Santos', '2008-03-10', '111.222.333-44'),
    (4, 1, 'Maria Silva', '2005-12-10', '555.666.777-88'),
    (5, 2, 'Pedro Oliveira', '2011-06-25', '999.000.123-45');

-- Selects
select * from ALUNO;
select * from TURMA;
select * from ALUNO_TURMA;
select * from PROFESSOR;
select * from DETALHES_AULA;
select * from UNIDADE_ACADEMICA;
select * from FUNCIONARIO;

select * from DEPENDENTE order by matricula_contratado;

select * from CONTRATADOS;
select * from CONTRATADOS where tipo_contratado='Professor';
select * from CONTRATADOS where tipo_contratado='Funcionario';

select * from DETALHES_DEPENDENTES order by matricula;

select avg(salario) as "Media Salarial" from CONTRATADOS;
select min(salario) as "Menor Salário" from CONTRATADOS;
select max(salario) as "Maior Salário" from CONTRATADOS;
select sum(salario) as "Soma de Todos os Salários" from CONTRATADOS;
select count(*) as "Qtd. Total de Funcionarios" from CONTRATADOS;