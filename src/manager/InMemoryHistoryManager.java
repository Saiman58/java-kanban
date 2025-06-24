package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    Map<Integer, Node> nodes = new HashMap<>();
    Node first;
    Node last;
    private List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        removeNode(task.getId());
        LinkLast(task);
        nodes.put(task.getId(), last);
        history.add(task);
    }

    public void removeNode(Integer taskId) {
        Node node = nodes.get(taskId);
        if (node == null) {
            return;
        } else if (node == first){
            first = first.next;
        } else if (node == last){
            last = last.prew;
        } else {
            node.prew.next = node.next;
            node.next.prew = node.prew;
        }
    }

    private void LinkLast(Task task) {
        Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    @Override
    public void remove(Task task) {
        removeNode(task.getId());
        history.remove(task);
    }

    @Override
    public List<Task> getHistory() {
        //Получить список историй
        return history;
    }

    private class Node {
        Task value;
        Node prew;
        Node next;

        public Node(Task value, Node prew, Node next) {
            this.value = value;
            this.prew = prew;
            this.next = next;
        }
    }
}
