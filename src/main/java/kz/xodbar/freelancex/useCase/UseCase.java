package kz.xodbar.freelancex.useCase;

public interface UseCase<INPUT, OUTPUT> {
    OUTPUT handle(INPUT input);
}
