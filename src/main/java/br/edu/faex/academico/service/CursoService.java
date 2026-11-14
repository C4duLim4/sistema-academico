package br.edu.faex.academico.service;

import br.edu.faex.academico.model.Curso;
import br.edu.faex.academico.repository.CursoRepository;
import java.util.List;

public class CursoService {
    private CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Curso curso) {
        if (!validar(curso, null)) {
            return;
        }
        repository.salvar(curso);
        System.out.println("Curso cadastrado com sucesso.");
    }

    public List<Curso> listar() {
        return repository.listar();
    }

    public Curso buscarPorId(Long id) {
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
            System.out.println("Curso não encontrado.");
            return;
        }
        repository.excluir(id);
        System.out.println("Curso excluído com sucesso.");
    }

    public void atualizar(Curso cursoEditado) {
        if (cursoEditado == null || !validarId(cursoEditado.getId())) {
            System.out.println("Informe um curso com ID válido.");
            return;
        }
        if (repository.buscarPorId(cursoEditado.getId()) == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        if (!validar(cursoEditado, cursoEditado.getId())) {
            return;
        }
        repository.atualizar(cursoEditado);
        System.out.println("Curso atualizado com sucesso.");
    }

    private boolean validarId(Long id) {
        if (id == null || id <= 0) {
            System.out.println("O ID deve ser positivo.");
            return false;
        }
        return true;
    }

    private boolean validar(Curso curso, Long idAtual) {
        if (curso == null) {
            System.out.println("Informe os dados do curso.");
            return false;
        }
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            System.out.println("O nome é obrigatório.");
            return false;
        }
        if (curso.getNome().length() > 150) {
            System.out.println("O nome deve ter no máximo 150 caracteres.");
            return false;
        }
        if (curso.getModalidade() == null || curso.getModalidade().isBlank()) {
            System.out.println("Modalidade é obrigatório(a).");
            return false;
        }
        if (curso.getModalidade().length() > 100) {
            System.out.println("Modalidade excede o limite de caracteres.");
            return false;
        }
        return true;
    }
}
