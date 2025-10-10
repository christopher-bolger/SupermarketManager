package main.java.supermarketmanager.model;

import java.util.Iterator;
import java.util.function.Consumer;

public class NodeIterator<K> implements Iterator<K> {

    //telling me it should be final but the list doesn't remain a constant (if items are removed)
    //or is it referencing that the actual list reference in memory won't change?
    //Since there's no setList() method?
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
        return lastPosition.getContent();
    }

    @Override
    public void remove() {
        list.remove(lastPosition.getContent());
    }

    @Override
    public void forEachRemaining(Consumer<? super K> action) {
        Iterator.super.forEachRemaining(action);
    }
}
