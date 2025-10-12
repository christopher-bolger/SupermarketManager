package main.java.supermarketmanager.model;

import java.util.ListIterator;
import java.util.function.Consumer;

public class LinkedListIterator<E> implements ListIterator<E> {
    private Node<E> position, previousPosition;
    private LinkedList<E> list;
    private int index;

    public LinkedListIterator(Node<E> node, LinkedList<E> list){
        position = node;
        this.list = list;
    }

    public LinkedListIterator(Node<E> node, LinkedList<E> list, int index){
        position = node;
        int startIndex = 0;
        while(startIndex < index){
            position = position.next;
            startIndex++;
        }
        this.index = index;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position != null;
    }

    @Override
    public E next() {
        previousPosition = position;
        position = position.next;
        index++;
        return previousPosition.getContent();
    }

    @Override
    public boolean hasPrevious() {
        return list.indexOf(previousPosition.getContent()) > 0;
    }

    @Override
    public E previous() {
        return list.get(list.indexOf(previousPosition.getContent())-1);
    }

    @Override
    public int nextIndex() {
        return hasNext() ? index + 1 : -1;
    }

    @Override
    public int previousIndex() {
        return hasPrevious() ? index - 1 : -1;
    }

    @Override
    public void remove() {
        list.remove(previousPosition.getContent());
    }

    @Override
    public void set(E e) {
        list.set(index, e);
    }

    @Override
    public void add(E e) {
        list.add(index, e);
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
//        Iterator.super.forEachRemaining(action);
    }
}
