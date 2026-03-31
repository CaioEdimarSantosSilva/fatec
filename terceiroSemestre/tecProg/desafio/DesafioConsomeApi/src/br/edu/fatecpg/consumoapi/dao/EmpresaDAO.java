package br.edu.fatecpg.consumoapi.dao;

import br.edu.fatecpg.consumoapi.db.DB;
import br.edu.fatecpg.consumoapi.model.Empresa;
import br.edu.fatecpg.consumoapi.model.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    public void inserir(Empresa empresa) throws SQLException {

        String sqlEmpresa = """
                INSERT INTO empresa (cnpj, razao_social, nome_fantasia, logradouro)
                VALUES (?, ?, ?, ?)
                """;

        String sqlSocio = """
                INSERT INTO socio (nome_socio, cnpj_cpf_do_socio, qualificacao_socio, empresa_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DB.connection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmtEmpresa =
                         conn.prepareStatement(sqlEmpresa, Statement.RETURN_GENERATED_KEYS)) {

                stmtEmpresa.setString(1, empresa.getCnpj());
                stmtEmpresa.setString(2, empresa.getRazao_social());
                stmtEmpresa.setString(3, empresa.getNome_fantasia());
                stmtEmpresa.setString(4, empresa.getLogradouro());
                stmtEmpresa.executeUpdate();

                int empresaId;
                try (ResultSet keys = stmtEmpresa.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Falha ao recuperar o ID gerado para a empresa.");
                    }
                    empresaId = keys.getInt(1);
                }

                try (PreparedStatement stmtSocio = conn.prepareStatement(sqlSocio)) {
                    for (Socio socio : empresa.getQsa()) {
                        String docLimpo = socio.getCnpj_cpf_do_socio()
                                .replaceAll("[^A-Z0-9]", "");

                        stmtSocio.setString(1, socio.getNome_socio());
                        stmtSocio.setString(2, docLimpo);
                        stmtSocio.setString(3, socio.getQualificacao_socio());
                        stmtSocio.setInt(4, empresaId);
                        stmtSocio.executeUpdate();
                    }
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean excluir(String cnpj) throws SQLException {
        String sql = "DELETE FROM empresa WHERE cnpj = ?";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public List<Empresa> listarTodas() throws SQLException {

        String sqlEmpresas = "SELECT id, cnpj, razao_social, nome_fantasia, logradouro FROM empresa ORDER BY razao_social";
        String sqlSocios   = "SELECT nome_socio, cnpj_cpf_do_socio, qualificacao_socio FROM socio WHERE empresa_id = ?";

        List<Empresa> lista = new ArrayList<>();

        try (Connection conn = DB.connection();
             PreparedStatement stmtEmpresa = conn.prepareStatement(sqlEmpresas);
             ResultSet rsEmpresas = stmtEmpresa.executeQuery()) {

            try (PreparedStatement stmtSocio = conn.prepareStatement(sqlSocios)) {

                while (rsEmpresas.next()) {
                    Empresa e = new Empresa(
                            rsEmpresas.getString("cnpj"),
                            rsEmpresas.getString("razao_social"),
                            rsEmpresas.getString("nome_fantasia"),
                            rsEmpresas.getString("logradouro")
                    );


                    stmtSocio.setInt(1, rsEmpresas.getInt("id"));
                    try (ResultSet rsSocios = stmtSocio.executeQuery()) {
                        while (rsSocios.next()) {
                            e.getQsa().add(new Socio(
                                    rsSocios.getString("nome_socio"),
                                    rsSocios.getString("cnpj_cpf_do_socio"),
                                    rsSocios.getString("qualificacao_socio")
                            ));
                        }
                    }

                    lista.add(e);
                }
            }
        }

        return lista;
    }
}