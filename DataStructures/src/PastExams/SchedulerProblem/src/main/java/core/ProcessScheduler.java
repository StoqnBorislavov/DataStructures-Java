package core;

import model.Task;
import shared.Scheduler;

import java.util.*;

public class ProcessScheduler implements Scheduler {
    private ArrayDeque<Task> tasks;


    public ProcessScheduler() {
        this.tasks = new ArrayDeque<>();
    }

    @Override
    public void add(Task task) {
        this.tasks.offer(task);
    }

    @Override
    public Task process() {
        if(this.size() == 0) {
            return null;
        }
        return this.tasks.poll();
    }

    @Override
    public Task peek() {
        return this.tasks.peek();
    }

    @Override
    public Boolean contains(Task task) {
        return this.tasks.contains(task);
    }

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public Boolean remove(Task task) {
        if (this.tasks.remove(task)) {
            return true;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Boolean remove(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return this.tasks.remove(t);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void insertBefore(int id, Task task) {
//        List<Task> task1 = this.toList();
//        for (int i = 0; i < task1.size(); i++) {
//            if(task1.get(i).getId() == id){
//                task1.add(i - 1, task);
//                this.tasks = new ArrayDeque<>(task1);
//            }
//        }
//        throw new IllegalArgumentException();
        List<Task> tasks1 = new ArrayList<>();
        Task current = this.tasks.peek();
        while (current != null && current.getId() != id) {
            tasks1.add(this.tasks.poll());
            current = this.tasks.peek();
        }
        if (current == null) {
            throw new IllegalArgumentException();
        }
        tasks1.add(task);
        while (!tasks1.isEmpty()) {
            this.tasks.addFirst(tasks1.remove(tasks1.size() - 1));
        }
    }

    @Override
    public void insertAfter(int id, Task task) {
//        List<Task> task1 = this.toList();
//        for (int i = 0; i < task1.size(); i++) {
//            if(task1.get(i).getId() == id){
//                task1.add(i + 1, task);
//                this.tasks = new ArrayDeque<>(task1);
//            }
//        }
//        throw new IllegalArgumentException();
        List<Task> tasks1 = new ArrayList<>();
        Task current = this.tasks.peek();
        while (current != null && current.getId() != id) {
            tasks1.add(this.tasks.poll());
            current = this.tasks.peek();
        }
        if (current == null) {
            throw new IllegalArgumentException();
        }
        tasks1.add(this.tasks.poll());
        tasks1.add(task);
        while (!tasks1.isEmpty()) {
            this.tasks.addFirst(tasks1.remove(tasks1.size() - 1));
        }
    }

    @Override
    public void clear() {
        this.tasks.clear();
    }

    @Override
    public Task[] toArray() {
        Task[] tasks1 = new Task[this.tasks.size()];
        return this.tasks.toArray(tasks1);
    }

    @Override
    public void reschedule(Task first, Task second) {
        List<Task> task1 = this.toList();
        int firstIndex = task1.indexOf(first);
        int secondIndex = task1.indexOf(second);
        if (firstIndex != -1 && secondIndex != -1) {
            Collections.swap(task1, firstIndex, secondIndex);
            this.tasks = new ArrayDeque<>(task1);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Task> toList() {
        return new ArrayList<>(this.tasks);
    }

    @Override
    public void reverse() {
        List<Task> tasks = toList();
        Collections.reverse(tasks);
        this.tasks = new ArrayDeque<>(tasks);

    }

    @Override
    public Task find(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Task find(Task task) {
        return this.find(task.getId());

    }
}
