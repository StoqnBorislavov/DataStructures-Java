package core;

import model.File;
import model.SampleFile;
import shared.FileManager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FileExplorer implements FileManager {
    private Deque<File> memory;
    private File root;

    public FileExplorer() {
        this.root = new SampleFile(1, "Root");
        this.memory = new ArrayDeque<>();
    }

    @Override
    public void addInDirectory(int directoryNumber, File file) {
        File f = getFileByKey(file.getNumber());
        f.getChildren().add(file);
    }

    @Override
    public File getRoot() {
        return this.root;
    }

    @Override
    public File get(int number) {
        return this.getFileByKey(number);
    }

    @Override
    public List<File> getFilesInPath(File path) {
        return getFileByKey(path.getNumber()).getChildren();
    }

    @Override
    public Boolean deleteFile(File file) {
        try {
            if (file.getNumber() == this.root.getNumber()) {
                this.root = null;
                return true;
            }
            File f = getFile(true, file.getNumber());
            f.getChildren().removeIf(e -> e.getNumber() == file.getNumber());
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public void move(File file, File destination) {
        if (this.root.getNumber() == file.getNumber()) {
            throw new IllegalStateException();
        }
        this.deleteFile(file);
        this.getFileByKey(destination.getNumber()).getChildren().add(file);
    }

    @Override
    public Boolean contains(File file) {
        try {
            this.getFileByKey(file.getNumber());
            return true;
        } catch (IllegalStateException e) {
            return false;
        }

    }

    @Override
    public List<File> getInDepth() {
        List<File> depthFiles = new ArrayList<>();
        this.doDFS(this.root, depthFiles);
        return depthFiles;
    }

    private void doDFS(File file, List<File> depthFiles) {
        if(file == null){
            return;
        }
        depthFiles.add(file);
        for (File child : file.getChildren()) {
            this.doDFS(child, depthFiles);
        }
    }


    @Override
    public List<File> getInLevel() {
        List<File> levelFiles = new ArrayList<>();
        ArrayDeque<File> deque = new ArrayDeque<>();
        deque.offer(this.root);
        while (!deque.isEmpty()){
            File file = deque.poll();
            levelFiles.add(file);
            for (File child : file.getChildren()) {
                deque.offer(child);
            }
        }
        return levelFiles;

    }

    @Override
    public void cut(int number) {
        File fileByKey = this.getFileByKey(number);
        this.deleteFile(fileByKey);
        this.memory.push(fileByKey);
    }

    @Override
    public void paste(File destination) {
        this.getFileByKey(destination.getNumber())
                .getChildren().add(this.memory.pop());
    }

    @Override
    public Boolean isEmpty() {
        File file = this.root;
        for (File child : file.getChildren()) {
            return true;
        }
        return false;

    }

    @Override
    public String getAsString() {
            if (this.root == null) {
                return "";
            }
            StringBuilder buffer = new StringBuilder();
            print(this.root, buffer, "", "");
            return buffer.toString().trim();
        }

        private void print(File file, StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(file.getNumber());
            buffer.append(System.lineSeparator());
            List<File> children = file.getChildren();
            for (int i = 0; i < children.size(); i++) {
                File next = children.get(i);
                if (i < children.size() - 1) {
                    print(next, buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                } else {
                    print(next, buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
                }
            }
        }


    private File getFile(boolean returnParent, int key) {
        Deque<File> deque = new ArrayDeque();
        if (this.root.getNumber() == key) {
            return this.root;

        }
        deque.offer(this.root);
        while (!deque.isEmpty()) {
            File file1 = deque.poll();
            for (File child : file1.getChildren()) {
                if (child.getNumber() == key) {
                    return returnParent ? file1 : child;
                }
                deque.offer(child);
            }
        }
        throw new IllegalStateException("Node not found");
    }

    public File getFileByKey(int key) {
        return this.getFile(false, key);
    }

    public File getParentByKey(int key) {
        return this.getFile(true, key);
    }
}
