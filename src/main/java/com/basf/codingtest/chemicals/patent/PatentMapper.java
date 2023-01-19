package com.basf.codingtest.chemicals.patent;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatentMapper {

    private ModelMapper modelMapper;

    public Patent toModel(PatentDto dto) {
        return modelMapper.map(dto, Patent.class);
    }

    public PatentDto toDto(Patent model) {
        return modelMapper.map(model, PatentDto.class);
    }
}
