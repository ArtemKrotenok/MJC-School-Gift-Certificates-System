package com.epam.esm.service;

import com.epam.esm.service.model.TagDTO;

public interface TagService {

    String add(TagDTO tagDTO);

    String getAllTagsByPageSorted(Integer page);

    String getAllTagsSorted();

    String findById(Long id);

    String deleteById(Long id);
}
