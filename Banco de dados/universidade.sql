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

create table PROFESSOR(
	matricula serial primary key,
	codigo_unidade_academica serial,
	nome varchar(50) not null,
	email varchar(100) not null,
	salario decimal(10,2) not null check(salario >= 0),
	formacao varchar(50) not null,
	data_inicio date not null default current_date,
	foreign key(codigo_unidade_academica) references UNIDADE_ACADEMICA(codigo)
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
	codigo_unidade_academica serial,
	nome varchar(50) not null,
	email varchar(100) not null,
	salario decimal(10,2) not null check(salario >= 0),
	funcao varchar(50) not null,
	data_inicio date not null default current_date,
	foreign key(codigo_unidade_academica) references UNIDADE_ACADEMICA(codigo)
);

create table DEPENDENTE(
	id serial primary key,
	matricula_contratado serial,
	tipo_contratado varchar(11) not null default 'Funcionario',
	nome varchar(50) not null,
	data_nascimento date not null default current_date,
	foreign key(matricula_contratado) references professor(matricula) on delete cascade,
	foreign key(matricula_contratado) references funcionario(matricula) on delete cascade,
	unique(matricula_contratado, tipo_contratado, nome, data_nascimento)
);

-- Views
create view DETALHES_AULA as
	select at.aluno_matricula as matricula_aluno, 
		a.nome as "Aluno",
		p.nome as "Professor",
		t.nome_disciplina as "Disciplina", 
		t.horario as "Horario"
	from ALUNO_TURMA at
		inner join ALUNO a on at.aluno_matricula = a.matricula
		inner join TURMA t on at.turma_numero = t.numero
		left join PROFESSOR p on t.matricula_professor = p.matricula;

create view CONTRATADOS as
	select p.matricula as matricula, 
		'Professor' as tipo_contratado,
		p.nome as "Nome", 
		p.formacao as formacao_professor,
		ua.codigo as codigo_unidade,
		ua.nome as nome_unidade
	from PROFESSOR p
	inner join UNIDADE_ACADEMICA ua on p.codigo_unidade_academica = ua.codigo
	union all
	select f.matricula as matricula,
		'Funcionario' as tipo_contratado,
		f.nome as "Nome",
		f.funcao,
		ua.codigo as codigo_unidade,
		ua.nome as nome_unidade
	from FUNCIONARIO f
	inner join UNIDADE_ACADEMICA ua on f.codigo_unidade_academica = ua.codigo;

create view DETALHES_DEPENDENTES as
	select p.matricula as matricula,
		p.nome as "Contratado",
		d.tipo_contratado as "Tipo Contratado",
		d.nome as "Dependente",
		d.data_nascimento as "Data Nascimento Dependente"
	from PROFESSOR p
	inner join DEPENDENTE d on p.matricula = d.matricula_contratado
	where d.tipo_contratado = 'Professor'
	union all
	select f.matricula as matricula,
		f.nome as "Contratado",
		d.tipo_contratado as "Tipo Contratado",
		d.nome as "Dependente",
		d.data_nascimento as "Data Nascimento Dependente"
	from FUNCIONARIO f
	inner join DEPENDENTE d on f.matricula = d.matricula_contratado
	where d.tipo_contratado = 'Funcionario';

-- Inserts
insert into ALUNO 
(matricula, nome, email, mensalidade) 
values
    (1, 'João Silva', 'joao@example.com', 500.00),
    (2, 'Maria Santos', 'maria@example.com', 550.00),
    (3, 'Carlos Oliveira', 'carlos@example.com', 600.00);

insert into UNIDADE_ACADEMICA 
(codigo, nome) 
values
    (1, 'Departamento de Informática'),
    (2, 'Departamento de Matemática'),
    (3, 'Departamento de Ciências Sociais');

insert into PROFESSOR 
(matricula, nome, email, salario, formacao, codigo_unidade_academica, data_inicio) 
values
    (1, 'Ana Pereira', 'ana@example.com', 3500.00, 'Doutorado em Informática', 3, '2022-05-09'),
    (2, 'Pedro Santos', 'pedro@example.com', 3800.00, 'Mestrado em Matemática', 2, '2023-04-11'),
    (3, 'Mariana Lima', 'mariana@example.com', 3200.00, 'Doutorado em Sociologia', 1, '2024-01-01');

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
(matricula, nome, email, salario, funcao, codigo_unidade_academica, data_inicio) 
values
    (1, 'José Silva', 'jose@example.com', 3000.00, 'Secretário', 1, '2020-08-08'),
    (2, 'Ana Oliveira', 'anao@example.com', 2800.00, 'Assistente Administrativo', 2, '2021-05-02'),
    (3, 'Carlos Santos', 'carloss@example.com', 3200.00, 'Auxiliar de Limpeza', 3, '2022-07-01');

insert into DEPENDENTE 
(id, matricula_contratado, tipo_contratado, nome, data_nascimento) 
values
    (1, 1, 'Professor', 'Pedro Silva', '2010-05-15'),
    (2, 3, 'Professor', 'Mariana Oliveira', '2012-08-20'),
    (3, 2, 'Professor', 'João Santos', '2008-03-10');

insert into DEPENDENTE 
(id, matricula_contratado, tipo_contratado, nome, data_nascimento) 
values
    (4, 1, 'Funcionario', 'Maria Silva', '2005-12-10'),
    (5, 2, 'Funcionario', 'Pedro Oliveira', '2011-06-25'),
    (6, 3, 'Funcionario', 'Ana Santos', '2009-09-03');

-- Selects
select * from ALUNO;
select * from TURMA;
select * from ALUNO_TURMA;
select * from PROFESSOR;
select * from DETALHES_AULA;
select * from UNIDADE_ACADEMICA;
select * from FUNCIONARIO;
select * from DEPENDENTE order by id;
select * from CONTRATADOS order by matricula;
select * from CONTRATADOS where tipo_contratado='Professor';
select * from CONTRATADOS where tipo_contratado='Funcionario';
select * from DETALHES_DEPENDENTES order by matricula;
select * from DETALHES_DEPENDENTES where "Tipo Contratado"='Professor';
select * from DETALHES_DEPENDENTES where "Tipo Contratado"='Funcionario';