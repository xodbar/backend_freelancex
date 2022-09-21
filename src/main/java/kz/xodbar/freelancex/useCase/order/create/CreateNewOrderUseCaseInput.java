package kz.xodbar.freelancex.useCase.order.create;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateNewOrderUseCaseInput {
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private LocalDateTime deadline;
    @NonNull
    private Integer price;
    @NonNull
    private String fieldName;
}
