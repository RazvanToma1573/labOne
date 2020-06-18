package ro.mpp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.mpp.core.Domain.Node;
import ro.mpp.core.Service.INodeService;
import ro.mpp.web.dto.NodeDTO;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NodeController {
    @Autowired
    private INodeService nodeService;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleException2(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("Validation Error: Your input is not a valid one!" , HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    List<NodeDTO> getAllNodes() {
        List<Node> nodes = nodeService.getNodes();
        return new ArrayList<>(nodes.stream().map(n -> NodeDTO.builder().name(n.getName()).totalCapacity(n.getTotalCapacity()).build()).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    void addNode(@RequestBody NodeDTO nodeDTO) {
        System.out.println(nodeDTO);
        this.nodeService.add(nodeDTO.getName(), nodeDTO.getTotalCapacity());
    }
}
