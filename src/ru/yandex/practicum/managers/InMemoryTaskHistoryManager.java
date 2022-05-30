package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Node;

import java.util.*;

public class InMemoryTaskHistoryManager implements TaskHistoryManager {

    private Node<AbstractTask> head;
    private Node<AbstractTask> tail;
    private Map<Integer, Node<AbstractTask>> nodeMap = new HashMap<>();

    @Override
    public List<AbstractTask> getHistory() {
        return this.getTasks();
    }

    @Override
    public void add(AbstractTask task) {
        this.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id));
            nodeMap.remove(id);
        }
        if (nodeMap.size() == 0) {
            head = null;
            tail = null;
        }
    }

    private void linkLast(AbstractTask task) {
        if (nodeMap.containsKey(task.getId())) {
            Node<AbstractTask> oldNode = nodeMap.get(task.getId());
            removeNode(oldNode);
        }

        boolean firstInsert = head == null;
        boolean secondInsert = tail == null && !firstInsert;
        Node<AbstractTask> taskNode = new Node<>(task, null, null);

        if (firstInsert) {
            head = taskNode;
        } else if (secondInsert) {
            tail = taskNode;
            head.setNext(tail);
            tail.setPrevious(head);
        } else {
            taskNode.setPrevious(tail);
            tail.setNext(taskNode);
            tail = taskNode;
        }
        nodeMap.put(task.getId(), taskNode);
    }

    private ArrayList<AbstractTask> getTasks() {
        Node<AbstractTask> node = head;
        ArrayList<AbstractTask> tasks = new ArrayList<>();
        while (node != null) {
            tasks.add(node.getData());
            node = node.getNext();
        }
        return tasks;
    }

    private void removeNode(Node<AbstractTask> node) {
        Node<AbstractTask> previous = node.getPrevious();
        Node<AbstractTask> next = node.getNext();
        boolean nodeIsFirst = previous == null;
        boolean nodeIsLast = next == null;

        if (nodeIsFirst) {
            head = next;
            if (next != null) {
                next.setPrevious(null);
            }
        } else if (nodeIsLast) {
            tail = previous;
            previous.setNext(null);
        } else {
            previous.setNext(next);
            next.setPrevious(previous);
        }
    }
}
