package com.casic.accessControl.marker.dto;

import com.casic.accessControl.marker.domain.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/12/6.
 */
public class OptionItemDto {
    private Long id;
    private String name;
    private String type;
    private Long cid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public static OptionItemDto convert2OptionItemDto(OptionItem optionItem){
        OptionItemDto optionItemDto = new OptionItemDto();
        optionItemDto.setId(optionItem.getId());
        optionItemDto.setName(optionItem.getName());
        optionItemDto.setType(optionItem.getType());
        optionItemDto.setCid(optionItem.getCid());
        return optionItemDto;
    }
    public static List<OptionItemDto> convert2OptionItemDtoList(List<OptionItem> optionItemList){
        List<OptionItemDto> optionItemDtos = new ArrayList<OptionItemDto>();
        for(OptionItem optionItem:optionItemList){
            optionItemDtos.add(convert2OptionItemDto(optionItem));
        }
        return optionItemDtos;
    }
}
