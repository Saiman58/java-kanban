package manager;

public class Managers {

    public static TaskManager getDefault() { //получить значение по умолчанию
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() { //получить историю по умолчанию
        return new InMemoryHistoryManager();
    }
}
