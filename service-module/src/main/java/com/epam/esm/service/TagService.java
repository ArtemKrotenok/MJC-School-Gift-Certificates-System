package com.epam.esm.service;

import com.epam.esm.service.model.TagDTO;

import java.util.List;

public interface TagService {

    void create(TagDTO tagDTO);

    List<TagDTO> getAllByPageSorted(Integer page);

    List<TagDTO> getAllSorted();

    TagDTO findById(Long id);

    void deleteById(Long id);
}