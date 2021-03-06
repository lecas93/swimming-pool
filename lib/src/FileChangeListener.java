import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileChangeListener extends Thread{
    private DatabaseInitialize DB;

    private String filePath;

    public FileChangeListener(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public void run() {
        System.out.println("filechangelistener corriendo");
        String firstContent = getFileContent();

        while(true){
            String actualContent = getFileContent();
            if( firstContent.equals(actualContent)== false ){
                System.out.println("detectado un cambio en el configfile");
                DB.notifyFileChange();
                firstContent = actualContent;
            }
        }

    }

    private String getFileContent() {
        char [] buffer = new char[200];
        try {
            File file = new File(filePath);
            FileReader reader = new FileReader(file);

            reader.read(buffer);
            reader.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        String contain = new String(buffer);
        return contain;
    }

    public void startWatching(DatabaseInitialize db) {
        this.DB = db;
    }
}