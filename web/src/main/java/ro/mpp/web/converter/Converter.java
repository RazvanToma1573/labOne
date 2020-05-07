package ro.mpp.web.converter;

import ro.mpp.core.Domain.BaseEntity;
import ro.mpp.web.dto.BaseDTO;

public interface Converter<Model extends BaseEntity<Integer>, DTO extends BaseDTO> {
    Model convertDTOtoModel(DTO dto);
    DTO convertModelToDTO(Model model);
}
