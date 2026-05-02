package be.riddler.v1.answer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Getter
public class Answer {
    private UUID id;
    private String value;
    private UUID questionId;
}
