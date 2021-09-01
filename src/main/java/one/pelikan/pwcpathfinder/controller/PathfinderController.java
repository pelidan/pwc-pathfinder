package one.pelikan.pwcpathfinder.controller;

import lombok.RequiredArgsConstructor;
import one.pelikan.pwcpathfinder.dto.Route;
import one.pelikan.pwcpathfinder.service.PathfinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
@Validated
public class PathfinderController {

    private static final String COUNTRY_CODE_PATTERN = "[a-zA-Z]{3}";

    private final PathfinderService pathfinderService;


    @GetMapping("/{origin}/{destination}")
    public Route findRoute(@PathVariable @Pattern(regexp = COUNTRY_CODE_PATTERN) final String origin,
                           @PathVariable @Pattern(regexp = COUNTRY_CODE_PATTERN) final String destination) {
        return pathfinderService.findRoute(origin, destination);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleValidationException(ConstraintViolationException e, WebRequest request) {
        return new ResponseEntity<>("400 BAD_REQUEST \"Illegal country code format\"", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
