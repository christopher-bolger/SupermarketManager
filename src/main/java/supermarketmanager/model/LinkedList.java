package main.java.supermarketmanager.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

//Questions:
// Contracts?
// Adding node behaviour? Should I always have one extra or can I try keep the list to its actual size?
// Explanation for iterator methods
// Iterator implementations needed for removeFirst & removeLast?
// spliterator???
// stream??
// list.super()?
// addLast just add?
// Handle collection passed to method?
// replaceAll unaryOperator?
// toArray
// Why do some pass objects and others allow for E?

public class LinkedList<E> implements List<E>{
    private Node<E> head, tail;

    public LinkedList() {
        head = new Node<E>();
        tail = head;
    }

    // TODO Contract?
    // Add element/node methods
    public boolean add(E element) {
        if(element == null)
            return false;
        tail.setContent(element);
        addNode();
        return true;
    }

    @Override
    public void add(int index, E element) {
        if(!isValidIndex(index) || element == null)
            return;

        if(index == 0){
            head = new Node<>(element, head);
            return;
        }

        Node<E> node = head;
        int count = 0;
        while(count != index-1){
            node = node.next;
            count++;
        }

        Node<E> nextLink = node.next;
        node.next = new Node<>(element, nextLink);
    }

    private void addNode(){
        tail.next = new Node<E>();
        tail = tail.next;
    }

    //get element methods
    //TODO
    //Needs work
    public E get(E element){
       Node<E> temp;
       for(temp=head;
           temp!=null && !temp.getContent().equals(element);
           temp=temp.next);

       return temp == null ? null : temp.getContent();
    }

    public E get(int index){
        if(isValidIndex(index)){
            Node<E> node = head;
            int count = 0;

            while(count != index){
                node = node.next;
                count++;
            }
            return node.getContent();
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if(!isValidIndex(index))
            return null;

        Node<E> temp = head;
        int count = 0;
        while(count != index){
            temp = temp.next;
            count++;
        }
        temp.setContent(element);
        return temp.getContent();
    }

    public E getFirst(){
        return size() > 0 ? head.getContent() : null;
    }

    public E getLast(){
        return size() > 0 ? tail.getContent() : null;
    }

    @Override
    public E removeFirst() {
        Node<E> temp = head;
        head = head.next;
        return temp.getContent();
    }

    @Override
    public E removeLast() {
        Node<E> temp = head;

        int indexToRemove = size()-2;
        int index = 0;

        while(index != indexToRemove){
            temp = temp.next;
            index++;
        }

        Node<E> removedObject = temp.next;
        temp.next = new Node<>();
        tail = temp.next;
        return removedObject.getContent();
    }

    @Override
    public List<E> reversed() {
        return List.super.reversed();
    }

    //removal methods
    public E remove(int index){
        if(!isValidIndex(index))
            return null;

        Node<E> removed = head;
        if(index == 0) {
            head = head.next;
            return head.getContent();
        }

        int count = 0;
        while(count != index-1){
            removed = removed.next;
            count++;
        }
        if(removed.next == tail) {
            tail = removed;
            addNode();
        }else
            removed.next = removed.next.next;
        return removed.getContent();
    }

    @Override
    public int indexOf(Object o) {
        if(isEmpty())
            return -1;
        int index = 0;
        Node<E> temp = head;
        while(temp.getContent() != null){
            if(temp.getContent().equals(o))
                break;
            temp = temp.next;
            index++;
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(isEmpty())
            return -1;

        int index = 0;
        int foundIndex = -1;
        Node<E> temp = head;

        while(temp.getContent() != null){
            if(temp.getContent().equals(o))
                foundIndex = index;
            temp = temp.next;
            index++;
        }
        return foundIndex;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if(isEmpty() || !isValidIndex(fromIndex) || !isValidIndex(toIndex))
            return List.of();

        int index = 0;
        Node<E> temp = head;
        while(index != fromIndex){
            temp = temp.next;
            index++;
        }

        List<E> returnList = new LinkedList<E>();
        while(index != toIndex+1){
            E object = temp.getContent();
            returnList.add(object);
            temp = temp.next;
            index++;
        }
        return returnList;
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return List.super.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return List.super.parallelStream();
    }

    @Override
    public void addFirst(E e) {
        if(e == null)
            return;
        head = new Node<>(e, head);
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    //    TODO
// I feel like this is overly complicated
    @Override
    public boolean remove(Object element){
        Node<E> temp, prev = head;
        for(temp = head; temp != null && !temp.getContent().equals(element); temp=temp.next) prev = temp;

        if(temp == null)
            return false;
        if(prev.next.next == null) {
            prev.next = new Node<>();
            tail = prev.next;
        }else prev.next = prev.next.next;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c)
            if(!contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(c == null || c.contains(null))
            return false;

        for(E o : c)
            add(o);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if(!isValidIndex(index) || c == null || c.contains(null))
            return false;

        for(E o : c) {
            add(index, o);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return List.super.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List.super.sort(c);
    }

    //misc methods
    private boolean isValidIndex(int index){
        return index > -1 && index < size();
    }

    public void clear(){
        head = new Node<>();
        tail = head;
    }

    public int size(){
        Node<E> node = head;
        int count = 0;
        while(node.getContent() != null){
            count++;
            node = node.next;
        }
        return count;
    }

    public boolean isEmpty(){
        return head.getContent() == null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeIterator<>(head, this);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Node<E> temp = head;
        for(int i = 0; i < array.length; i++){
            array[i] = temp.getContent();
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return List.super.toArray(generator);
    }

    public String toString(){
        if(head.getContent() == null)
            return null;

        Node<E> node = head;
        StringBuilder string = new StringBuilder();
        while(node != null){
            string.append(node.getContent().toString()).append("\n");
            node = node.next;
        }
        return string.toString();
    }
}
