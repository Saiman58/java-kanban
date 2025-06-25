package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node> nodes = new HashMap<>();
    private Node first;
    private Node last;


    @Override
    public void add(Task task) {
        removeNode(task.getId());
        linkLast(task);
        nodes.put(task.getId(), last);
    }

    public void removeNode(Integer taskId) {
        Node node = nodes.get(taskId);
        if (node == null) {
            return;
        }

        if (node == first) {
            first = first.next;
            if (first != null) {
                first.prew = null;
            }
        } else if (node == last) {
            last = last.prew;
            if (last != null) {
                last.next = null;
            }
        } else {
            node.prew.next = node.next;
            node.next.prew = node.prew;
        }
        nodes.remove(taskId); // Удаляем узел из карты
    }

    private void linkLast(Task task) {
        Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();

        Node node = first;
        while (node != null) {
            tasks.add(node.value);
            node = node.next;
        }
        return tasks;
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