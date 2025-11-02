package supermarketmanager.model.supermarket;

import supermarketmanager.model.linkedlist.LinkedList;

import java.util.Collection;
import java.util.Objects;

public abstract class MarketStructure<E> {
    private String name = "";
    protected LinkedList<E> list;
    private int[] dimensions = new int[2];

    public MarketStructure(String name, int[] dimensions) {
        if(name != null && !name.isEmpty()) {
            if (name.length() > 30)
                this.name = name.substring(0, 30);
            else
                this.name = name;
        }
        if(dimensions.length != 2){
            dimensions[0] = 1;
            dimensions[1] = 1;
        }else{
            this.dimensions[0] = dimensions[0];
            this.dimensions[1] = dimensions[1];
        }
        list = new LinkedList<>();
    }

    public void setName(String name){
        if(name != null && !name.isEmpty())
            if(name.length() > 30)
                this.name = name.substring(0, 30);
            else
                this.name = name;
    }

    public String getName(){
        return name;
    }

    public LinkedList<E> getList() {
        if(list.isEmpty())
            return null;
        return list;
    }

    public void setList(Collection<E> list) {
        if(list == null || list.isEmpty())
            return;
        this.list.clear();
        this.list.addAll(list);
    }

    public void setDimensions(int[] dimensions) {
        if(dimensions.length == 2){
            if(dimensions[0] > 0 && dimensions[1] > 0) {
                this.dimensions[0] = dimensions[0];
                this.dimensions[1] = dimensions[1];
            }
        }
    }

    public int[] getDimensions() {
        return dimensions;
    }

    public boolean add(E item){
        return item != null && list.add(item);
    }

    public void add(E item, int index){
        list.add(index, item);
    }

    public E set(E item, int index){
        return list.set(index, item);
    }

    public boolean addAll(Collection<E> items){
        return list.addAll(items);
    }

    public boolean remove(E item){
        return list.remove(item);
    }

    public boolean removeAll(Collection<E> item){
        return list.removeAll(item);
    }

    public boolean checkIndex(int index){
        return list.get(index) != null;
    }

    public boolean contains(E item){
        return list.contains(item);
    }

    public E getFirst(){
        return list.getFirst();
    }

    public E getLast(){
        return list.getLast();
    }

    public int size(){
        return list.size();
    }

    public String toString(){
        return details();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketStructure<?> that = (MarketStructure<?>) o;
        return name.toLowerCase().contains(that.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public abstract boolean replace(E itemToReplace, E item);

    public abstract String details();

    public String getListDetails(){
        if(list.isEmpty())
            return "No items in list!";
        StringBuilder string = new StringBuilder();
        int index = 0;
        for(E o : list){
            string.append("Index: ").append(index).append("\t").append(o);
            index++;
        }
        return string.toString();
    }

    public Object get(int index) {
        return list.get(index);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
