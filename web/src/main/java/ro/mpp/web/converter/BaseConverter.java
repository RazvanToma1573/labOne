package ro.mpp.web.converter;

import ro.mpp.core.Domain.BaseEntity;
import ro.mpp.web.dto.BaseDTO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseConverter<Model extends BaseEntity<Integer>, DTO extends BaseDTO>
        implements Converter<Model, DTO>{
    public List<Integer> convertModelsToIDs(List<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> convertDTOsToIDs(List<DTO> dtos) {
        return dtos.stream()
                .map(BaseDTO::getId)
                .collect(Collectors.toList());
    }

    public List<DTO> convertModelsToDTOs(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }

}
