package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;


import java.util.*;

public class Loader implements Buffer {
    private List<Entity> entities;

    public Loader() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public Entity extract(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                return this.entities.remove(i);
            }
        }
        return null;
    }

    @Override
    public Entity find(Entity entity) {
        if (this.entities.contains(entity)) {
            return entity;
        }
        return null;
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entities.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.entities.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        int i = this.entities.indexOf(oldEntity);
        if (i == -1) {
            throw new IllegalStateException("Entity not found");
        }
        this.entities.set(i, newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        int firstIndex = this.entities.indexOf(first);
        int secondIndex = this.entities.indexOf(second);
        if (firstIndex == -1 || secondIndex == -1) {
            throw new IllegalStateException("Entity not found");
        }
        Collections.swap(this.entities, firstIndex, secondIndex);
    }

    @Override
    public void clear() {
        this.entities.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] arr = new Entity[this.entities.size()];
        return this.entities.toArray(arr);
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        List<Entity> retainedList = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity.getStatus().compareTo(lowerBound) >= 0 && entity.getStatus().compareTo(upperBound) <= 0) {
                retainedList.add(entity);
            }
        }
        return retainedList;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        for (Entity entity : entities) {
            if (entity.getStatus().compareTo(oldStatus) == 0) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        entities.removeIf(entity -> entity.getStatus().equals(BaseEntity.Status.SOLD));
    }

    @Override
    public Iterator<Entity> iterator() {
        return new Iterator<Entity>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < entities.size();
            }

            @Override
            public Entity next() {
                return entities.get(index++);
            }
        };
    }
}
