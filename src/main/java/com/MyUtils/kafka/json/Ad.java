package com.MyUtils.kafka.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ad {

    //广告主
    private String mediaid;
    //成交价
    private long auctionprice;
    //时间戳 long
    private long callid;
    private String sid;
    private String date;
    private String key;

    public Ad(String mediaid, long auctionprice, long callid, String sid) {
        this.mediaid = mediaid;
        this.auctionprice = auctionprice;
        this.callid = callid;
        this.sid = sid;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(callid), ZoneId.systemDefault());
        //测试 到分钟
        String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        this.key = date + mediaid;
        this.key = mediaid;
    }


    public void setCallid(long callid) {
        this.callid = callid;
    }
}
