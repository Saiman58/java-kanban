package manager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() { //получить значение по умолчанию
        return new FileBackedTaskManager(new File("resources/data.csv"));
    }

    public static HistoryManager getDefaultHistory() { //получить историю по умолчанию
        return new InMemoryHistoryManager();
    }
}
