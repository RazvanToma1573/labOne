package ro.mpp.core.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.Node;
import ro.mpp.core.Repository.NodeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService implements INodeService {
    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public void add(String name, Integer totalCapacity) {
        Node node = Node.builder()
                .name(name)
                .totalCapacity(totalCapacity)
                .build();
        nodeRepository.save(node);
    }

    @Override
    public List<Node> getNodes() {
        return nodeRepository.findAll();
    }

    @Override
    @Transactional
    public Node update(String name, Integer totalCapacity) {
        Node node = getNodes().stream().filter(n -> n.getName().equals(name)).collect(Collectors.toList()).get(0);
        node.setTotalCapacity(totalCapacity);
        return node;
    }

    @Override
    public List<Node> getAvailableNodes() {
        return null;
    }

    @Override
    public Integer trySchedulePod(Long nodeId, Long podId) {
        return null;
    }

    @Override
    public void deleteSchedule(Long nodeId, Long podId) {

    }
}
