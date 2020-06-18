package ro.mpp.core.Service;

import ro.mpp.core.Domain.Node;

import java.util.List;

public interface INodeService {
    void add(String name, Integer totalCapacity);

    List<Node> getNodes();

    Node update(String name, Integer totalCapacity);

    //the nodes for which totalCapacity is greater than the sum of costs (from the associated Pods)
    List<Node> getAvailableNodes();

    // the method will verify if the pod cost is smaller than the current capacity and,if this is the case, assign the pod to the node. returns the missing cost value (how much more capacity would be needed to fulfill the entire cost)
    Integer trySchedulePod(Long nodeId, Long podId);

    void deleteSchedule(Long nodeId, Long podId);

}
