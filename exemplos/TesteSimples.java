import o.famoso.nfe.item.ItemNFe;

public class TesteSimples {
    public static void main(String[] args) {
        System.out.println("TESTE SIMPLES ITEMNFE");
        
        try {
            // Teste básico
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD001");
            item.setDescricaoItem("TESTE BASICO");
            item.setNCM("12345678");
            item.setCFOP(5102);
            
            System.out.println("Item criado: " + item.getDescricaoItem());
            System.out.println("Numero: " + item.getNItem());
            System.out.println("E produto: " + item.isProduto());
            System.out.println("E servico: " + item.isServico());
            
            System.out.println("TESTE CONCLUIDO COM SUCESSO!");
            
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 