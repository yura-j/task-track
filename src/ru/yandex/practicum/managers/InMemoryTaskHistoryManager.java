package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Node;

import java.util.*;

public class InMemoryTaskHistoryManager implements TaskHistoryManager {

    public List<AbstractTask> getHistory() {
        return this.getTasks();
    }

    public void add(AbstractTask task) {
        this.linkLast(task);
    }

    public void remove(int id) {
        if (nodeMap.containsKey(id)){
            removeNode(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }

    /**
     * Я не понял почему по условию задачи
     * нельзя просто использовать LinkedHashSet
     * Уникальность есть
     * Упорядоченность есть
     * add и remove за O(1)
     */
    private Node<AbstractTask> head;
    private Node<AbstractTask> tail;
    private Map<Integer, Node<AbstractTask>> nodeMap = new HashMap<>();

    private void linkLast(AbstractTask task) {
        Node<AbstractTask> taskNode, oldNode;
        boolean firstInsert = head == null;
        boolean secondInsert = tail == null && !firstInsert;
        taskNode = new Node<>(task, null, null);
        if (firstInsert) {
            head =  taskNode;
        } else if (secondInsert){
            tail = taskNode;
            head.setNext(tail);
            tail.setPrevious(head);
        } else {
            if (nodeMap.containsKey(task.getId())){
                oldNode = nodeMap.get(task.getId());
                removeNode(oldNode);
            }
                taskNode.setPrevious(tail);
                tail.setNext(taskNode);
                tail = taskNode;
        }
        nodeMap.put(task.getId(), taskNode);
    }

    private ArrayList<AbstractTask> getTasks() {
        Node<AbstractTask> node = head;
        ArrayList<AbstractTask> tasks =  new ArrayList<>();
        while (node != null){
            tasks.add(node.getData());
            node = node.getNext();
        }
        return tasks;
    }

    private void removeNode(Node<AbstractTask> node){
        Node<AbstractTask> previous, next;
        previous = node.getPrevious();
        next = node.getNext();
        boolean nodeIsFirst = previous == null;
        boolean nodeIsLast = next == null;
        if (nodeIsFirst){
            head = next;
            if (next != null){
                next.setPrevious(null);
            }
        } else if (nodeIsLast){
            tail = previous;
            previous.setNext(null);
        } else {
            previous.setNext(next);
            next.setPrevious(previous);
        }
    }
}
