package ArrayList;

import java.util.*;

/**
 * An implementation of ArrayList data structure.
 * @param <T> - the data type for the list to store.
 */
public class MyArrayList<T> implements List<T> {
    // Class Variables //
    /** The array that is storing the elements*/
    private Object[] array;
    /** The max size of the array*/
    private int capacity;
    /** The current size of the array (amount used)*/
    private int size;

    private static final int INIT_SIZE = 100;

    // Constructors //
    public MyArrayList(int initCapaicity) {
        array = new Object[initCapaicity];
        capacity = initCapaicity;
        size = 0;
    }

    public MyArrayList() {
        this(INIT_SIZE);
    }

    public MyArrayList(Collection<T> c) {
        this();
        this.addAll(c);
    }


    // Methods //
    /**
     * A constructor cloning method that creates a shallow copy of the MyArrayList instance.
     * @return a clone of this MyArrayList instance
     */
    public MyArrayList<T> copy() {
        return new MyArrayList<>(this);
    }

    /**
     * Returns the number of elements in the list.
     * @return the number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Determines whether the list is empty or not.
     * @return true if the list contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Determines whether the list contains the specified element.
     * @param o - the element whose presence in the list is to be tested.
     * @return true if the list contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
    }

    /**
     * Returns the Iterator for this list instance.
     * @return Iterator for this list instance.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyListIterator();
    }

    /**
     * Returns a shallow copy the array containing all the elements.
     * @return a shallow copy the array containing all the elements.
     */
    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size];
        for (int i = 0; i < size; temp[i] = array[i++]);

        return temp;
    }

    /**
     * Append the specified element to the end of this list.
     * @param o - the element to be appended.
     * @return true
     */
    @Override
    public boolean add(T o) {
        this.add(size, o);
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from the list if it exists.
     * @param o - the element to be removed from the list.
     * @return true if the list contained the specified element.
     */
    @Override
    public boolean remove(Object o) {
        int i = this.indexOf(o);

        if (i >= 0) {
            this.safeShiftElementsLeft(i + 1, size - 1, 1);
            size--;
        }

        return i >= 0;
    }

    /**
     * Append all of the elements in the specified collection to the end of the list,
     * in the order they are returned by the Collection's Iterator.
     * @param c - the collection of elements to be appended.
     * @return true if elements were added to the list.
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (size + c.size() >= capacity) {
            this.resizeArray((size + c.size() - 1) - capacity);
        }

        for (T el : c) {
            this.add(el);
        }

        return c.size() != 0;
    }

    /**
     * Append all of the elements in the specified collection starting from the specified index,
     * in the order they are returned by the Collection's Iterator.
     * @param index - the starting index to add the Collection
     * @param c - the collection of elements to be added
     * @return true if elements were added to the list.
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot add element to index %d from list of size %d", index, size));
        }

        Object[] temp = c.toArray();

        if (size + temp.length >= capacity) {
            this.resizeArray((size + temp.length - 1) - capacity);
        }

        this.safeShiftElementsRight(index, size - 1, temp.length);
        size += temp.length;

        for (int i = 0; i < temp.length; array[i + index] = temp[i++]);

        return temp.length != 0;
    }

    /**
     * Set the list to size 0.
     */
    @Override
    public void clear() {
        size = 0;
    }

    /**
     * Access the element at the specified index in the list.
     * @param index - the index of the element to be accessed.
     * @return the element at the specified index in the list.
     */
    @Override @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot get element at index %d from list of size %d", index, size));
        } else {
            return (T) array[index];
        }
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index - the index to set the specified element.
     * @param element - the element to be set at the specified index.
     * @return the replaced element.
     */
    @Override @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot set element at index %d from list of size %d", index, size));
        } else {
            T o = (T) array[index];
            array[index] = element;
            return o;
        }
    }

    /**
     * Append the specified element at the specified index.
     * @param index - the index to insert the specified element at.
     * @param o - the element to be inserted.
     */
    @Override
    public void add(int index, T o) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot add element to index %d from list of size %d", index, size));
        }

        if (size == capacity) {
            this.resizeArray(0);
        }

        this.safeShiftElementsRight(index, size - 1, 1);
        array[index] = o;
        size++;
    }

    /**
     * Remove the element at the specified index.
     * @param index - the index to remove element.
     * @return the removed element from the specified index.
     */
    @Override @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot remove element at index %d from list of size %d", index, size));
        } else {
            Object o = array[index];
            this.safeShiftElementsLeft(index + 1, size - 1, 1);
            size--;
            return (T) o;
        }
    }

    /**
     * Return the index of the first occurrence of the specified element in the list,
     * or -1 if list does not contain such element.
     * @param o - the element to search for.
     * @return the index of the first occurrence of the specified element in the list,
     *      * or -1 if list does not contain such element.
     */
    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Return the index of the last occurrence of the specified element in the list,
     *      * or -1 if list does not contain such element.
     * @param o - the element to be searched for
     * @return the index of the last occurrence of the specified element in the list,
     *      * or -1 if list does not contain such element.
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i > -1; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Return The ListIterator for this MyArrayList instance.
     * @return the ListIterator for this MyArrayList instance.
     */
    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    /**
     * The ListIterator for this MyArrayList instance with the iterator starting at the specified index.
     * @param index - the starting index for the iterator.
     * @return the ListIterator for this MyArrayList instance with the iterator starting at the specified index.
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot iterate element at index %d from list of size %d", index, size));
        }
        return new MyListIterator(index);
    }

    /**
     * Returns a view of the portion of this list between the specified inclusiveLower index and exclusiveUpper index.
     * The returned list is backed by this list, so non-structural changes in the returned list are reflected in
     * this list, and vice-versa.
     * @param inclusiveLower - low endpoint (inclusive) of the subList
     * @param exclusiveUpper - high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     */
    @Override @SuppressWarnings("unchecked")
    public List<T> subList(int inclusiveLower, int exclusiveUpper) {
        if (inclusiveLower < 0 || inclusiveLower >= size || exclusiveUpper < 0 || exclusiveUpper > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot sublist elements from index %d to index %d from list of size %d"
                            , inclusiveLower, exclusiveUpper, size));
        }

        List<T> temp = new MyArrayList<T>();

        for (int i = inclusiveLower; i < exclusiveUpper; temp.add((T) array[i++]));

        return temp;
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection.
     * @param c - collection containing elements to be retained in this list.
     * @return true if this list changed as a result of the call.
     */
    @Override @SuppressWarnings("unchecked")
    public boolean retainAll(Collection c) {
        int initSize = size;
        HashSet<T> lookup = new HashSet<>(c);
        this.removeIf(el -> !lookup.contains(el));
        return size != initSize;
    }

    /**
     * Removes from this list all of its elements that are contained in the specified collection.
     * @param c - collection containing elements to be removed from this list.
     * @return true if this list changed as a result of the call.
     */
    @Override @SuppressWarnings("unchecked")
    public boolean removeAll(Collection c) {
        int initSize = size;
        HashSet<T> lookup = new HashSet<>(c);
        this.removeIf(lookup::contains);
        return size != initSize;
    }

    /**
     * Returns true if this collection contains all of the elements in the specified collection.
     * @param c - collection to be checked for containment in this collection
     * @return true if this list contains all of the elements in the specified collection
     */
    @Override @SuppressWarnings("unchecked")
    public boolean containsAll(Collection c) {
        HashSet<T> lookup = new HashSet<>(this);

        for (Object el : c) {
            if (!lookup.contains(el)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns an array containing all of the elements in this list; If the list fits in the specified array,
     * it is returned therein. Otherwise, a new array is allocated and the size of this list.
     * If the list fits in the specified array with room to spare (i.e., the array has more elements than the list),
     * the element in the array immediately following the end of the collection is set to null.
     * @param array - the array to contain the elements in this list.
     * @return the specified array, containing elements in ths list.
     */
    @Override @SuppressWarnings("unchecked")
    public Object[] toArray(Object[] array) {
        array = (array.length < size)? new Object[size] : array;

        for (int i = 0; i < size; array[i] = this.array[i++]);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }


    private void safeShiftElementsRight(int startIndex, int endIndex, int shiftSize) {
        assert(endIndex + shiftSize < capacity);

        for (int i = endIndex; i > startIndex - 1; array[i + shiftSize] = array[i--]);
    }

    private void safeShiftElementsLeft(int startIndex, int endIndex, int shiftSize) {
        assert(startIndex - shiftSize > -1);

        for (int i = startIndex; i < endIndex + 1; array[i - shiftSize] = array[i++]);
    }

    private void resizeArray(int value) {
        capacity = (value == 0)? (capacity *= 2) : (capacity + value);
        Object[] newArr = new Object[capacity];

        for (int i = 0; i < size; newArr[i] = array[i++]);

        array = newArr;
    }

    /**
     * The ListIterator for MyArrayList
     */
    private class MyListIterator implements ListIterator<T>{
        // Class Variables //
        /** The next index to be called */
        private int index;
        /** Flag specifying if the list can be modified at the moment */
        private boolean canModify;

        // Constructors //
        private MyListIterator() {
            index = 0;
            canModify = false;
        }

        private MyListIterator(int startIndex) {
            index = startIndex;
            canModify = false;
        }

        // Methods //

        /**
         * Return whether there are anymore elements remaining.
         * @return true if next() can be called without error.
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * Return the next element in list
         * @return the next element in list
         */
        @Override @SuppressWarnings("unchecked")
        public T next() {
            canModify = true;
            return (T) array[index++];
        }

        /**
         * Return whether there are elements before the current element
         * @return true if previous() can be called without error
         */
        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        /**
         * Return the previous element in list
         * @return the previous element in list
         */
        @Override @SuppressWarnings("unchecked")
        public T previous() {
            canModify = true;
            return (T) array[--index];
        }

        /**
         * Return the index of the next element in list,
         * returns size of list if there are no more elements next in line.
         * @return index of next element in list, or size of list if no more elements next in line.
         */
        @Override
        public int nextIndex() {
            return index;
        }

        /**
         * Return the index of the previous element in list,
         * returns -1 if there are no elements previous in line.
         * @return index of previous element in list, or -1 if no more elements previous in line.
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }

        /**
         * Remove the current element from the list. Can only be called after a successful next()/previous() call.
         * Cannot be called back to back.
         */
        @Override
        public void remove() {
            if (!canModify) {
                throw new IllegalStateException("Cannot remove element without first calling next/prev");
            }

            safeShiftElementsLeft(index, size - 1, 1);
            size--;
            index--;
            canModify = false;
        }

        /**
         * Replace the current element with the sepcified element. Can only be called after a successful
         * next()/previous() call. Can be called back to back, however cannot be called after remove()/add() calls.
         * @param t - the replacing element.
         */
        @Override
        public void set(T t) {
            if (!canModify) {
                throw new IllegalStateException(
                        "Cannot set element if add/remove has been called after the last call to next/prev");
            }
            array[index - 1] = t;
        }

        /**
         * Add the specified element to the list. Element will be added after the current element
         * and the cursor or skip the added element. Calling previous() would return the new element.
         * @param t - the element to be added.
         */
        @Override
        public void add(T t) {
            if (size == capacity) {
                resizeArray(0);
            }

            safeShiftElementsRight(index, size - 1, 1);
            array[index] = t;
            size++;
            index++;
            canModify = false;
        }
    }
}
