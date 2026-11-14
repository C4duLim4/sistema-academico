# CRUD de Cursos e Professores

## Implementação

As duas entidades seguem o fluxo Controller → Service → Repository → MySQL.
Os controllers oferecem cadastrar(), listar(), buscarPorId(), atualizar() e excluir().
Os repositories oferecem salvar(), listar(), buscarPorId(), atualizar() e excluir().
Salvar cadastra com INSERT; atualizar usa UPDATE; excluir usa DELETE. O ID vem do
AUTO_INCREMENT e retorna ao objeto por getGeneratedKeys(), sem contador em memória.

Curso e Professor já existiam: foi adicionado somente setId(Long id) a esses modelos,
necessário para preencher os objetos nas consultas e após a inserção. As demais
classes originais, incluindo Aluno, Conexao e Main, e o pom.xml foram preservados.
MainCursosProfessores é uma demonstração separada das quatro operações.

## Validações

- Objetos e campos obrigatórios, nomes com até 150 caracteres.
- Modalidade com até 100 caracteres; e-mail com até 150 e formato básico válido.
- IDs positivos e existência do registro antes de atualizar/excluir.
- E-mail de professor único; na atualização o próprio registro é desconsiderado.
  A restrição UNIQUE do MySQL é a garantia final, inclusive para acessos simultâneos.

As validações exibem mensagens e interrompem a operação, seguindo AlunoService.
Os repositories usam PreparedStatement e try-with-resources para fechar os recursos.
Erros SQL são propagados com a causa, evitando anunciar sucesso após uma falha.

## Como executar

1. Abra a pasta no IntelliJ como projeto Maven. O pom.xml original exige JDK 25.
2. Inicie o MySQL e execute sql/cursos_professores.sql no Workbench ou equivalente.
3. Confira a configuração original em config/Conexao.java: localhost, porta 3307,
   banco sistema_academico, usuário root e senha vazia. Se seu servidor for diferente,
   ajuste esses dados no seu ambiente.
4. Recarregue as dependências Maven e execute MainCursosProfessores.main().
   Para compilar pelo terminal, use `mvn compile` com JDK 25 e Maven instalados.

O exemplo cadastra dois cursos e dois professores, lista os registros, busca pelo ID,
atualiza e exclui o primeiro curso/professor criado na própria execução. O segundo
registro permanece. Ao repetir, cursos podem se repetir, enquanto e-mails de professores
já existentes serão rejeitados. A demonstração não é uma transação única: operações
anteriores permanecem gravadas caso uma operação posterior falhe.

## Roteiro de conferência no MySQL

- Cadastre um curso e professor e confira os IDs gerados.
- Liste e consulte esses IDs; consulte também um ID inexistente (retorna null).
- Atualize modalidade/nome e consulte novamente para conferir a persistência.
- Rejeite nome vazio, modalidade vazia, e-mail inválido e e-mail duplicado.
- Atualize um professor sem mudar seu e-mail (deve ser permitido).
- Tente usar o e-mail de outro professor (deve ser rejeitado).
- Exclua os registros de teste e confirme que buscarPorId retorna null.

## Verificação realizada

Todos os fontes foram compilados com o compilador Java 21 disponível no ambiente.
As validações e o encaminhamento Controller/Service foram testados com repositories
em memória substitutos, cobrindo cadastro, consulta, atualização, exclusão e rejeição
de dados inválidos e e-mails duplicados. Esses substitutos são apenas testes; os
repositories entregues usam JDBC/MySQL. Não foi executado Maven (indisponível),
nem teste integrado com MySQL. O pom.xml continua configurado para Java 25.
