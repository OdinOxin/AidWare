package de.odinoxin.aiddesk.controls.reflist;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aiddesk.plugins.RecordItem;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A list of {@link de.odinoxin.aiddesk.controls.refbox.RefBox}es.
 *
 * @param <T> The type of the records.
 */
public class RefList<T extends RecordItem<?>> extends VBox implements ObservableList<T> {

    private ObservableList<T> items;
    private Provider<T> provider;

    private ListChangeListener<? super T> listener;

    public RefList() {
        this.setSpacing(5);
        this.bindBidirectional(FXCollections.observableArrayList());
    }

    /**
     * Sets the provider.
     *
     * @param provider The provider to set.
     */
    public void setProvider(Provider<T> provider) {
        this.provider = provider;
        for (int i = 0; i < this.getChildren().size(); i++)
            ((RefListCell<T>) this.getChildren().get(i)).setProvider(provider);
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public int indexOf(Object o) {
        return this.items.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.items.lastIndexOf(o);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.items.size())
            return null;
        return this.items.get(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.items.subList(fromIndex, toIndex);
    }

    @Override
    public boolean add(T item) {
        return this.items.add(item);
    }

    @Override
    public void add(int index, T element) {
        this.items.add(index, element);
    }

    @Override
    public T set(int index, T item) {
        return this.items.set(index, item);
    }

    @Override
    public T remove(int index) {
        return this.items.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.items.remove(o);
    }

    @Override
    public void remove(int from, int to) {
        this.items.remove(from, to);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.items.removeAll(c);
    }

    @Override
    public boolean removeAll(T[] elements) {
        return this.items.removeAll(elements);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.items.addAll(c);
    }

    @Override
    public boolean addAll(T[] elements) {
        return this.items.addAll(elements);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.items.addAll(index, c);
    }

    @Override
    public boolean setAll(Collection<? extends T> col) {
        return this.items.addAll(col);
    }

    @Override
    public boolean setAll(T[] elements) {
        return this.items.addAll(elements);
    }

    @Override
    public boolean contains(Object o) {
        return this.items.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.items.containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.items.retainAll(c);
    }

    @Override
    public boolean retainAll(T[] elements) {
        return this.items.retainAll(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.items.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.items.listIterator(index);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        this.items.addListener(listener);
    }

    @Override
    public void addListener(ListChangeListener<? super T> listener) {
        this.items.addListener(listener);
    }

    public void bindBidirectional(ObservableList<T> items) {
        this.items = items;
        this.items.addListener((ListChangeListener.Change<? extends T> c) -> {
            while (c.next()) {
                if (!c.wasReplaced() && c.wasRemoved()) {
                    this.getChildren().remove(c.getFrom(), c.getFrom() + c.getRemovedSize());
                } else if (!c.wasReplaced() && c.wasAdded()) {
                    int i = c.getFrom();
                    for (; i < c.getFrom() + c.getAddedSize(); i++)
                        this.getChildren().add(i, new RefListCell<T>(RefList.this.provider, this, i));
                }
                for (int i = 0; i < this.getChildren().size(); i++)
                    ((RefListCell<T>) this.getChildren().get(i)).update(i);
            }
        });
        this.getChildren().clear();
        for (int i = 0; i < this.items.size(); i++)
            this.getChildren().add(new RefListCell<T>(RefList.this.provider, this, i));
        this.getChildren().add(new RefListCell<T>(RefList.this.provider, this, this.items.size()));
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.items.removeListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super T> listener) {
        this.items.removeListener(listener);
    }

    @Override
    public Object[] toArray() {
        return this.items.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return this.items.toArray(a);
    }
}
