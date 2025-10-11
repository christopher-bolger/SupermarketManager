package main.java.supermarketmanager.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

//Questions:
// spliterator???
// stream??
// list.super()?
// addLast just add?
// Handle collection passed to method?
// replaceAll unaryOperator?
// toArray
// Why do some pass objects and others allow for E?
//NodeIterator <final>

public class LinkedList<E> implements List<E>{
    private Node<E> head, tail;

    public LinkedList() {
        head = new Node<>();
        tail = head;
    }

    // Add element/node methods
    //test complete
    @Override
    public boolean add(E element) {
        if(element == null)
            return false;

        if(isEmpty())
            return head.setContent(element); //or tail - both point to the same thing in an empty list
        addNode();
        return tail.setContent(element);
    }

    @Override
    //test complete
    public void add(int index, E element) {
        if(!isValidIndex(index) || element == null)
            return;

        if(index == 0){
            addFirst(element);
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

    @Override
    //test completed
    public void addFirst(E e) {
        if(e == null)
            return;
        if(head.getContent() == null)
            head.setContent(e);
        else
            head = new Node<>(e, head);
    }

    @Override
    //test completed
    public void addLast(E e) {
        add(e);
    }

    //get element methods
    //test completed
    public E get(E element){
        if(isEmpty())
            return null;
       Node<E> temp = head;
       for(;temp != null && !temp.getContent().equals(element); temp = temp.next);

       return temp == null ? null : temp.getContent();
    }

    @Override
    //test completed
    public E get(int index){
        if(!isValidIndex(index))
            return null;

        Node<E> node = head;
        int count = 0;
        while(count != index){
            node = node.next;
            count++;
        }
        return node.getContent();
    }

    @Override
    //test finished
    public E set(int index, E element) {
        if(!isValidIndex(index) || element == null)
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

    @Override
    //test completed
    public E getFirst(){
        return !isEmpty() ? head.getContent() : null;
    }

    @Override
    //test completed
    public E getLast(){
        return !isEmpty() ? tail.getContent() : null;
    }

    //removal methods
    @Override
    //test complete
    public boolean remove(Object element){
        if(!contains(element))
            return false; //saving time & memory

        Node<E> temp = head, prev = null;
        while(temp != null && !temp.getContent().equals(element)){
            prev = temp;
            temp = temp.next;
        }

        if(prev == null)
            return removeFirst().equals(element); //should never be false
        if(temp.next == null)
            return removeLast().equals(element); //should never be false

        prev.next = temp.next;
        return true;
    }

    @Override
    //test completed
    public E remove(int index){
        if(!isValidIndex(index))
            return null;

        Node<E> temp = new Node<>();
        temp.setContent(get(index));

        return remove(get(index)) ? temp.getContent() : null;
    }

    @Override
    //test complete
    public E removeFirst() {
        if(isEmpty())
            return null;

        Node<E> removedObject = new Node<>();
        removedObject.setContent(head.getContent());
        if(size() == 1)
            clear();
        else
            head = head.next;
        return removedObject.getContent();
    }

    @Override
    //test completed
    public E removeLast() {
        if(isEmpty())
            return null;

        Node<E> removedObject = new Node<>(), temp = head;
        removedObject.setContent(tail.getContent());

        if(head == tail){
            clear();
            return removedObject.getContent();
        }

        while(temp.next != tail){
            temp = temp.next;
        }

        temp.next = null;
        tail = temp;
        return removedObject.getContent();
    }

    @Override
    // do I need to check if containsAll(c)?
    // since it's an optioanl operation, I shouldn't really need to?
    // just if the element is contained within this then i'll remove it, if not i'll ignore it
    //test completed
    public boolean removeAll(Collection<?> c) {
        if(isEmpty())
            return false;

        int size = size();
        for(Object o : c)
            remove(o);
        return size < size();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return List.super.removeIf(filter);
    }

    @Override
    //test completed
    public boolean retainAll(Collection<?> c) {
        if(isEmpty())
            return false;

        LinkedList<E> newList = new LinkedList();
        for(Object o : c)
            if(contains(o))
                newList.add((E) o);

        clear();
        addAll(newList);
        return true;
    }

    @Override
    //test completed
    public List<E> reversed() {
        LinkedList<E> reversedOrder = new LinkedList<>();
        if(isEmpty())
            return reversedOrder;

        Node<E> temp = head;
        while(temp != null){
            reversedOrder.addFirst(temp.getContent());
            temp = temp.next;
        }
        return reversedOrder;
    }

    @Override
    // test finished
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
    // test finished
    public int lastIndexOf(Object o) {
        if(isEmpty())
            return -1;

        int index = 0;
        int foundIndex = -1;
        Node<E> temp = head;

        while(temp != null){
            if(temp.getContent().equals(o)){
                foundIndex = index;
            }
            temp = temp.next;
            index++;
        }
        return Math.max(foundIndex, -1);
    }

    @Override
    public ListIterator<E> listIterator() {
        return (ListIterator<E>) head;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    //test incomplete
    public List<E> subList(int fromIndex, int toIndex) {
        if(isEmpty() || !isValidIndex(fromIndex) || !isValidIndex(toIndex-1))
            return List.of();

        int index = 0;
        Node<E> temp = head;
        while(index != fromIndex){
            temp = temp.next;
            index++;
        }

        List<E> returnList = new LinkedList<>();
        while(temp != null && index < toIndex){
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
    public void replaceAll(UnaryOperator<E> operator) {
        if(isEmpty())
            return;

        Node<E> temp = head;
        while(temp != null && temp.getContent() != null){
            temp.setContent(operator.apply(temp.getContent()));
            temp = temp.next;
        }
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List.super.sort(c);
    }

    //misc methods
    private boolean isValidIndex(int index){
        if(isEmpty())
            return false;
        return index > -1 && index < size();
    }

    private void addNode(){
        tail.next = new Node<>();
        tail = tail.next;
    }

    public void clear(){
        head = new Node<>();
        tail = head;
    }

    // test finished
    public int size(){
        Node<E> node = head;
        int count = 0;
        while(node != null && node.getContent() != null){
            count++;
            node = node.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty(){
        return head.getContent() == null;
    }

    @Override
    public boolean contains(Object o) {
        if(isEmpty()) //probably silly to have this here because the gains are so small but its saving memory (I think)
            return false;

        Node<E> temp = head;
        for(; temp != null; temp = temp.next)
            if(temp.getContent().equals(o))
                return true;
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

    //TODO
    // Since it's using T is it telling me this is going to be a different type?
    // or is it just using it to signify an array, which would be a different type to the base
    // object?
    @Override
    public <T> T[] toArray(T[] a) {
        if(a.length < size())
            a = Arrays.copyOf(a, size());

        Node<E> temp = head;
        int i;
        for(i = 0; i < size(); i++){
            a[i] = (T) temp.getContent();
            temp = temp.next;
        }

        if(i < a.length)
            for(;i < a.length; i++) a[i] = null;

        return a;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return List.super.toArray(generator);
    }

    public String toString(){
        if(isEmpty())
            return null;

        Node<E> node = head;
        StringBuilder string = new StringBuilder();
        while(node != tail){
            string.append(node).append("\n");
            node = node.next;
        }
        string.append(tail);
        return string.toString();
    }
}
