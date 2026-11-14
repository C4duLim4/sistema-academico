package br.edu.faex.academico.repository;

import br.edu.faex.academico.config.Conexao;
import br.edu.faex.academico.model.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {
    public void salvar(Professor professor) {
        String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, professor.getNome());
            comando.setString(2, professor.getEmail());
            comando.executeUpdate();
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    professor.setId(resultado.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar professor: " + e.getMessage(), e);
        }
    }

    public List<Professor> listar() {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM professores ORDER BY id";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                professores.add(mapear(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage(), e);
        }
        return professores;
    }

    public Professor buscarPorId(Long id) {
        String sql = "SELECT id, nome, email FROM professores WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return mapear(resultado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor: " + e.getMessage(), e);
        }
        return null;
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM professores WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, id);
            if (comando.executeUpdate() == 0) {
                throw new IllegalStateException("Professor não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir professor: " + e.getMessage(), e);
        }
    }

    public void atualizar(Professor professor) {
        String sql = "UPDATE professores SET nome = ?, email = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, professor.getNome());
            comando.setString(2, professor.getEmail());
            comando.setLong(3, professor.getId());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    private Professor mapear(ResultSet resultado) throws SQLException {
        Professor professor = new Professor();
        professor.setId(resultado.getLong("id"));
        professor.setNome(resultado.getString("nome"));
        professor.setEmail(resultado.getString("email"));
        return professor;
    }
}
