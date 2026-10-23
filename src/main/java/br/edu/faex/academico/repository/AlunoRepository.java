package br.edu.faex.academico.repository;

import br.edu.faex.academico.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno){
        alunos.add(aluno);
    }

    public List<Aluno> listar(){
        return alunos;
    }

    public Aluno buscarPorId(Long id){
        for (Aluno aluno : alunos){
            if (aluno.getId().equals(id)){
                return aluno;
            }
        }
        return null;
    }
<<<<<<< HEAD

    public void excluir(Long id){
        for (Aluno aluno : alunos){
=======
    public void excluir(Long id){
        for (Aluno aluno: alunos){
>>>>>>> 4c093b153a6efc575d211b106390c107ba535047
            if(aluno.getId().equals(id)){
                alunos.remove(aluno);
                return;
            }
        }
    }

<<<<<<< HEAD
    public void atualizar(Aluno alunoEditado){
        for (Aluno aluno : alunos){
            if(aluno.getId().equals(alunoEditado.getId())){
                    aluno.setNome(alunoEditado.getNome());
                    aluno.setEmail(alunoEditado.getEmail());
                return;
            }
        }
    }


=======
>>>>>>> 4c093b153a6efc575d211b106390c107ba535047

}
