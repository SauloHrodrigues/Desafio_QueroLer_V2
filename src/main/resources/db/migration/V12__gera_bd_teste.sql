-- =========================
-- AUTORES
-- =========================
INSERT INTO tb_autores (nome) VALUES
                                  ('Robert C. Martin'),
                                  ('Martin Fowler'),
                                  ('Erich Gamma'),
                                  ('Richard Helm'),
                                  ('Ralph Johnson'),
                                  ('John Vlissides'),
                                  ('Joshua Bloch'),
                                  ('Andrew Hunt'),
                                  ('David Thomas'),
                                  ('Kent Beck'),
                                  ('Eric Evans'),
                                  ('Brian Goetz'),
                                  ('James Gosling'),
                                  ('George Orwell'),
                                  ('J.R.R. Tolkien'),
                                  ('J.K. Rowling'),
                                  ('Fiódor Dostoiévski'),
                                  ('Machado de Assis'),
                                  ('Clarice Lispector'),
                                  ('Yuval Noah Harari');

-- =========================
-- LIVROS
-- =========================
INSERT INTO tb_livros (
    titulo, isbn, editora, ano_de_publicacao,
    numero_de_paginas, idioma, sinopse,
    data_de_cadastro, quantidade_de_uso
) VALUES
      ('Clean Code', '9780132350884', 'Prentice Hall', '2008', 464, 'PORTUGUES', 'Boas práticas de programação.', NOW(), 10),
      ('Refactoring', '9780201485677', 'Addison-Wesley', '1999', 448, 'PORTUGUES', 'Melhoria de código existente.', NOW(), 15),
      ('Design Patterns', '9780201633610', 'Addison-Wesley', '1994', 395, 'PORTUGUES', 'Padrões de projeto clássicos.', NOW(), 20),
      ('Effective Java', '9780134685991', 'Addison-Wesley', '2018', 416, 'PORTUGUES', 'Boas práticas em Java.', NOW(), 20),
      ('The Pragmatic Programmer', '9780201616224', 'Addison-Wesley', '1999', 352, 'PORTUGUES', 'Princípios de desenvolvimento.', NOW(), 15),
      ('Test Driven Development', '9780321146533', 'Addison-Wesley', '2002', 240, 'PORTUGUES', 'Desenvolvimento orientado a testes.', NOW(), 7),
      ('Domain-Driven Design', '9780321125217', 'Addison-Wesley', '2003', 560, 'PORTUGUES', 'Modelagem de domínio.', NOW(), 7),
      ('Java Concurrency in Practice', '9780321349606', 'Addison-Wesley', '2006', 432, 'PORTUGUES', 'Concorrência em Java.', NOW(), 5),
      ('1984', '9780451524935', 'Signet Classics', '1949', 328, 'PORTUGUES', 'Distopia política.', NOW(), 3),
      ('A Revolução dos Bichos', '9780451526342', 'Companhia das Letras', '1945', 152, 'PORTUGUES', 'Sátira política.', NOW(), 3),
      ('O Senhor dos Anéis', '9780618640157', 'HarperCollins', '1954', 1216, 'PORTUGUES', 'Fantasia épica.', NOW(), 20),
      ('O Hobbit', '9780261102217', 'HarperCollins', '1937', 310, 'PORTUGUES', 'Aventura fantástica.', NOW(), 10),
      ('Harry Potter e a Pedra Filosofal', '9780747532699', 'Rocco', '1997', 223, 'PORTUGUES', 'Fantasia juvenil.', NOW(), 15),
      ('Harry Potter e a Câmara Secreta', '9780747538493', 'Rocco', '1998', 251, 'PORTUGUES', 'Continuação da saga.', NOW(), 15),
      ('Crime e Castigo', '9780143058144', 'Penguin', '1866', 671, 'PORTUGUES', 'Romance psicológico.', NOW(), 5),
      ('O Idiota', '9780140447927', 'Penguin', '1869', 656, 'PORTUGUES', 'Clássico russo.', NOW(), 5),
      ('Dom Casmurro', '9788520930842', 'Ática', '1899', 256, 'PORTUGUES', 'Clássico brasileiro.', NOW(), 7),
      ('Memórias Póstumas de Brás Cubas', '9788520930859', 'Ática', '1881', 208, 'PORTUGUES', 'Romance inovador.', NOW(), 7),
      ('A Hora da Estrela', '9788520923264', 'Rocco', '1977', 96, 'PORTUGUES', 'Obra marcante brasileira.', NOW(), 0),
      ('Laços de Família', '9788532506354', 'Rocco', '1960', 160, 'PORTUGUES', 'Contos.', NOW(), 0),
      ('Sapiens', '9780062316097', 'Harper', '2011', 498, 'PORTUGUES', 'História da humanidade.', NOW(), 10),
      ('Homo Deus', '9780062464316', 'Harper', '2015', 450, 'PORTUGUES', 'Futuro da humanidade.', NOW(), 10),
      ('Clean Architecture', '9780134494166', 'Prentice Hall', '2017', 432, 'PORTUGUES', 'Arquitetura de software.', NOW(), 20),
      ('Agile Software Development', '9780135974445', 'Prentice Hall', '2002', 552, 'PORTUGUES', 'Metodologias ágeis.', NOW(), 3),
      ('Patterns of Enterprise Application Architecture', '9780321127426', 'Addison-Wesley', '2002', 560, 'PORTUGUES', 'Arquitetura corporativa.', NOW(), 15),
      ('Working Effectively with Legacy Code', '9780131177055', 'Prentice Hall', '2004', 456, 'PORTUGUES', 'Código legado.', NOW(), 5),
      ('Head First Design Patterns', '9780596007126', 'OReilly', '2004', 694, 'PORTUGUES', 'Padrões de projeto.', NOW(), 10),
      ('Effective Java 2nd Edition', '9780321356680', 'Addison-Wesley', '2008', 384, 'PORTUGUES', 'Java avançado.', NOW(), 10),
      ('The Clean Coder', '9780137081073', 'Prentice Hall', '2011', 256, 'PORTUGUES', 'Conduta profissional.', NOW(), 3),
      ('Scrum: The Art of Doing Twice the Work', '9780385346450', 'Currency', '2014', 256, 'PORTUGUES', 'Scrum explicado.', NOW(), 3);

-- =========================
-- RELACIONAMENTO
-- =========================
INSERT INTO tb_livro_autor (livro_id, autor_id)
SELECT l.id, a.id
FROM tb_livros l, tb_autores a
WHERE
    (l.titulo = 'Clean Code' AND a.nome = 'Robert C. Martin')
   OR (l.titulo = 'Clean Architecture' AND a.nome = 'Robert C. Martin')
   OR (l.titulo = 'The Clean Coder' AND a.nome = 'Robert C. Martin')
   OR (l.titulo = 'Refactoring' AND a.nome = 'Martin Fowler')
   OR (l.titulo = 'Patterns of Enterprise Application Architecture' AND a.nome = 'Martin Fowler')
   OR (l.titulo = 'Design Patterns' AND a.nome IN ('Erich Gamma','Richard Helm','Ralph Johnson','John Vlissides'))
   OR (l.titulo = 'Effective Java' AND a.nome = 'Joshua Bloch')
   OR (l.titulo = 'Effective Java 2nd Edition' AND a.nome = 'Joshua Bloch')
   OR (l.titulo = 'The Pragmatic Programmer' AND a.nome IN ('Andrew Hunt','David Thomas'))
   OR (l.titulo = 'Test Driven Development' AND a.nome = 'Kent Beck')
   OR (l.titulo = 'Agile Software Development' AND a.nome = 'Kent Beck')
   OR (l.titulo = 'Scrum: The Art of Doing Twice the Work' AND a.nome = 'Kent Beck')
   OR (l.titulo = 'Domain-Driven Design' AND a.nome = 'Eric Evans')
  OR (l.titulo = 'Java Concurrency in Practice' AND a.nome IN ('Brian Goetz','James Gosling'))
   OR (l.titulo = '1984' AND a.nome = 'George Orwell')
   OR (l.titulo = 'A Revolução dos Bichos' AND a.nome = 'George Orwell')
   OR (l.titulo = 'O Senhor dos Anéis' AND a.nome = 'J.R.R. Tolkien')
   OR (l.titulo = 'O Hobbit' AND a.nome = 'J.R.R. Tolkien')
   OR (l.titulo LIKE 'Harry Potter%' AND a.nome = 'J.K. Rowling')
   OR (l.titulo IN ('Crime e Castigo','O Idiota') AND a.nome = 'Fiódor Dostoiévski')
   OR (l.titulo IN ('Dom Casmurro','Memórias Póstumas de Brás Cubas') AND a.nome = 'Machado de Assis')
   OR (l.titulo IN ('A Hora da Estrela','Laços de Família') AND a.nome = 'Clarice Lispector')
   OR (l.titulo IN ('Sapiens','Homo Deus') AND a.nome = 'Yuval Noah Harari')
   OR (l.titulo = 'Working Effectively with Legacy Code' AND a.nome = 'Robert C. Martin')
   OR (l.titulo = 'Head First Design Patterns' AND a.nome = 'Erich Gamma');