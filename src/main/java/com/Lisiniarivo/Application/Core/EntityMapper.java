package com.Lisiniarivo.Application.Core;

import org.modelmapper.ModelMapper;

public class EntityMapper {
	
	public static <T, D> D toDto(T entity, Class<D> dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPreferNestedProperties(false);
		return mapper.map(entity, dto);
	}
	
	public static <T, D> D toEntity(T dto, Class<D> entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPreferNestedProperties(false);
		return mapper.map(dto, entity);
	}

}
