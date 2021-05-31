package ieit.agh.edu.pl.botcompetitionarena.domain.bot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InvalidBotException extends Exception {
    private final List<String> messages;
}
