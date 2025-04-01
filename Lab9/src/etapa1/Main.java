import java.util.concurrent.*; 
import java.util.Random;


public class Main { 
    public static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
    int sleepProdutor = 100; 
    int sleepConsumidor = 1000;

    ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Produtor(blockingQueue, sleepProdutor));
        executor.execute(new Consumidor(blockingQueue, sleepConsumidor));
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
            while(true){
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
                Integer numero = blockingQueue.take();
                System.out.println("Consumido: " + numero);
                Thread.sleep(sleep);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}