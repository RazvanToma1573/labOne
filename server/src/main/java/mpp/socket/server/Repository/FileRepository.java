package mpp.socket.server.Repository;

import mpp.socket.server.Domain.BaseEntity;

import java.io.*;
import java.util.Optional;

public class FileRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {
    protected String filePath;

    public FileRepository(String filePath) {
        super();
        this.filePath = filePath;
    }

    @Override
    public Optional<T> findOne(ID id) {
        return  super.findOne(id);
    }

    @Override
    public Iterable<T> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<T> save(T entity) {
        Optional<T> savedEntity = super.save(entity);
        this.writeToFile();
        return savedEntity;
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<T> deletedEntity =  super.delete(id);
        this.writeToFile();
        return deletedEntity;
    }

    @Override
    public Optional<T> update(T entity) {
        Optional<T> updatedEntity =  super.update(entity);
        this.writeToFile();
        return updatedEntity;
    }

    protected void writeToFile() {
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, false)));
            super.findAll().forEach(printWriter::println);
            printWriter.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
