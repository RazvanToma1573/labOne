package mpp.socket.server.Repository;


import mpp.socket.common.Domain.Problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProblemFileRepository extends FileRepository<Integer, Problem> {

    public ProblemFileRepository(String filePath) {
        super(filePath);
        this.readFromFile();
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
