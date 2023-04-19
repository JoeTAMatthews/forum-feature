package com.joetymatthews.forum;

import com.joetymatthews.forum.discussion.Discussion;
import com.joetymatthews.forum.discussion.DiscussionDTO;
import com.joetymatthews.forum.discussion.sub.SubDiscussion;
import com.joetymatthews.forum.discussion.sub.SubDiscussionDTO;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import com.joetymatthews.forum.section.Section;
import com.joetymatthews.forum.section.SectionDTO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtil {

    public static Section createSection() {
        return new Section("hi", 0);
    }

    public static Discussion createDiscussion() {
        return new Discussion("1", "How are you?");
    }

    public static Flux<Discussion> createDiscussions() {
        return Flux.just(new Discussion("1", "How are you?"), new Discussion("1", "Listen to them"));
    }

    public static SubDiscussion createSubDiscussion() {
        return new SubDiscussion("How are you?");
    }

    public static Thread thread() {
        return new Thread("Post!", "<h1>Post</h1>", "user");
    }

    public static SectionDTO createSectionDTO() {
        return new SectionDTO("creator", 1);
    }

    public static DiscussionDTO createDiscussionDTO() {
        return new DiscussionDTO("1", "How are you?");
    }

    public static SubDiscussionDTO createSubDiscussionDTO(Discussion discussion) {
        return new SubDiscussionDTO(discussion.getId(), "How are you?");
    }

    public static SubDiscussionDTO createSubDiscussionDTO() {
        return new SubDiscussionDTO("1", "How are you?");
    }

    public static ThreadDTO threadDTO() {
        return new ThreadDTO("Post!", "<h1>Post</h1>");
    }

    public static void assertSection(Section section) {
        assertThat(section.getName()).isEqualTo("hi");
        assertThat(section.getOrder()).isEqualTo(0);
    }

    public static void assertDiscussion(Discussion discussion) {
        assertThat(discussion.getTitle()).isEqualTo("How are you?");
        assertThat(discussion.getSectionId()).isEqualTo("1");
    }

    public static void assertDiscussions(List<Discussion> discussionList) {
        assertThat(discussionList.size()).isEqualTo(2);
        assertThat(discussionList.get(0).getTitle()).isEqualTo("How are you?");
        assertThat(discussionList.get(0).getSectionId()).isEqualTo("1");
        assertThat(discussionList.get(1).getTitle()).isEqualTo("Listen to them");
        assertThat(discussionList.get(1).getSectionId()).isEqualTo("1");
    }

    public static void assertSubDiscussion(SubDiscussion subDiscussion) {
        assertThat(subDiscussion.getTitle()).isEqualTo("How are you?");
    }

    public static void assertThread(Thread thread) {
        assertThat(thread.getTitle()).isEqualTo("Post!");
        assertThat(thread.getContent()).isEqualTo("<h1>Post</h1>");
        assertThat(thread.getUserId()).isEqualTo("user");
    }

    public static ServerHttpRequest httpRequest() {
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        when(request.getURI()).thenReturn(URI.create("http://localhost:8080"));
        return request;
    }

    public static void assertSubDiscussions(List<SubDiscussion> subDiscussions) {
        assertThat(subDiscussions.size()).isEqualTo(1);
        assertThat(subDiscussions.get(0).getTitle()).isEqualTo("How are you?");
    }

    public static void assertThread(Thread thread) {
        assertThat(thread.getTitle()).isEqualTo("Post!");
        assertThat(thread.getContent()).isEqualTo("<h1>Post</h1>");
        assertThat(thread.getUserId()).isEqualTo("user");
    }

    public static ServerHttpRequest httpRequest() {
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        when(request.getURI()).thenReturn(URI.create("http://localhost:8080"));
        return request;
    }
}
