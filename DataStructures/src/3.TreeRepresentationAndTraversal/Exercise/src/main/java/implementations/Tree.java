package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree() {
        this.children = new ArrayList<>();
    }

    public Tree(E key) {
        this.key = key;
        this.children = new ArrayList<>();
//        for (Tree<E> child : children) {
//            child.setParent(this);
//            this.children.add(child);
//        }
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();
        traverseTreeWithRecurrence(builder, 0, this);

        return builder.toString().trim();
    }

    public List<Tree<E>> traverseWithBFS() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        List<Tree<E>> allNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            allNodes.add(tree);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }
        return allNodes;
    }

    private void traverseTreeWithRecurrence(StringBuilder builder, int indent, Tree<E> eTree) {

        builder.append(this.getPadding(indent)).append(eTree.key).append(System.lineSeparator());

        for (Tree<E> child : eTree.children) {
            traverseTreeWithRecurrence(builder, indent + 2, child);
        }
    }

    private void traverseTreeWithRecurrence(List<Tree<E>> collection, Tree<E> eTree) {
        collection.add(eTree);
        for (Tree<E> child : eTree.children) {
            traverseTreeWithRecurrence(collection, child);
        }
    }

    private String getPadding(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFS().stream()
                .filter(tree -> tree.children.size() == 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();
        this.traverseTreeWithRecurrence(allNodes, this);

        return allNodes.stream()
                .filter(tree -> tree.getParent() != null && tree.children.size() > 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseWithBFS();

        int maxPath = 0;
        Tree<E> deepestLeftmostNode = null;
        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftmostNode = tree;
                }
            }
        }
        return deepestLeftmostNode;
    }

    public Tree<E> getDeepestLeftmostNodeDFS() {

        List<Tree<E>> deepestLeftmostNode = new ArrayList<>();
        int[] maxPath = new int[1];
        int max = 0;
        findDeepestNodeDFS(deepestLeftmostNode, maxPath, max, this);

        return deepestLeftmostNode.get(0);
    }

    private void findDeepestNodeDFS(List<Tree<E>> deepestLeftmostNode, int[] maxPath, int max, Tree<E> tree) {

        max++;

        if (max > maxPath[0]) {
            maxPath[0] = max;
            deepestLeftmostNode.set(0, tree);
        }
        for (Tree<E> child : tree.children) {
            findDeepestNodeDFS(deepestLeftmostNode, maxPath, max + 1, child);
        }
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        List<E> path = new ArrayList<>();
        Tree<E> current = getDeepestLeftmostNode();
        while (current != null) {
            path.add(current.getKey());
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> allPaths = getLeafs().stream().map(l -> l.getPath()).collect(Collectors.toList());
        List<List<E>> pathsWithGivenSum = new ArrayList<>();
        int currentSum = 0;
        for (List<E> path : allPaths) {
            for (E e : path) {
                currentSum += (Integer) e;
            }
            if (currentSum > sum) {
                break;
            }
            if (currentSum == sum) {
                pathsWithGivenSum.add(path);
            }
        }

        return pathsWithGivenSum;
    }

    private List<E> getPath() {
        ArrayDeque<E> stack = new ArrayDeque<>();
        Tree<E> currentTree = this;

        while (currentTree != null) {
            stack.push(currentTree.getKey());
            currentTree = currentTree.parent;
        }
        return new ArrayList<>(stack);
    }

    private List<Tree<E>> getLeafs() {
        List<Tree<E>> collection = new ArrayList<>();
        return getAllTreesDFS().stream().filter(t -> t.children.isEmpty()).collect(Collectors.toList());
    }

    private List<Tree<E>> getAllTreesDFS() {
        List<Tree<E>> result = new ArrayList<>();
        getAllTreesDFS(this, result);
        return result;
    }

    private void getAllTreesDFS(Tree<E> currentTree, List<Tree<E>> allTrees) {
        allTrees.add(currentTree);

        for (Tree<E> child : currentTree.children) {
            getAllTreesDFS(child, allTrees);
        }
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> allTrees = getAllTreesDFS();

        return allTrees.stream()
                .filter(t -> t.getSum() == sum)
                .collect(Collectors.toList());
    }


    private int getSum() {
        return getAllTreesDFS().stream()
                .map(t -> (Integer) t.key)
                .reduce(0, (accumulator, element) -> accumulator += element);
    }
}


