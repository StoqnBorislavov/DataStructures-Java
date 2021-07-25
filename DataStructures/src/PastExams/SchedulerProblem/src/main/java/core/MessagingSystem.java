package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.ArrayList;
import java.util.List;

public class MessagingSystem implements DataTransferSystem {
    private Node root;
    private int size;

    private static class Node {
        Message message;
        Node leftChild;
        Node rightChild;

        Node(Message message) {
            this.message = message;
        }
    }

    public MessagingSystem() {
    }

    @Override
    public void add(Message message) {
        Node newNode = new Node(message);
        if (this.root == null) {
            this.root = newNode;
        } else {
            Node current = this.root;
            Node prev = current;
            while (current != null) {
                prev = current;
                if (current.message.getWeight() > newNode.message.getWeight()) {
                    current = current.leftChild;
                } else if (current.message.getWeight() < newNode.message.getWeight()) {
                    current = current.rightChild;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            current = newNode;
            if (prev.message.getWeight() < current.message.getWeight()) {
                prev.rightChild = current;
            } else {
                prev.leftChild = current;
            }
        }
        this.size++;
    }

    @Override
    public Message getByWeight(int weight) {
        Node current = this.root;
        while (current != null) {
            if (current.message.getWeight() < weight) {
                current = current.rightChild;
            } else if (current.message.getWeight() > weight) {
                current = current.leftChild;
            } else {
                return current.message;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Message getLightest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node current = this.root;
        Node prev = current;

        while (current != null) {
            prev = current;
            current = current.leftChild;
        }

        return prev.message;
    }

    @Override
    public Message getHeaviest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node current = this.root;
        Node prev = current;

        while (current != null) {
            prev = current;
            current = current.rightChild;
        }

        return prev.message;
    }

    @Override
    public Message deleteLightest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node current = this.root;
        Node prev = current;

        while (current != null) {
            prev = current;
            current = current.leftChild;
        }

        Message message;
        if (prev.leftChild == null) {
            message = prev.message;
            this.root = null;
        } else {
            message = prev.leftChild.message;
            prev.leftChild = null;
        }

        this.size--;
        return message;
    }

    @Override
    public Message deleteHeaviest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node current = this.root;
        Node prev = current;

        while (current != null) {
            prev = current;
            current = current.rightChild;
        }

        Message message;
        if (prev.rightChild == null) {
            message = prev.message;
            this.root = null;
        } else {
            message = prev.rightChild.message;
            prev.rightChild = null;
        }

        this.size--;
        return message;
    }

    @Override
    public Boolean contains(Message message) {
        try {
            this.getByWeight(message.getWeight());
        } catch (IllegalArgumentException ignored) {
            return false;
        }
        return true;
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return this.getInOrder();

    }

    private void nodeInOrder(Node node, List<Message> result) {
        if (node == null) {
            return;
        }
        this.nodeInOrder(node.leftChild, result);
        result.add(node.message);
        this.nodeInOrder(node.rightChild, result);
    }

    @Override
    public List<Message> getPostOrder() {
        List<Message> result = new ArrayList<>();

        this.nodePostOrder(this.root, result);
        return result;
    }

    private void nodePostOrder(Node node, List<Message> result) {
        if (node == null) {
            return;
        }
        this.nodePostOrder(node.leftChild, result);
        this.nodePostOrder(node.rightChild, result);
        result.add(node.message);
    }

    @Override
    public List<Message> getPreOrder() {
        List<Message> result = new ArrayList<>();

        this.nodePreOrder(this.root, result);
        return result;
    }

    private void nodePreOrder(Node node, List<Message> result) {
        if (node == null) {
            return;
        }
        result.add(node.message);
        this.nodeInOrder(node.leftChild, result);
        this.nodeInOrder(node.rightChild, result);
    }

    @Override
    public List<Message> getInOrder() {
        List<Message> result = new ArrayList<>();
        this.nodeInOrder(this.root, result);
        return result;
    }

    @Override
    public int size() {
        return this.size;
    }
}
