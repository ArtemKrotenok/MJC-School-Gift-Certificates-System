package com.epam.esm.repository;

import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface TagRepository extends GenericRepository<Tag> {

    Tag getTagByName(String name);

    List<Tag> getAllTagByPageSorted(int startPosition, int itemsByPage);

    List<Tag> getAllTagSorted();

    List<Tag> updateTagList(List<Tag> tagList);
}
