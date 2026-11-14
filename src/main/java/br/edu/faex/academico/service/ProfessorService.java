package br.edu.faex.academico.service;

import br.edu.faex.academico.model.Professor;
import br.edu.faex.academico.repository.ProfessorRepository;
import java.util.List;

public class ProfessorService {
    private ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Professor professor) {
        if (!validar(professor, null)) {
            return;
        }
        repository.salvar(professor);
        System.out.println("Professor cadastrado com sucesso.");
    }

    public List<Professor> listar() {
        return repository.listar();
    }

    public Professor buscarPorId(Long id) {
        if (!validarId(id)) {
            return null;
        }
        return repository.buscarPorId(id);
    }

    public void excluir(Long id) {
        if (!validarId(id)) {
            return;
        }
        if (repository.buscarPorId(id) == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        repository.excluir(id);
        System.out.println("Professor excluído com sucesso.");
    }

    public void atualizar(Professor professorEditado) {
        if (professorEditado == null || !validarId(professorEditado.getId())) {
            System.out.println("Informe um professor com ID válido.");
            return;
        }
        if (repository.buscarPorId(professorEditado.getId()) == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        if (!validar(professorEditado, professorEditado.getId())) {
            return;
        }
        repository.atualizar(professorEditado);
        System.out.println("Professor atualizado com sucesso.");
    }

    private boolean validarId(Long id) {
        if (id == null || id <= 0) {
            System.out.println("O ID deve ser positivo.");
            return false;
        }
        return true;
    }

    private boolean validar(Professor professor, Long idAtual) {
        if (professor == null) {
            System.out.println("Informe os dados do professor.");
            return false;
        }
        if (professor.getNome() == null || professor.getNome().isBlank()) {
            System.out.println("O nome é obrigatório.");
            return false;
        }
        if (professor.getNome().length() > 150) {
            System.out.println("O nome deve ter no máximo 150 caracteres.");
            return false;
        }
        if (professor.getEmail() == null || professor.getEmail().isBlank()) {
            System.out.println("E-mail é obrigatório(a).");
            return false;
        }
        if (professor.getEmail().length() > 150) {
            System.out.println("E-mail excede o limite de caracteres.");
            return false;
        }
        if (!professor.getEmail().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            System.out.println("E-mail inválido.");
            return false;
        }
        if (repository.listar().stream().anyMatch(p ->
                p.getEmail().equalsIgnoreCase(professor.getEmail())
                && !p.getId().equals(idAtual))) {
            System.out.println("E-mail já cadastrado.");
            return false;
        }
        return true;
    }
}
