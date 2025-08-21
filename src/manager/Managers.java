package manager;

import java.io.File;
import java.io.IOException;

public class Managers {

    public static TaskManager getDefault() {
        File taskFile = new File("task data/data.csv");

        //  существования папки и файла
        if (!taskFile.getParentFile().exists()) {
            taskFile.getParentFile().mkdirs(); // создай директорию, если ее нет
        }

        try {
            if (!taskFile.exists()) {
                taskFile.createNewFile(); // создай файл, если его нет
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }

        return new FileBackedTaskManager(taskFile);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
