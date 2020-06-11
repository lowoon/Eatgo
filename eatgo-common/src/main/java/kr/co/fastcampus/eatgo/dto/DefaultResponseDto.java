package kr.co.fastcampus.eatgo.dto;

public class DefaultResponseDto<T> {
    private Integer code;
    private String message;
    private T data;

    public DefaultResponseDto(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static DefaultResponseDto<Void> error() {
        return new DefaultResponseDto<>(null, "Error", null);
    }

    public static <T> DefaultResponseDto<T> of(T data) {
        return new DefaultResponseDto<>(null, null, data);
    }
}
