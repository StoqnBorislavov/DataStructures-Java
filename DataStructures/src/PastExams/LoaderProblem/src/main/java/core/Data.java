package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.*;

public class Data implements Repository {
    private Entity root;
    protected Queue<Entity> queue;

    public Data(){
        this.queue = new PriorityQueue<>();
        this.root = null;
    }

    public Data(Data other) {
        this.root = other.root;
        this.queue = new PriorityQueue<>(other.queue);
    }

    @Override
    public void add(Entity entity) {
        if(this.root == null){
            this.root = entity;
        } else {
            this.getById(entity.getParentId()).addChild(entity);
        }
        this.queue.offer(entity);
    }

    @Override
    public Entity getById(int id) {
        Entity current = this.root;
        if(current.getId() == id){
            return this.root;
        }
         Deque<Entity> newDeque = new ArrayDeque<>();
        newDeque.offer(current);
        while (!newDeque.isEmpty()){
            Entity entity = newDeque.poll();
            if(entity.getId() == id){
                return entity;
            }
            for (Entity child : entity.getChildren()) {
                newDeque.offer(child);
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        Entity parent = this.getById(id);
        return parent == null ? new ArrayList<>() : parent.getChildren();
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.queue);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        if(!type.equals("User") && !type.equals("Invoice") && !type.equals("StoreClient")){
            throw new IllegalArgumentException("Illegal type: " + type);
        }
        Entity current = this.root;

        List<Entity> listByType = new ArrayList<>();

        ArrayDeque<Entity> newDeque = new ArrayDeque<>();
        newDeque.offer(current);
        while (!newDeque.isEmpty()){
            Entity entity = newDeque.poll();
            if(entity.getClass().getSimpleName().equals(type)){
                listByType.add(entity);
            }
            for (Entity child : entity.getChildren()) {
                newDeque.offer(child);
            }
        }
        return listByType;
    }

    @Override
    public Entity peekMostRecent() {
        if(this.size() == 0){
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.queue.peek();
    }

    @Override
    public Entity pollMostRecent() {
        if(this.size() == 0){
            throw new IllegalStateException("Operation on empty Data");
        }
        Entity result = this.queue.poll();
        Entity parent = this.getById(result.getParentId());
        if(parent != null && parent.getChildren() != null){
            parent.getChildren().remove(result);
        }
        return result;
    }

    @Override
    public int size() {
        return this.queue.size();
    }
}
