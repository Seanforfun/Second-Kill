package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.FormatUtils;
import io.seanforfun.seckill.validator.isVin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 12:26
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Getter
public class Vehicle {
    // Static fields
    // Vehicle status
    public static final int VEHICLE_NEW = 0;
    public static final int VEHICLE_USED = 1;
    public static final int VEHICLE_CERTIFIED_PREOWNED = 2;

    private static final String VEHICLE_NEW_STR = "NEW";
    private static final String VEHICLE_USED_STR = "USED";
    private static final String VEHICLE_CERTIFIED_PREOWNED_STR = "CERTIFIED PREOWNED";

    private static Map<Integer, String> VEHICEL_STATUS_MAP = null;

    // Transmission Type
    public static final int AUTOMATIC = 0;
    public static final int MANUAL = 1;
    public static final int OTHER = 2;

    private static final String AUTOMATIC_STR = "AUTOMATIC";
    private static final String MANUAL_STR = "MANUAL";
    private static final String OTHER_STR = "OTHER";

    private static Map<Integer, String> TRANSMISSION_TYPE_MAP = null;

    // process
    public static final int VEHICLE_ON_SALE = 0;
    public static final int VEHICLE_ORDERED = 1;
    public static final int VEHICLE_MONEY_RECEIVED = 2;
    public static final int VEHICLE_TRANSACTION_FINISHED = 3;
    public static final int VEHICLE_UNEXPECTED_PENDING = -1;

    private static final String VEHICLE_ON_SALE_STR = "ON SALE";
    private static final String VEHICLE_ORDERED_STR = "ORDER RECEIVED";
    private static final String VEHICLE_MONEY_RECEIVED_STR = "MONEY RECEIVED";
    private static final String VEHICLE_TRANSACTION_FINISHED_STR = "TRANSACTION FINISHED";
    private static final String VEHICLE_UNEXPECTED_PENDING_STR = "UNEXPECTED PENDING";

    private static  Map<Integer, String> VEHICLE_PROCESS_MAP = null;

    // Modify
    public static final Long VEHICLE_NEVER_MODIFIED = 0L;
    public static final String VEHICLE_NEVER_MODIFIED_STR = "NEVER MODIFIED";
    public static final Long VEHICLE_NEVER_MODIFIED_USER = -1L;

    static{
        VEHICEL_STATUS_MAP = new HashMap<>();
        VEHICEL_STATUS_MAP.put(VEHICLE_NEW, VEHICLE_NEW_STR);
        VEHICEL_STATUS_MAP.put(VEHICLE_USED, VEHICLE_USED_STR);
        VEHICEL_STATUS_MAP.put(VEHICLE_CERTIFIED_PREOWNED, VEHICLE_CERTIFIED_PREOWNED_STR);
        TRANSMISSION_TYPE_MAP = new HashMap<>();
        TRANSMISSION_TYPE_MAP.put(AUTOMATIC, AUTOMATIC_STR);
        TRANSMISSION_TYPE_MAP.put(MANUAL, MANUAL_STR);
        TRANSMISSION_TYPE_MAP.put(OTHER, OTHER_STR);
        VEHICLE_PROCESS_MAP = new HashMap<>();
        VEHICLE_PROCESS_MAP.put(VEHICLE_ON_SALE, VEHICLE_ON_SALE_STR);
        VEHICLE_PROCESS_MAP.put(VEHICLE_ORDERED, VEHICLE_ORDERED_STR);
        VEHICLE_PROCESS_MAP.put(VEHICLE_MONEY_RECEIVED, VEHICLE_MONEY_RECEIVED_STR);
        VEHICLE_PROCESS_MAP.put(VEHICLE_TRANSACTION_FINISHED, VEHICLE_TRANSACTION_FINISHED_STR);
        VEHICLE_PROCESS_MAP.put(VEHICLE_UNEXPECTED_PENDING, VEHICLE_UNEXPECTED_PENDING_STR);
    }

    // Main fields
    @Setter
    private Long id;
    @Setter
    @isVin
    private String vin;
    @Setter
    @NotNull
    private String make;
    @Setter
    @NotNull
    private String model;
    @Setter
    @NotNull
    private Integer year;
    @Setter
    private Long creatorId;
    @Setter
    private Long lastModifierId;
    @NotNull
    private Integer status;
    @NotNull
    private Double price;
    @NotNull
    private Integer transmission;
    @NotNull
    private Integer process;
    private Long createTime;    //Vo
    private Long lastModifyTime;    //Vo

    //Vo fields
    private String statusVo;
    private String priceVo;
    private String transmissionVo;
    private String processVo;
    private String createTimeVo;
    private String lastModifyTimeVo;

    public void setStatus(Integer status) {
        this.status = status;
        this.statusVo = VEHICEL_STATUS_MAP.get(status);
    }

    public void setPrice(Double price) {
        this.price = price;
        this.priceVo = FormatUtils.formatPrice(price);
    }

    public void setTransmission(Integer transmission) {
        this.transmission = transmission;
        this.transmissionVo = TRANSMISSION_TYPE_MAP.get(transmission);
    }

    public void setProcess(Integer process) {
        this.process = process;
        this.processVo = VEHICLE_PROCESS_MAP.get(process);
    }


    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
        this.createTimeVo = FormatUtils.formatDateTime(createTime);
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        this.lastModifyTimeVo = lastModifyTime.equals(VEHICLE_NEVER_MODIFIED) ?
                VEHICLE_NEVER_MODIFIED_STR :
                FormatUtils.formatDateTime(lastModifyTime);
    }
}
