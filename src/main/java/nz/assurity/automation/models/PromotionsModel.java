package nz.assurity.automation.models;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class PromotionsModel {
    @NonNull
    private int Id;
    @NonNull
    private String Name;
    @NonNull
    private String Description;
    @NonNull
    private float Price;
    @NonNull
    private float OriginalPrice;
    @NonNull
    private boolean Recommended;
    @NonNull
    private int MinimumPhotoCount;
}
