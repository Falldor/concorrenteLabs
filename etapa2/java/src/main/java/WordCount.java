import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.lang.InterruptedException;

class thread extends Thread{
	private int count = 0;
	private String dirPath;
	private WordCount instancia = new WordCount();

	public thread(String dirPath){
		this.dirPath = dirPath;
	}

	public void run(){
		count = instancia.wcDir(this.dirPath);
	}

	public int getCount(){
		return this.count;
	}


}
public class WordCount{

    
    // Calculate the number of words in the files stored under the directory name
    // available at argv[1].
    //
    // Assume a depth 3 hierarchy:
    //   - Level 1: root
    //   - Level 2: subdirectories
    //   - Level 3: files
    //
    // root
    // ├── subdir 1
    // │     ├── file
    // │     ├── ...
    // │     └── file
    // ├── subdir 2
    // │     ├── file
    // │     ├── ...
    // │     └── file
    // ├── ...
    // └── subdir N
    // │     ├── file
    // │     ├── ...
    // │     └── file
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: java WordCount <root_directory>");
            System.exit(1);
        }

        String rootPath = args[0];
        File rootDir = new File(rootPath);
        File[] subdirs = rootDir.listFiles();
        int count = 0;
	List<thread> threads = new ArrayList<>();  

        if (subdirs != null) {
            for (File subdir : subdirs) {
                if (subdir.isDirectory()) {
                    String dirPath = rootPath + "/" + subdir.getName();
                    thread thread_folder = new thread(dirPath);
		    threads.add(thread_folder);
		    thread_folder.start();
                }
            }
        }

	for(int i = 0; i < threads.size(); i++){
		threads.get(i).join();
		count += threads.get(i).getCount();
	}

        System.out.println(count);
    }

    public static int wc(String fileContent) {
        String[] words = fileContent.split("\\s+");
        return words.length;
    }

    public static int wcFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            reader.close();
            return wc(fileContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int wcDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        int count = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    count += wcFile(file.getAbsolutePath());
                }
            }
            return count;
        }
        return count;
    }
}
