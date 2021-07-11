package com.cr.rentalsystem.pojo;
import lombok.*;

@AllArgsConstructor
@Setter @Getter
@NoArgsConstructor
@ToString
public class VehicleDetail {
	private String type;
	private String subType;
	private int availableQty;
}
