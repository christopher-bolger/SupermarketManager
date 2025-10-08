package main.java.supermarketmanager.model;

import java.util.Iterator;
import java.util.function.Consumer;

public class NodeIterator<K> implements Iterator<K> {

    private LinkedList<K> list;
    private Node<K> position, lastPosition;

    public NodeIterator(Node<K> node, LinkedList<K> list) {
        position = node;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position != null;
    }

    @Override
    public K next() {
        lastPosition = position;
        position = position.next;
        return position.getContent();
    }

    @Override
    public void remove() {
        list.remove(lastPosition);
    }

    @Override
    public void forEachRemaining(Consumer<? super K> action) {
        Iterator.super.forEachRemaining(action);
    }
}
