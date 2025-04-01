import java.util.concurrent.*; 
import java.util.Random;


public class Main { 
    public static BlockingQueue<Integer> blockingQueue = new PriorityBlockingQueue<>();

    public static void main(String[] args) {
    int sleepProdutor = 10; 
    int sleepConsumidor = 600;

    ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Produtor(blockingQueue, sleepProdutor));
        executor.execute(new Consumidor(blockingQueue, sleepConsumidor));
        executor.shutdown();
    }
}

class Produtor implements Runnable {
    public static Random random = new Random();

    private final BlockingQueue<Integer> blockingQueue; 
    private final int sleep;

    public Produtor(BlockingQueue<Integer> blockingQueue, int sleep) {
        this.blockingQueue = blockingQueue;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10000; i++) {
                int numero = random.nextInt(10) + 1;
                blockingQueue.put(numero);
                System.out.println("Produzido: " + numero);
                Thread.sleep(sleep);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

class Consumidor implements Runnable { 
    private final BlockingQueue<Integer> blockingQueue; 
    private final int sleep;

    public Consumidor(BlockingQueue<Integer> blockingQueue, int sleep) {
        this.blockingQueue = blockingQueue;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer numero = blockingQueue.poll(600, TimeUnit.MILLISECONDS);
                if (numero == null) {
                    System.out.println("Timeout: Nenhum número na blockingQueue.");
                    break;
                }
                System.out.println("Consumido: " + numero);
                Thread.sleep(sleep);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}