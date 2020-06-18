package ro.mpp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NodeDTO extends BaseDTO{
    private String name;
    private int totalCapacity;
}
