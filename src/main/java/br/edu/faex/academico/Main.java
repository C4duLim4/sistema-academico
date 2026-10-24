package br.edu.faex.academico;


import br.edu.faex.academico.config.Conexao;

import java.sql.Connection;

public class Main {
    static void main() {

        try (Connection con = Conexao.conectar()) {
            System.out.println("Conectado");
        } catch (Exception e) {
            System.out.println("Erro ao conectar");
            System.out.println(e.getMessage());
        }


    }
}