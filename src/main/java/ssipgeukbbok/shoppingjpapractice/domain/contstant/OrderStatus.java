package ssipgeukbbok.shoppingjpapractice.domain.contstant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {


    ORDER("ORDER", "주문"),
    CANCEL("CANCEL", "취소")
    ;

    @Getter
    private final String orderStatus;
    @Getter private final String description;

}
