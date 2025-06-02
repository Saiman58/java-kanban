package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private List<Task> history = new ArrayList<>();
    @Override
    public List<Task> getHistory() {
        //Получить список историй
        return history;
    }

    @Override
    public void add(Task task) {
        //Добавить таску в список с историей
        if (task == null) {
            return;
        }
        history.add(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }
}
