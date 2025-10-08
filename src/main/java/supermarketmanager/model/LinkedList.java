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
        if(!checkIndex(index) || element == null)
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
       Node<E> temp=head;
        //while(temp!=null && !temp.getContent().equals(element)) temp=temp.next;
        for(temp=head; temp!=null && !temp.getContent().equals(element); temp=temp.next);
        return temp==null ? null : temp.getContent();



        if(isEmpty())
            return null;
        if(size() == 1)
            return head.getContent().equals(element) ? head.getContent() : null;

        Node<E> node = head;
        boolean found = false;
        do{
            if(node.getContent().equals(element)) {
                found = true;
                break;
            }
            node = node.next;
        }while(node.getContent() != null);

        return found ? node.getContent() : null;
    }

    public E get(int index){
        if(checkIndex(index)){
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
        if(!checkIndex(index))
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
        if(!checkIndex(index))
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
        if(isEmpty() || !checkIndex(fromIndex) || !checkIndex(toIndex))
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
        List.super.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        List.super.addLast(e);
    }

    //    TODO
// I feel like this is overly complicated
    public boolean remove(Object element){
        if(element == null || isEmpty())
            return false;

        if(size() == 1)
            if(head.getContent().equals(element)) {
                clear();
                return true;
            }else return false;

        if(head.getContent().equals(element)){
            head = head.next;
            return true;
        }else {
            Node<E> temp = head;
            boolean found = false;
            while (temp.next != null) {
                if (temp.next.getContent().equals(element)) {
                    found = true;
                    break;
                }
                temp = temp.next;
            }
            if(found && temp.next.next != null){
                temp.next = temp.next.next;
                return true;
            }else if(found) {
                tail = temp.next;
                return true;
            }else return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
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
    private boolean checkIndex(int index){
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
        return new Object[0];
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
