package com.joetymatthews.forum;

import com.joetymatthews.forum.discussion.*;
import com.joetymatthews.forum.discussion.sub.SubDiscussionControllerIntegrationTest;
import com.joetymatthews.forum.discussion.sub.SubDiscussionControllerUnitTest;
import com.joetymatthews.forum.discussion.sub.SubDiscussionServiceIntegrationTest;
import com.joetymatthews.forum.discussion.sub.SubDiscussionServiceUnitTest;
import com.joetymatthews.forum.section.*;
import com.joetymatthews.forum.thread.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectionRepositoryIntegrationTest.class,
        SectionServiceUnitTest.class,
        SectionServiceIntegrationTest.class,
        SectionControllerUnitTest.class,
        SectionControllerIntegrationTest.class,

        DiscussionRepositoryIntegrationTest.class,
        DiscussionServiceUnitTest.class,
        DiscussionServiceIntegrationTest.class,
        DiscussionControllerUnitTest.class,
        DiscussionControllerIntegrationTest.class,

        SubDiscussionServiceUnitTest.class,
        SubDiscussionServiceIntegrationTest.class,
        SubDiscussionControllerUnitTest.class,
        SubDiscussionControllerIntegrationTest.class

        ThreadRepositoryIntegrationTest.class,
        ThreadServiceUnitTest.class,
        ThreadServiceIntegrationTest.class,
        ThreadControllerUnitTest.class,
        ThreadControllerIntegrationTest.class
})
public class ForumApplicationTestSuite {}
