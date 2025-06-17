package update;

import utils.FunctionsAux;

import java.sql.*;
import java.util.Scanner;

public class UpdateEnderecos {
    //metodo para att rua
    //att numero
    //att complemento
    //att bairro
    Scanner teclado = new Scanner(System.in);
    String palavra = "";
    private final String queryListagem = "SELECT * FROM enderecos ORDER BY ende_id";
    private static final String queryListagemC = "SELECT * FROM cidades ORDER BY cida_id";
    int opcao = 0;

    public void atualizacaoEnde(Connection conn, PreparedStatement stmtP) throws SQLException {
        do {
            System.out.println("\n--- Atualização de elementos do endereço ---");
            System.out.println("""
                    Opções de atualização:
                    1- Adicionar um novo endereço.
                    2- Atualizar rua do endereço.
                    3- Atualizar número do endereço.
                    4- Atualizar complemento do endereço.
                    5- Atualizar bairro do endereço.
                    6- Listar todos os endereços.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada (1-6 ou 0 para sair): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando atualização!\n");
            }else if(opcao == 1){
                adicionarEndereco(conn, stmtP);
            }else if(opcao == 2){
                atualizarRua(conn);
            }else if(opcao == 3){
                atualizarNumero(conn);
            }else if(opcao == 4){
               atualizarComplemento(conn);
            }else if(opcao == 5){
                atualizarBairro(conn);
            }else if(opcao == 6){
                listAll(conn, queryListagem);
            }else{
                System.out.println("A opção digitada é inválida. Digite uma opção válida!");
            }
        }while (opcao != 0);
    }

    public static void listCidades(Connection conn, String queryListagemC) throws SQLException {
        UpdateCidades cidades = new UpdateCidades();

        System.out.println();
        cidades.listCidades(conn, queryListagemC);
    }

    public static boolean getCidaID(Connection conn, Integer id){
        Integer cidadeId = null;
        boolean cidadeIgual = false;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryListagemC)) {
            while (rs.next()) {
                cidadeId = rs.getInt("cida_id");

                if(cidadeId.equals(id)){
                    cidadeIgual = true;
                    return cidadeIgual;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao executar a consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return cidadeIgual;
    }

    public static void listAll(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder sb = new StringBuilder();

            System.out.println("---Listagem dos endereços existentes---");

            // --- Cabeçalho estilizado ---
            sb.append("┌").append("─".repeat(7)).append("┬") // ID (7)
                    .append("─".repeat(22)).append("┬") // CEP (22)
                    .append("─".repeat(27)).append("┬") // RUA (27)
                    .append("─".repeat(10)).append("┬") // NÚMERO (10)
                    .append("─".repeat(16)).append("┬") // COMPLEMENTO (16)
                    .append("─".repeat(20)).append("┬") // BAIRRO (20)
                    .append("─".repeat(13)).append("┬") // ID_CIDADE (13)
                    .append("─".repeat(10)).append("┐\n"); // STATUS (10)

            sb.append(String.format("│ %-5s │ %-20s │ %-25s │ %-8s │ %-14s │ %-18s │ %-11s │ %-8s │%n",
                    "ID", "CEP", "RUA", "NÚMERO", "COMPLEMENTO", "BAIRRO", "ID_CIDADE", "STATUS"));

            sb.append("├").append("─".repeat(7)).append("┼")
                    .append("─".repeat(22)).append("┼")
                    .append("─".repeat(27)).append("┼")
                    .append("─".repeat(10)).append("┼")
                    .append("─".repeat(16)).append("┼")
                    .append("─".repeat(20)).append("┼")
                    .append("─".repeat(13)).append("┼")
                    .append("─".repeat(10)).append("┤\n");

            System.out.print(sb.toString());

            // --- Dados da Tabela ---
            while (rs.next()) {
                String rua = rs.getString("ende_rua");
                String bairro = rs.getString("ende_bairro");
                String complemento = rs.getString("ende_complemento"); // Obtém o complemento
                String ende_status = rs.getString("ende_status"); // Puxa o status do banco de dados

                // Ajusta tamanho das strings
                rua = ajustarTamanho(rua, 25);
                bairro = ajustarTamanho(bairro, 18);
                complemento = ajustarTamanho(complemento, 14); // Ajusta o complemento
                ende_status = ajustarTamanho(ende_status, 8); // Ajusta para a largura da coluna STATUS

                System.out.printf("│ %-5d │ %-20s │ %-25s │ %-8d │ %-14s │ %-18s │ %-11d │ %-8s │%n",
                        rs.getInt("ende_id"),
                        rs.getString("ende_cep"),
                        rua,
                        rs.getInt("ende_numero"),
                        complemento, // Usa a variável ajustada
                        bairro,
                        rs.getInt("ende_cida_id"),
                        ende_status); // Imprime o status do endereço
            }

            // --- Rodapé da Tabela ---
            System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(22) +
                    "┴" + "─".repeat(27) + "┴" + "─".repeat(10) +
                    "┴" + "─".repeat(16) + "┴" + "─".repeat(20) +
                    "┴" + "─".repeat(13) + "┴" + "─".repeat(10) +
                    "┘");

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    private static String ajustarTamanho(String str, int tamanho) {
        if (str == null) str = "";
        if (str.length() > tamanho) {
            return str.substring(0, tamanho - 3) + "...";
        }
        return str;
    }

    public void adicionarEndereco(Connection conn, PreparedStatement stmt) throws SQLException {
        int auxCidaID, controle = 0;
        teclado.nextLine();
        listAll(conn, queryListagem);
        listCidades(conn, queryListagemC);

        System.out.println("\n--Adicionando endereço novo ao banco de dados--");
        System.out.println("Digite o id da cidade que terá um novo endereço adicionado: ");
        palavra = teclado.nextLine();
        auxCidaID = Integer.parseInt(palavra);

        if(getCidaID(conn, auxCidaID)){
            try(Statement stmt3 = conn.createStatement();
                ResultSet rs = stmt3.executeQuery(queryListagem)){

                System.out.println("Digite o número do CEP do endereço a ser adicionado: ");
                palavra = teclado.nextLine();

                while(rs.next()){
                    String cep = rs.getString("ende_cep");
                    if(cep.equals(palavra)){
                        controle++;
                    }
                }
            }catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());
            }
            if(controle == 0){
                stmt.setInt(1, FunctionsAux.getSequenceValue(conn, "seq_ende_id"));
                stmt.setInt(7, auxCidaID); //adiciona o ID da cidade
                stmt.setString(2, palavra); //adiciona o CEP

                System.out.println("Digite o nome da rua do endereço: ");
                palavra = teclado.nextLine();
                stmt.setString(3, palavra);

                System.out.println("Digite o número da construção, casa ou apartamento: ");
                palavra = teclado.nextLine();
                stmt.setInt(4, Integer.parseInt(palavra));

                System.out.println("Digite o complemento do endereço: ");
                palavra = teclado.nextLine();
                stmt.setString(5, palavra);

                System.out.println("Digite o bairro do endereço: ");
                palavra = teclado.nextLine();
                stmt.setString(6, palavra);

                stmt.setString(8, "S");
                stmt.executeUpdate();

                System.out.println("Endereço adicionado com sucesso!");
            }else{
                System.out.println("O CEP informado já existe no banco de dados");
            }
        }else{
            System.out.println("A cidade não foi encontrada. Digite um ID válido.");
        }
    }

    public void atualizarRua(Connection conn) throws SQLException {
        String sql = "UPDATE enderecos SET ende_rua = ? WHERE ende_id = ?";
        Integer id_cidade;

        listCidades(conn, queryListagemC);
        System.out.println("Digite o ID da cidade que terá seu endereço alterado: ");
        id_cidade = teclado.nextInt();

        if(getCidaID(conn, id_cidade)){
            PreparedStatement stmt = conn.prepareStatement(sql);

            System.out.println("\n--- Listagem de endereços ---\n");
            listAll(conn, queryListagem);

            teclado.nextLine(); //limpando buffer
            System.out.println("-Atualização de rua por ID do endereço-");
            System.out.println("Escreva o nome da NOVA rua para o endereço a ser alterado: ");
            palavra = teclado.nextLine();
            stmt.setString(1, palavra);

            System.out.println("Escreva o id do endereco: ");
            palavra = teclado.nextLine();
            stmt.setInt(2, Integer.parseInt(palavra));

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Rua atualizada com sucesso!\n");
            }else{
                System.out.println("Endereço não encontrado!\n");
            }
        }else{
            System.out.println("O ID de cidade informado não é válido!\n");
        }
    }

    public void atualizarNumero(Connection conn) throws SQLException {
        String sql = "UPDATE enderecos SET ende_numero = ? WHERE ende_id = ?";
        Integer id_cidade;

        listCidades(conn, queryListagemC);
        System.out.println("Digite o ID da cidade que terá seu endereço alterado: ");
        id_cidade = teclado.nextInt();

        if(getCidaID(conn, id_cidade)){
            PreparedStatement stmt = conn.prepareStatement(sql);

            System.out.println("\n--- Listagem de endereços ---\n");
            listAll(conn, queryListagem);

            teclado.nextLine(); //limpando buffer
            System.out.println("-Atualização do número por ID do endereço-");
            System.out.println("Escreva o NOVO número para o endereço a ser alterado: ");
            palavra = teclado.nextLine();
            stmt.setInt(1, Integer.parseInt(palavra));

            System.out.println("Escreva o id do endereco: ");
            palavra = teclado.nextLine();
            stmt.setInt(2, Integer.parseInt(palavra));

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Número atualizado com sucesso!\n");
            }else{
                System.out.println("Endereço não encontrado!\n");
            }
        }else{
            System.out.println("O ID de cidade informado não é válido!\n");
        }
    }

    public void atualizarComplemento(Connection conn) throws SQLException {
        String sql = "UPDATE enderecos SET ende_complemento = ? WHERE ende_id = ?";
        Integer id_cidade;

        listCidades(conn, queryListagemC);
        System.out.println("Digite o ID da cidade que terá seu endereço alterado: ");
        id_cidade = teclado.nextInt();

        if(getCidaID(conn, id_cidade)){
            PreparedStatement stmt = conn.prepareStatement(sql);

            System.out.println("\n--- Listagem de endereços ---\n");
            listAll(conn, queryListagem);

            teclado.nextLine(); //limpando buffer
            System.out.println("-Atualização de complemento por ID do endereço-");
            System.out.println("Escreva o nome do NOVO complemento para o endereço a ser alterado: ");
            palavra = teclado.nextLine();
            stmt.setString(1, palavra);

            System.out.println("Escreva o id do endereco: ");
            palavra = teclado.nextLine();
            stmt.setInt(2, Integer.parseInt(palavra));

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Complemento atualizado com sucesso!\n");
            }else{
                System.out.println("Endereço não encontrado!\n");
            }
        }else{
            System.out.println("O ID de cidade informado não é válido!\n");
        }
    }

    public void atualizarBairro(Connection conn) throws SQLException {
        String sql = "UPDATE enderecos SET ende_bairro = ? WHERE ende_id = ?";
        Integer id_cidade;

        listCidades(conn, queryListagemC);
        System.out.println("Digite o ID da cidade que terá seu endereço alterado: ");
        id_cidade = teclado.nextInt();

        if(getCidaID(conn, id_cidade)){
            PreparedStatement stmt = conn.prepareStatement(sql);

            System.out.println("\n--- Listagem de endereços ---\n");
            listAll(conn, queryListagem);

            teclado.nextLine(); //limpando buffer
            System.out.println("-Atualização do Bairro por ID do endereço-");
            System.out.println("Escreva o nome do NOVO bairro para o endereço a ser alterado: ");
            palavra = teclado.nextLine();
            stmt.setString(1, palavra);

            System.out.println("Escreva o id do endereco: ");
            palavra = teclado.nextLine();
            stmt.setInt(2, Integer.parseInt(palavra));

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Bairro atualizado com sucesso!\n");
            }else{
                System.out.println("Endereço não encontrado!\n");
            }
        }else{
            System.out.println("O ID de cidade informado não é válido!\n");
        }
    }

}
