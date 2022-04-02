package ru.yandex.practicum.util;

import java.util.Collection;
import java.util.LinkedList;

public class LimitedStackLinkedList<E> extends LinkedList<E> {
    private final int limit;

    public LimitedStackLinkedList(int limit) {
        this.limit = limit;
    }

    private void removeExcess(){
        while (size() > limit) {
            super.removeFirst();
        }
    }

    /**
     * Добавляет элемент в начало списка, если есть место
     * @param e
     */
    @Override
    public void addFirst(E e) {
        super.addFirst(e);
        removeExcess();
    }

    /**
     * Добавляет элемент в конец списка, удаляет первый если нет места
     * @param e
     */
    @Override
    public void addLast(E e) {
        super.addLast(e);
        removeExcess();
    }

    /**
     * Добавляет элемент в конец списка, удаляет первый если нет места
     * @param o
     */
    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.removeFirst(); }
        return true;
    }

    /**
     * Добавляет коллекцию в конец списка, оставляет limit последних елементов, если нет места
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isChanged =  super.addAll(c);
        removeExcess();
        return isChanged;
    }

    /**
     * Добавляет коллекцию в определенное место списка, оставляет limit последних елементов, если нет места
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean isChanged =  super.addAll(index, c);
        removeExcess();
        return isChanged;
    }

    /**
     * Добавлят элемент в определенное место списка, удаляет первый, если нет места
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        super.add(index, element);
        removeExcess();
    }
}