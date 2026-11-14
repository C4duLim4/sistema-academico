package br.edu.faex.academico;

import br.edu.faex.academico.controller.CursoController;
import br.edu.faex.academico.controller.ProfessorController;
import br.edu.faex.academico.model.Curso;
import br.edu.faex.academico.model.Professor;
import br.edu.faex.academico.repository.CursoRepository;
import br.edu.faex.academico.repository.ProfessorRepository;
import br.edu.faex.academico.service.CursoService;
import br.edu.faex.academico.service.ProfessorService;

public class MainCursosProfessores {
    public static void main(String[] args) {
        CursoController cursos = new CursoController(new CursoService(new CursoRepository()));
        ProfessorController professores = new ProfessorController(
                new ProfessorService(new ProfessorRepository()));

        Curso curso1 = new Curso("Análise e Desenvolvimento de Sistemas", "Presencial");
        Curso curso2 = new Curso("Sistemas de Informação", "EAD");
        Professor professor1 = new Professor("Carlos Oliveira", "carlos.oliveira@faex.edu.br");
        Professor professor2 = new Professor("Ana Souza", "ana.souza@faex.edu.br");

        try {
            cursos.cadastrar(curso1);
            cursos.cadastrar(curso2);
            professores.cadastrar(professor1);
            professores.cadastrar(professor2);

            System.out.println("\n===== LISTA DE CURSOS =====");
            for (Curso curso : cursos.listar()) {
                System.out.println("ID: " + curso.getId());
                System.out.println("Nome: " + curso.getNome());
                System.out.println("Modalidade: " + curso.getModalidade());
                System.out.println("--------------------------");
            }
            System.out.println("\n===== LISTA DE PROFESSORES =====");
            for (Professor professor : professores.listar()) {
                System.out.println("ID: " + professor.getId());
                System.out.println("Nome: " + professor.getNome());
                System.out.println("E-mail: " + professor.getEmail());
                System.out.println("--------------------------");
            }

            // Busca, atualização e exclusão somente dos registros criados nesta execução.
            if (curso1.getId() != null) {
                Curso encontrado = cursos.buscarPorId(curso1.getId());
                if (encontrado != null) {
                    encontrado.setModalidade("EAD");
                    cursos.atualizar(encontrado);
                    System.out.println("Modalidade após atualização: "
                            + cursos.buscarPorId(encontrado.getId()).getModalidade());
                    cursos.excluir(encontrado.getId());
                }
            }
            if (professor1.getId() != null) {
                Professor encontrado = professores.buscarPorId(professor1.getId());
                if (encontrado != null) {
                    encontrado.setNome("Carlos Oliveira Silva");
                    professores.atualizar(encontrado);
                    System.out.println("Nome após atualização: "
                            + professores.buscarPorId(encontrado.getId()).getNome());
                    professores.excluir(encontrado.getId());
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Não foi possível concluir a demonstração: " + e.getMessage());
        }
    }
}
