package com.joetymatthews.forum.discussion.sub;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.discussion.Discussion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.spy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
public class SubDiscussionServiceIntegrationTest {

    @Autowired
    private SubDiscussionService subDiscussionService;

    private final Discussion discussion = spy(TestUtil.createDiscussion());
    private final SubDiscussion subDiscussion = TestUtil.createSubDiscussion();
    private final SubDiscussionDTO dto = TestUtil.createSubDiscussionDTO();

    @Test
    public void createSubDiscussion() {}
}
