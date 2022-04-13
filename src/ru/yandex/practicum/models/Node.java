package ru.yandex.practicum.models;

public class Node<Data> {
    private Data data;
    private Node<Data> previous;
    private Node<Data> next;

    public Node(Data data, Node<Data> previous, Node<Data> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    public Data getData() {
        return data;
    }

    public Node<Data> getPrevious() {
        return previous;
    }

    public Node<Data> getNext() {
        return next;
    }

    public void setPrevious(Node<Data> previous) {
        this.previous = previous;
    }

    public void setNext(Node<Data> next) {
        this.next = next;
    }
}
