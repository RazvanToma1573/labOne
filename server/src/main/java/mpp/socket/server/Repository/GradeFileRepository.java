package mpp.socket.server.Repository;



import mpp.socket.common.Domain.Grade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;


public class GradeFileRepository extends FileRepository<Integer, Grade> {

    public GradeFileRepository(String filePath) {
        super(filePath);
        this.readFromFile();
    }

    @Override
    public Optional<Grade> findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Grade> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Grade> save(Grade entity) {
        return super.save(entity);
    }

    @Override
    public Optional<Grade> delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public Optional<Grade> update(Grade entity) {
        return super.update(entity);
    }

    @Override
    protected void writeToFile() {
        super.writeToFile();
    }

    private void readFromFile() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.filePath));
            bufferedReader.lines().forEach(entry -> {
                int studentId = Integer.parseInt(entry.split(",")[0]);
                int problemId = Integer.parseInt(entry.split(",")[1]);
                Grade grade = new Grade(studentId, problemId, Integer.parseInt(entry.split(",")[2]));
                super.save(grade);
            });
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
