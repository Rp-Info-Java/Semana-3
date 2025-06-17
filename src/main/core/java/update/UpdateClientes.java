package update;

import insert.ConsultaProdutos;
import utils.FunctionsAux;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class UpdateClientes {
    Scanner teclado = new Scanner(System.in);
    String palavra = "";
    private final String queryListagem = "SELECT * FROM clientes ORDER BY clie_id";
    private static final String queryListagemE = "SELECT * FROM enderecos ORDER BY ende_id";
    int opcao = 0;

    public void atualizacaoClie(Connection conn, PreparedStatement stmtP) throws SQLException {
        do {
            System.out.println("\n--- Atualização de dados do cliente ---");
            System.out.println("""
                    Opções de atualização:
                    1- Adicionar um novo cliente.
                    2- Atualizar o email do cliente.
                    3- Atualizar telefone do cliente.
                    4- Atualizar o endereço(ID) do cliente.
                    5- Listar todos os clientes cadastrados.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada (1-5 ou 0 para sair): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando atualização!\n");
            }else if(opcao == 1){
                adicionarCliente(conn, stmtP);
            }else if(opcao == 2){
                atualizarEmail(conn);
            }else if(opcao == 3){
                atualizarTelefone(conn);
            }else if(opcao == 4){
                atualizarEnderecoID(conn);
            }else if(opcao == 5){
                listAll(conn, queryListagem);
            }else{
                System.out.println("A opção digitada é inválida. Digite uma opção válida!");
            }
        }while (opcao != 0);
    }

    public static void listAll(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder sb = new StringBuilder();

            System.out.println("---Listagem dos clientes cadastrados---");

            // --- Cabeçalho estilizado (usando a mesma lógica de produtos) ---
            sb.append("┌").append("─".repeat(7)).append("┬").append("─".repeat(22)) // ID (7), NOME (22)
                    .append("┬").append("─".repeat(27)).append("┬").append("─".repeat(16)) // EMAIL (27), CPF (16)
                    .append("┬").append("─".repeat(16)).append("┬").append("─".repeat(20)) // TELEFONE (16), DATA CADASTRO (20)
                    .append("┬").append("─".repeat(14)).append("┬").append("─".repeat(10)) // ID_ENDEREÇO (14), STATUS (10)
                    .append("┐\n");

            sb.append(String.format("│ %-5s │ %-20s │ %-25s │ %-14s │ %-14s │ %-18s │ %-12s │ %-8s │%n",
                    "ID", "NOME", "EMAIL", "CPF", "TELEFONE", "DATA CADASTRO", "ID_ENDEREÇO", "STATUS"));

            sb.append("├").append("─".repeat(7)).append("┼").append("─".repeat(22))
                    .append("┼").append("─".repeat(27)).append("┼").append("─".repeat(16))
                    .append("┼").append("─".repeat(16)).append("┼").append("─".repeat(20))
                    .append("┼").append("─".repeat(14)).append("┼").append("─".repeat(10))
                    .append("┤\n");

            System.out.print(sb.toString());

            // --- Dados da Tabela ---
            while (rs.next()) {
                String clie_nome = rs.getString("clie_nome");
                String clie_email = rs.getString("clie_email");
                String clie_cpf = rs.getString("clie_cpf");
                String clie_telefone = rs.getString("clie_telefone");
                String clie_status = rs.getString("clie_status"); // Puxa o status do banco de dados

                // Ajusta tamanho das strings
                clie_nome = ajustarTamanho(clie_nome, 20);
                clie_email = ajustarTamanho(clie_email, 25);
                clie_cpf = ajustarTamanho(clie_cpf, 14);
                clie_telefone = ajustarTamanho(clie_telefone, 14);
                clie_status = ajustarTamanho(clie_status, 8); // Ajusta para a largura da coluna STATUS

                System.out.printf("│ %-5d │ %-20s │ %-25s │ %-14s │ %-14s │ %-18s │ %-12d │ %-8s │%n",
                        rs.getInt("clie_id"),
                        clie_nome,
                        clie_email,
                        clie_cpf,
                        clie_telefone,
                        rs.getDate("clie_data_cadastro"),
                        rs.getInt("clie_ende_id"),
                        clie_status); // Imprime o status do cliente
            }

            // --- Rodapé da Tabela ---
            System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(22) +
                    "┴" + "─".repeat(27) + "┴" + "─".repeat(16) +
                    "┴" + "─".repeat(16) + "┴" + "─".repeat(20) +
                    "┴" + "─".repeat(14) + "┴" + "─".repeat(10) +
                    "┘");

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    // Método auxiliar para ajustar o tamanho das strings e tratar nulos
    private static String ajustarTamanho(String text, int length) {
        if (text == null) {
            return String.format("%-" + length + "s", "");
        }
        if (text.length() > length) {
            return text.substring(0, length);
        } else {
            return String.format("%-" + length + "s", text);
        }
    }

    public static boolean getEndeID(Connection conn, Integer id){
        Integer endeID = null;
        boolean endeIgual = false;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryListagemE)) {
            while (rs.next()) {
                endeID = rs.getInt("ende_id");

                if(endeID.equals(id)){
                    endeIgual = true;
                    return endeIgual;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao executar a consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return endeIgual;
    }
    public static void listEnderecos(Connection conn, String queryListagemE) throws SQLException{
        UpdateEnderecos enderecos = new UpdateEnderecos();
        System.out.println();
        enderecos.listAll(conn, queryListagemE);
    }
    public void adicionarCliente(Connection conn, PreparedStatement stmt) throws SQLException{
        int auxEndeID, controle = 0;
        teclado.nextLine();
        listAll(conn, queryListagem);
        listEnderecos(conn, queryListagemE);
        LocalDateTime dataCadastro = LocalDateTime.now();

        System.out.println("\n--Adicionando cliente novo ao banco de dados--");
        System.out.println("Digite o id do endereço que terá um novo cliente adicionado: ");
        palavra = teclado.nextLine();
        auxEndeID = Integer.parseInt(palavra);

        if(getEndeID(conn, auxEndeID)){
            try(Statement stmt3 = conn.createStatement();
                ResultSet rs = stmt3.executeQuery(queryListagem)){

                System.out.println("Digite o número do CPF do cliente a ser cadastrado: ");
                palavra = teclado.nextLine();

                while(rs.next()){
                    String cpf = rs.getString("clie_cpf");
                    if(cpf.equals(palavra)){
                        controle++;
                    }
                }
            }catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());
            }
            if(controle == 0){
                stmt.setInt(1, FunctionsAux.getSequenceValue(conn, "seq_clie_id"));
                stmt.setString(4, palavra); //adiciona o CPF
                stmt.setInt(7, auxEndeID); //adiciona o ID do endereço

                System.out.println("Digite o nome do cliente: ");
                palavra = teclado.nextLine();
                stmt.setString(2, palavra);

                System.out.println("Digite o e-mail do cliente: ");
                palavra = teclado.nextLine();
                stmt.setString(3, palavra);

                System.out.println("Digite o telefone do cliente: ");
                palavra = teclado.nextLine();
                stmt.setString(5, palavra);

                System.out.println("Inserindo data atual do cadastro do cliente...");
                stmt.setObject(6, dataCadastro);
                stmt.setString(8, "S");
                stmt.executeUpdate();

                System.out.println("Cliente cadastrado com sucesso!");
            }else{
                System.out.println("O CPF informado já existe no banco de dados");
            }
        }else{
            System.out.println("O endereço não foi encontrado. Digite um ID válido.");
        }
    }
    public void atualizarEmail(Connection conn) throws SQLException{
        String sql = "UPDATE clientes SET clie_email = ? WHERE clie_id = ?";
        int controle = 0;

        System.out.println("\n--- Listagem de clientes ---\n");
        listAll(conn, queryListagem);

            PreparedStatement stmt = conn.prepareStatement(sql);

            teclado.nextLine(); //limpando buffer
            System.out.println("-Atualização de email por ID do cliente-");

            try(Statement stmt1 = conn.createStatement();
                ResultSet rs = stmt1.executeQuery(queryListagem)){

                System.out.println("Escreva o id do cliente: ");
                palavra = teclado.nextLine();

                while(rs.next()){
                    Integer clienteID = rs.getInt("clie_id");
                    if(clienteID.equals(Integer.parseInt(palavra))){
                        controle++;
                    }
                }
            }catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());
            }
            if(controle == 0){
                System.out.println("Cliente não encontrado no banco de dados.");
            }else{
                stmt.setInt(2, Integer.parseInt(palavra));

                System.out.println("Escreva o NOVO email do cliente: ");
                palavra = teclado.nextLine();
                stmt.setString(1, palavra);
            }
            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Email atualizado com sucesso!\n");
            }else{
                System.out.println("Cliente não encontrado!\n");
            }
    }
    public void atualizarTelefone (Connection conn) throws SQLException{
        String sql = "UPDATE clientes SET clie_telefone = ? WHERE clie_id = ?";
        int controle = 0;

        System.out.println("\n--- Listagem de clientes ---\n");
        listAll(conn, queryListagem);

        PreparedStatement stmt = conn.prepareStatement(sql);

        teclado.nextLine(); //limpando buffer
        System.out.println("-Atualização de telefone por ID do cliente-");

        try(Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(queryListagem)){

            System.out.println("Escreva o id do cliente: ");
            palavra = teclado.nextLine();

            while(rs.next()){
                Integer clienteID = rs.getInt("clie_id");
                if(clienteID.equals(Integer.parseInt(palavra))){
                    controle++;
                }
            }
        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        if(controle == 0){
            System.out.println("Cliente não encontrado no banco de dados.");
        }else{
            stmt.setInt(2, Integer.parseInt(palavra));

            System.out.println("Escreva o NOVO telefone do cliente (sem parênteses, caracteres especiais ou espaços): ");
            palavra = teclado.nextLine();
            stmt.setString(1, palavra);
        }
        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Telefone atualizado com sucesso!\n");
        }else{
            System.out.println("Cliente não encontrado!\n");
        }
    }
    public void atualizarEnderecoID(Connection conn) throws SQLException{
        String sql = "UPDATE clientes SET clie_ende_id = ? WHERE clie_id = ?";
        int controle = 0;

        System.out.println("\n--- Listagem de clientes ---\n");
        listAll(conn, queryListagem);

        PreparedStatement stmt = conn.prepareStatement(sql);

        teclado.nextLine(); //limpando buffer
        System.out.println("-Atualização de endereço (ID) por ID do cliente-");

        try(Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(queryListagem)){

            System.out.println("Escreva o id do cliente: ");
            palavra = teclado.nextLine();

            while(rs.next()){
                Integer clienteID = rs.getInt("clie_id");
                if(clienteID.equals(Integer.parseInt(palavra))){
                    controle++;
                }
            }
        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        if(controle == 0){
            System.out.println("Cliente não encontrado no banco de dados.");
        }else{
            stmt.setInt(2, Integer.parseInt(palavra));

            listEnderecos(conn, queryListagemE);

            System.out.println("\nO endereço escolhido deve estar cadastrado no banco de dados." +
                    " Em caso de não existir o endereço, cadastrá-lo pelo menu.");
            System.out.println("Escreva o NOVO ID de endereço do cliente: ");
            palavra = teclado.nextLine();
            stmt.setInt(1, Integer.parseInt(palavra));
        }
        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Endereço atualizado com sucesso!\n");
        }else{
            System.out.println("Cliente não encontrado!\n");
        }
    }
}
