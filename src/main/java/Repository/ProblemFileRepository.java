package Repository;

import Domain.Problem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ProblemFileRepository extends FileRepository<Integer, Problem> {

    public ProblemFileRepository(String filePath) {
        super(filePath);
        this.readFromFile();
    }

    @Override
    public Optional<Problem> findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Problem> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Problem> save(Problem entity) {
        return super.save(entity);
    }

    @Override
    public Optional<Problem> delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public Optional<Problem> update(Problem entity) {
        return super.update(entity);
    }

    @Override
    protected void writeToFile() {
        super.writeToFile();
    }

    private void readFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(super.filePath));
            bufferedReader.lines().forEach(entry -> {
                Problem problem = new Problem(entry.split(",")[1], entry.split(",")[2]);
                problem.setId(Integer.valueOf(entry.split(",")[0]));
                super.save(problem);
            });
            bufferedReader.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
