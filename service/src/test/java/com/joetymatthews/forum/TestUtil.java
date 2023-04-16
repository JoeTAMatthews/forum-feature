package com.joetymatthews.forum;

import com.joetymatthews.forum.section.Section;
import com.joetymatthews.forum.section.SectionDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestUtil {

    public static Section createSection() {
        return new Section("hi", 0);
    }

    public static SectionDTO createSectionDTO() {
        return new SectionDTO("creator", 1);
    }

    public static void assertSection(Section section) {
        assertThat(section.getName()).isEqualTo("hi");
        assertThat(section.getOrder()).isEqualTo(0);
    }
}
