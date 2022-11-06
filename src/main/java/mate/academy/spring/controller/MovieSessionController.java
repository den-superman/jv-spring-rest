package mate.academy.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.MovieSessionRequestDto;
import mate.academy.spring.dto.MovieSessionResponseDto;
import mate.academy.spring.dto.mapper.MovieSessionDtoMapper;
import mate.academy.spring.model.MovieSession;
import mate.academy.spring.service.MovieSessionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private MovieSessionDtoMapper movieSessionDtoMapper;
    private MovieSessionService movieSessionService;

    @PostMapping
    public MovieSessionResponseDto create(
            @RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        return movieSessionDtoMapper.toDto(movieSessionService.add(
                movieSessionDtoMapper.toModel(movieSessionRequestDto)));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> findAvailable(
            @RequestParam Long movieId, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return movieSessionService.findAvailableSessions(movieId, date)
                .stream()
                .map(movieSessionDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public MovieSessionResponseDto update(
            @PathVariable Long id, @RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = movieSessionDtoMapper.toModel(movieSessionRequestDto);
        movieSessionService.update(movieSession);
        return movieSessionDtoMapper.toDto(movieSession);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieSessionService.delete(id);
    }
}