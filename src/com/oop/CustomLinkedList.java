package com.oop;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements Iterable<T> {

    private int listSize;
    private int modCount;
    private Node<T> headerNode;
    private Node<T> tailNode;

    public CustomLinkedList(){
        clear();
    }

    /**
     * @return The size of the list.
     * */
    public int size() {
        return listSize;
    }

    /**
     * Checks if the list is empty
     * @return True if list has no items or false if list size > 0.
     * */
    public boolean isEmpty() {
        return listSize == 0;
    }

    public void clear() {
        headerNode = new Node<>(null, null, null);
        tailNode = new Node<>(null, headerNode, null);
        headerNode.next = tailNode;
        listSize = 0;
        modCount++;
    }

    /**
     * Adds a new Node to the linked list.
     * @param data The object to be stored in the nodes data variable.
     * */
    public void add(T data) {
        // Create new node between tail node and node preceding tail node.
        Node<T> newNode = new Node<>(data, tailNode.prev, tailNode);
        // Update the pointers of the two affected nodes
        tailNode.prev.next = newNode;
        tailNode.prev = newNode;
        // Increment list size
        listSize++;
        // Increment modification count
        modCount++;
    }

    /**
     * Get the data stored in a Node at the provided index.
     * @param index The list index of the node.
     * @return The object stored in the Node at the specified index.
     * */
    public T get(int index){
        return getNode(index).data;
    }

    /**
     * Get a Node from the list
     * @param index The list index of the node.
     * @return The Node at the provided index.
     * */
    public Node<T> getNode(int index){

        Node<T> node;
        // Check if list has items
        if(isEmpty())
            throw new NoSuchElementException();
        // Check index is within bounds
        if(index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException();
        // Search from the start of the list if item is in first half
        if(index < size() / 2){
            node = headerNode.next;

            for(int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        // Search from the end of the list. Item is in second half of list.
        else {
            node = tailNode;
            for(int i = size(); i > index; i--)
                node = node.prev;
        }
        // Return the node
        return node;
    }

    private T removeNode(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        listSize--;
        modCount++;

        return node.data;
    }

    /**
     * Remove the Node from the list that contains the object.
     * @param object --> The object to remove
     * */
    public T remove(T object) {
        Node<T> current = headerNode.next;

        for(int i = 0; i < size(); i++){
            current = current.next;
            if(current.prev.data.hashCode() == object.hashCode()){
                return removeNode(current.prev);
            }
        }
        throw new NoSuchElementException("The object could not be found");
    }

    /**
     * Public remove method that will be exposed. This calls the internal
     * remove method that looks up the node at the specified index and
     * removes it from the list.
     * @param index --> The index of the node to remove
     * @return The data stored in the Node.
     * */
    public T remove(int index) {
        return removeNode(getNode(index));
    }

    /**
     * @return A new instance of CustomIterator
     * */
    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    public void printList(){
        if(isEmpty())
            System.out.println("List is empty");
        else{
            for (T item : this) {
                System.out.println(item.toString());
            }
        }
    }

    private class CustomIterator implements Iterator<T> {

        Node<T> currentNode = headerNode.next;
        int expectedModCount = modCount;
        private boolean isRemovable = false;

        @Override
        public boolean hasNext() {
            return currentNode != tailNode;
        }

        @Override
        public T next() {
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();

            if(!hasNext())
                throw new NoSuchElementException();

            T nodeData = currentNode.data;
            currentNode = currentNode.next;
            isRemovable = true;

            return nodeData;

        }

        @Override
        public void remove() {
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();

            if(!isRemovable)
                throw new IllegalStateException();

            // currentNode.prev will remove the last Node returned by the call to next().
            // The iterator already knows the position of the item to remove, so the
            // linked list remove method is called and the node passed as argument.
            CustomLinkedList.this.removeNode(currentNode.prev);

            expectedModCount++;
            // isRemovable should be false after a call to remove.
            // No more nodes can be removed before another call to next
            isRemovable = false;

        }
    }

    private static class Node<T> {

        private final T data;
        public Node<T> prev;
        public Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next){
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return (String) data;
        }
    }

    public static void main() {

        CustomLinkedList<Contact> contacts = new CustomLinkedList<>();

        Contact c1 = new Contact("Paul", "123 Main street");
        Contact c2 = new Contact("Meja", "123 Main street");
        Contact c3 = new Contact("Josefin", "123 Main street");
        Contact c4 = new Contact("Bob", "123 Main street");

        System.out.println("""

                Initial List
                ============""");
        contacts.printList();

        // Add some contacts
        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);

        System.out.println("""

                List after contacts have been added
                ===================================""");

        // Display the contacts that have been added
        contacts.printList();

        // Get a contact object by index
        Contact contact1 = contacts.get(2);

        // Remove a contact with the object as argument
        Contact removed = contacts.remove(contact1);

        System.out.printf("""

                        List after contact %s removed using index argument
                        =======================================================%n""",
                removed.getName()
        );

        contacts.printList();

        // Remove a contact that doesn't exist
        try{
            System.out.printf("""

                        Try to remove contact %s from the list
                        =========================================%n""",
                    c4.getName()
            );
            contacts.remove(c4);
        } catch(NoSuchElementException e){
            System.out.println(e.getMessage());
        }

        // Remove a contact by index
        removed = contacts.remove(1);

        System.out.printf("""

                        List after contact %s removed using object argument
                        =====================================================%n""",
                removed.getName()
        );

        // Display the contacts
        contacts.printList();

    }
}

