package com.cr.rentalsystem.pojo;
import lombok.*;

@AllArgsConstructor
@Setter @Getter
@NoArgsConstructor
@ToString
public class ResponseMessage {
	
	private boolean isError;
	private String errorMessage;
	private Object data;
}
