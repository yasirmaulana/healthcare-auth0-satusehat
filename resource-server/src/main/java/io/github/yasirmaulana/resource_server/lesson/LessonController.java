package io.github.yasirmaulana.resource_server.lesson;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class LessonController {

    @GetMapping("/lessons")
    public List<Lesson> getLesson() {
        return Arrays.asList(
                new Lesson(
                    "Beginer Golf Basics",
                    "An introduction",
                    "Jon doe",
                    LocalDateTime.of(2024, 11, 5, 10, 0)
                ),
                new Lesson(
                    "Advanced swing technique",
                    "improve your swing with advanced technique",
                    "Jane smith",
                    LocalDateTime.of(2024, 11, 6, 14, 0)
                )
        );
    }
}
