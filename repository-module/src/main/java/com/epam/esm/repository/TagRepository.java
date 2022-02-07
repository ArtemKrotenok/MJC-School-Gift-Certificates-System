package com.epam.esm.repository;

import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface TagRepository extends GenericRepository<Tag> {

    Tag findByName(String name);

    List<Tag> getAllByPageSorted(int startPosition, int itemsByPage);

    List<Tag> getAllSorted();

    List<Tag> updateTags(List<Tag> tags);
}
