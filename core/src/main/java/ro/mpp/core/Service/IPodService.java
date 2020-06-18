package ro.mpp.core.Service;

import ro.mpp.core.Domain.Pod;

import java.util.List;

public interface IPodService {
    Pod add(String name, Integer cost);

    List<Pod> getPods();

    //returns all pods which are currently not scheduled on any node.
    List<Pod> getAvailablePods();

    Pod delete(Long podId);

}
