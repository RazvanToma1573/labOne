package ro.mpp.core.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.mpp.core.Domain.Pod;
import ro.mpp.core.Repository.PodRepository;

import java.util.List;

@Service
public class PodService implements IPodService {
    @Autowired
    private PodRepository podRepository;

    @Override
    public Pod add(String name, Integer cost) {
        Pod pod = new Pod(name, cost);
        podRepository.save(pod);
        return pod;
    }

    @Override
    public List<Pod> getPods() {
        return podRepository.findAll();
    }

    @Override
    public List<Pod> getAvailablePods() {
        return null;
    }

    @Override
    public Pod delete(Long podId) {
        Pod pod = podRepository.findOne(podId);
        podRepository.delete(podId);
        return pod;
    }
}
