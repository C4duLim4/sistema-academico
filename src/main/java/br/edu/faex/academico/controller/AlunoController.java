package br.edu.faex.academico.controller;

import br.edu.faex.academico.model.Aluno;
import br.edu.faex.academico.service.AlunoService;

import java.util.List;

public class AlunoController {
    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    public void cadastrar(Aluno aluno){
        this.service.cadastrar(aluno);
    }
    public List<Aluno> listar(){
        return this.service.listar();
    }

    public Aluno buscarPorId(long id){
        return service.buscarPorId(id);
    }

    public void excluir(Long id){
        service.excluir(id);
    }

<<<<<<< HEAD
    public void atualizar(Aluno alunoEditado){
        service.atualizar(alunoEditado);
    }


=======
>>>>>>> 4c093b153a6efc575d211b106390c107ba535047
}
