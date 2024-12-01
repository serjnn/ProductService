package com.serjnn.ProductService.mappers;

import com.serjnn.ProductService.dtos.CacheableDiscountDto;
import com.serjnn.ProductService.dtos.DiscountChangesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiscountMapper {
    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    @Mapping(source = "newDiscount", target = "discount")
    CacheableDiscountDto toCacheableDto(DiscountChangesDto dto);


}
