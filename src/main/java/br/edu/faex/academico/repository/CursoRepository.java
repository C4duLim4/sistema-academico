package br.edu.faex.academico.repository;

import br.edu.faex.academico.config.Conexao;
import br.edu.faex.academico.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoRepository {
    public void salvar(Curso curso) {
        String sql = "INSERT INTO cursos (nome, modalidade) VALUES (?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, curso.getNome());
            comando.setString(2, curso.getModalidade());
            comando.executeUpdate();
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    curso.setId(resultado.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar curso: " + e.getMessage(), e);
        }
    }

    public List<Curso> listar() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id, nome, modalidade FROM cursos ORDER BY id";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                cursos.add(mapear(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos: " + e.getMessage(), e);
        }
        return cursos;
    }

    public Curso buscarPorId(Long id) {
        String sql = "SELECT id, nome, modalidade FROM cursos WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return mapear(resultado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage(), e);
        }
        return null;
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, id);
            if (comando.executeUpdate() == 0) {
                throw new IllegalStateException("Curso não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir curso: " + e.getMessage(), e);
        }
    }

    public void atualizar(Curso curso) {
        String sql = "UPDATE cursos SET nome = ?, modalidade = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, curso.getNome());
            comando.setString(2, curso.getModalidade());
            comando.setLong(3, curso.getId());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso: " + e.getMessage(), e);
        }
    }

    private Curso mapear(ResultSet resultado) throws SQLException {
        Curso curso = new Curso();
        curso.setId(resultado.getLong("id"));
        curso.setNome(resultado.getString("nome"));
        curso.setModalidade(resultado.getString("modalidade"));
        return curso;
    }
}
