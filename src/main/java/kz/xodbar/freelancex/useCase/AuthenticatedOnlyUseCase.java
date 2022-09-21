package kz.xodbar.freelancex.useCase;

public interface AuthenticatedOnlyUseCase <INPUT, TOKEN_TYPE extends String, OUTPUT> {
    OUTPUT handle(INPUT input, TOKEN_TYPE token);
}
