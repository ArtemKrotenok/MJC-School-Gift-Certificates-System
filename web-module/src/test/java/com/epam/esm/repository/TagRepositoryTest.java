package com.epam.esm.repository;

import com.epam.esm.app.config.AppConfig;
import com.epam.esm.app.config.AppConfigTest;
import com.epam.esm.repository.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, AppConfigTest.class})
@ActiveProfiles("test")
class TagRepositoryTest {

    public static final String TEST_TAG_NAME_EXIST_IN_DB = "sport";
    public static final String TEST_TAG_NAME_NO_EXIST_IN_DB = "extreme";
    public static final long TEST_TAG_ID_EXIST_IN_DB = 3L;
    public static final long TEST_TAG_ID_FOR_DELETE = 9L;
    public static final long TEST_TAG_ID_NO_EXIST_IN_DB = 30L;
    public static final int START_POSITION = 0;
    public static final int ITEMS_BY_PAGE = 3;
    public static final String NEW_TAG_NAME = "new_name";
    @Autowired
    private TagRepository tagRepository;

    @Test
    void findAll_returnsTags() {
        List<Tag> tags = tagRepository.findAll();
        assertEquals(tags.size(), tagRepository.count());
    }

    @Test
    void add_givenValidTag_returnsIdNewRecord() {
        Tag tag = TestRepositoryDataUtil.getValidTag();
        Long idNewRecord = tagRepository.add(tag);
        assertThat(idNewRecord).isNotNull();
        assertTrue(idNewRecord > 0);
    }

    @Test
    void add_givenValidTag_returnsCountTagInDBIncrement() {
        long tagsBeforeAdd = tagRepository.count();
        Tag tag = TestRepositoryDataUtil.getValidTag();
        tagRepository.add(tag);
        long tagsAfterAdd = tagRepository.count();
        assertEquals(tagsAfterAdd - tagsBeforeAdd, 1);
    }

    @Test
    void getTagByName_givenValidName_returnsValidTag() {
        Tag tag = tagRepository.findByName(TEST_TAG_NAME_EXIST_IN_DB);
        assertThat(tag).isNotNull();
        assertEquals(tag.getName(), TEST_TAG_NAME_EXIST_IN_DB);
    }

    @Test
    void getTagByName_givenInvalidName_returnsNull() {
        Tag tag = tagRepository.findByName(TEST_TAG_NAME_NO_EXIST_IN_DB);
        assertThat(tag).isNull();
    }

    @Test
    void getAllTagSorted_returnsTags() {
        List<Tag> tags = tagRepository.getAllSorted();
        assertFalse(tags.isEmpty());
        assertTrue(isSorted(tags));
    }

    @Test
    void getAllTagByPageSorted_returnsSortedTags() {
        List<Tag> tags = tagRepository.getAllByPageSorted(START_POSITION, ITEMS_BY_PAGE);
        assertFalse(tags.isEmpty());
        assertTrue(isSorted(tags));
        assertEquals(tags.size(), ITEMS_BY_PAGE);
    }

    @Test
    void findById_givenValidId_returnsTag() {
        Tag tag = tagRepository.findById(TEST_TAG_ID_EXIST_IN_DB);
        assertThat(tag).isNotNull();
        assertEquals(tag.getId(), TEST_TAG_ID_EXIST_IN_DB);
    }

    @Test
    void findById_givenInvalidId_returnsNull() {
        Tag tag = tagRepository.findById(TEST_TAG_ID_NO_EXIST_IN_DB);
        assertThat(tag).isNull();
    }

    @Test
    void delete_givenValidId_returnsRecordEffectOne() {
        Tag tag = tagRepository.findById(TEST_TAG_ID_FOR_DELETE);
        assertThat(tag).isNotNull();
        int effectRows = tagRepository.delete(tag);
        assertEquals(effectRows, 1);
        Tag deletedTag = tagRepository.findById(TEST_TAG_ID_FOR_DELETE);
        assertThat(deletedTag).isNull();
    }

    @Test
    void update_givenValidTag_returnsRecordEffectOne() {
        Tag dbTag = tagRepository.findById(TEST_TAG_ID_EXIST_IN_DB);
        dbTag.setName(NEW_TAG_NAME);
        int effectRows = tagRepository.update(dbTag);
        assertEquals(effectRows, 1);
        Tag updateTag = tagRepository.findById(TEST_TAG_ID_EXIST_IN_DB);
        assertEquals(updateTag.getName(), NEW_TAG_NAME);
    }

    private boolean isSorted(List<Tag> tags) {
        Tag preTag = tags.get(0);
        boolean isSorted = true;
        for (Tag tag : tags) {
            if (preTag.getName().compareTo(tag.getName()) > 0) {
                isSorted = false;
            }
            preTag = tag;
        }
        return isSorted;
    }
}