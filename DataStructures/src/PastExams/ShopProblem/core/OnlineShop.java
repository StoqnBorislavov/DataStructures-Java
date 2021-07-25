package core;

import model.Order;
import shared.Shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnlineShop implements Shop {
    private List<Order> orders;

    public OnlineShop() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void add(Order order) {
        this.orders.add(order);
    }

    @Override
    public Order get(int index) {
        if(!ensureIndexTrue(index)){
            throw new IndexOutOfBoundsException();
        }
        return this.orders.get(index);
    }

    private boolean ensureIndexTrue(int index) {
        return index > 0 && this.orders.size() > index;
    }

    @Override
    public int indexOf(Order order) {
        for (int i = 0; i < this.orders.size(); i++) {
            if(this.orders.get(i).getId() == order.getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Boolean contains(Order order) {
        for (Order o : orders) {
            if(o.getId() == order.getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean remove(Order order) {
        for (Order o : orders) {
            if(o.getId() == order.getId()){
                this.orders.remove(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean remove(int id) {
        for (Order o : orders) {
            if(o.getId() == id){
                this.orders.remove(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public void set(int index, Order order) {
        if(!ensureIndexTrue(index)){
            throw new IndexOutOfBoundsException();
        }
        this.orders.set(index, order);
    }

    @Override
    public void set(Order oldOrder, Order newOrder) {
        if(!ensureIndexTrue(this.orders.indexOf(oldOrder))){
            throw new IndexOutOfBoundsException();
        }
        this.orders.set(this.orders.indexOf(oldOrder), newOrder);
    }


    @Override
    public void clear() {
        this.orders.clear();
    }

    @Override
    public Order[] toArray() {
        Order[] or = new Order[this.orders.size()];
        or = this.orders.toArray(or);
        return or;
    }

    @Override
    public void swap(Order first, Order second) {
        if(!this.orders.contains(first) || !this.orders.contains(second)){
            throw new IllegalArgumentException();
        }
        Collections.swap(this.orders, this.orders.indexOf(first), this.orders.indexOf(second));
    }

    @Override
    public List<Order> toList() {
        return new ArrayList<>(this.orders);
    }

    @Override
    public void reverse() {
        Collections.reverse(this.orders);
    }

    @Override
    public void insert(int index, Order order) {
        if(!ensureIndexTrue(index) || this.orders.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        this.orders.add(index, order);
    }

    @Override
    public Boolean isEmpty() {
        return this.orders.isEmpty();
    }

    @Override
    public int size() {
        return this.orders.size();
    }
}
