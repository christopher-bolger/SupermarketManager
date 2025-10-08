package main.java.supermarketmanager.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

//TODO
//Look at implementing Iterable & Iterator -> On the notes
//Maybe also implement List
public class LinkedList<E>{
    private Node<E> head, tail;

    public LinkedList() {
        head = new Node<E>();
        tail = head;
    }

    // Add element/node methods
    public boolean add(E element) {
        if(element == null)
            return false;

        if(tail.getContent() != null)
            addNode();
        return tail.setContent(element);
    }

    public boolean insert(int index, E element) {
        if(!checkIndex(index) || element == null)
            return false;

        if(index == 0){
            head = new Node<>(element, head);
            return true;
        }

        Node<E> node = head;
        int count = 0;
        while(count != index-1){
            node = node.next;
            count++;
        }

        Node<E> nextLink = node.next;
        node.next = new Node<>(element, nextLink);
        return true;
    }

    private void addNode(){
        tail.next = new Node<E>();
        tail = tail.next;
    }

    //get element methods
    //TODO
    //Needs work
    public E get(E element){
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
        }while(node != null);

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

    public E getFirst(){
        return head.getContent();
    }

    public E getLast(){
        return tail.getContent();
    }

    //removal methods
    public boolean remove(int index){
        if(!checkIndex(index))
            return false;

        if(index == 0) {
            head = head.next;
            return true;
        }

        Node<E> temp = head;
        int count = 0;
        while(count != index-1){
            temp = temp.next;
            count++;
        }
        if(temp.next == tail) {
            tail = temp;
            tail.next = null;
        }else
            temp.next = temp.next.next;
        return true;
    }

//    TODO
// I feel like this is overly complicated
    public boolean remove(E element){
        if(element == null || isEmpty())
            return false;

        if(size() == 1)
            if(head.getContent().equals(element)) {
                return clear();
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

    //misc methods
    private boolean checkIndex(int index){
        return index > -1 && index < size();
    }

    public boolean clear(){
        head = new Node<>();
        tail = head;
        return true;
    }

    public int size(){
        if(head == null || head.getContent() == null)
            return 0;

        Node<E> node = head;
        int count = 1;
        while(node.next != null){
            count++;
            node = node.next;
        }
        return count;
    }

    public boolean isEmpty(){
        return head.getContent() == null;
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

    private class Node<E>{
        private E content;
        public Node<E> next;

        public Node(){
            content = null;
            next = null;
        }

        public Node(E content, Node<E> next){
            if(next != null) {
                setContent(content);
                this.next = next;
            }
        }

        public boolean setContent(E content){
            if(content != null) {
                this.content = content;
                return true;
            }
            return false;
        }

        public E getContent(){
            return content;
        }

        public String toString(){
            return content.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(content, node.content) && Objects.equals(next, node.next);
        }
    }
}
