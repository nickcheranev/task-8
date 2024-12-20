package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskCommandTest {
    @Mock
    private Resource taskResource;
    @InjectMocks
    private TaskCommand sut;

    @Test
    void locale() throws IOException {
        when(taskResource.getContentAsString(Charset.defaultCharset())).thenReturn("content");

        var taskText = sut.task();

        assertThat(taskText).isEqualTo("content");
        verify(taskResource).getContentAsString(Charset.defaultCharset());
    }
}