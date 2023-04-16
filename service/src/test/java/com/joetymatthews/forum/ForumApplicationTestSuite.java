package com.joetymatthews.forum;

import com.joetymatthews.forum.section.SectionControllerUnitTest;
import com.joetymatthews.forum.section.SectionRepositoryIntegrationTest;
import com.joetymatthews.forum.section.SectionServiceIntegrationTest;
import com.joetymatthews.forum.section.SectionServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectionRepositoryIntegrationTest.class,
        SectionServiceUnitTest.class,
        SectionServiceIntegrationTest.class,
        SectionControllerUnitTest.class,
})
public class ForumApplicationTestSuite {}
