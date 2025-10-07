package main.java.supermarketmanager.model;

import java.util.Objects;

public class LinkedList<E> {
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
    public E get(E element){
        Node<E> node = head;
        boolean found = false;
        do{
            if (node.getContent().equals(element)) {
                found = true;
                break;
            }
            node = node.next;
        }while(node != tail);
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
        if(temp.next == tail)
            tail = temp;
        else
            temp.next = temp.next.next;
        return true;
    }

//    TODO
//    This doesn't work. Needs work.
    public boolean remove(E element){
        if(element == null || head.getContent() == null)
            return false;

        boolean found = false;
        if(size() > 1){
            Node<E> target = head, previous = new Node<>();
            while(target.next != null){
                if(target.getContent().equals(element)) {
                    found = true;
                    break;
                }
                previous.next = head;
                target = target.next;
            }
            if(!found)
                return false;

            //Telling me it could be null but the if statement prevents that?
            //I should always go through the while at least once
            previous.next = target.next;
        }else{
            if (head.getContent().equals(element)) {
                head = new Node<>();
                tail = head;
            }
        }
        return true;
    }

    //misc methods
    private boolean checkIndex(int index){
        return index > -1 && index < size();
    }

    public int size(){
        if(head.getContent() == null)
            return 0;

        Node<E> node = head;
        int count = 1;
        while(node.next != null){
            count++;
            node = node.next;
        }
        return count;
    }

    public String toString(){
        Node<E> node = head;
        StringBuilder string = new StringBuilder();
        do{
            string.append(node.getContent()).append("\n");
            node = node.next;
        }while(node != tail);

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
