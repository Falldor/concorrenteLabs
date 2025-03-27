import java.io.*;
import java.util.concurrent.*;


public class ContadorPalavras{

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(args.length);
        Future<Integer> futuro =null;
        int total = 0;

        for(String arquivo: args){ 
                Callable<Integer> tarefa = () -> {
                    return contarPalavras(arquivo);
                };
                futuro = executorService.submit(tarefa);
        }

        for(int i  = 0; i < args.length; i++){
            
            try {
                total += futuro.get();

            } catch (Exception erro) {
                System.out.println(erro);
            }
            
        }
        executorService.shutdownNow();
        System.out.println(total); 
    }

    static int contarPalavras(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        int count = 0;
        String linha;
        while ((linha = br.readLine()) != null) {
            count += linha.split("\\s+").length;
        }
        br.close();
        return count;
    }
}

