package com.example.springbootbatch.programmatic;

import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * DemoLineAggregator
 * 我们需要自己实现一个LineAggregator实现类，用于将DeviceCommand对象转成字符串，保存到batch-data.csv文件。
 * @Author 王洪悦.{javayue@yeah.net}
 * @Create 2017-08-06 下午9:32
 */
public class DemoLineAggregator implements LineAggregator<DeviceCommand>{

    @Override
    public String aggregate(DeviceCommand deviceCommand) {
        StringBuffer sb = new StringBuffer();
        sb.append(deviceCommand.getId());
        sb.append(",");
        sb.append(deviceCommand.getStatus());
        return sb.toString();
    }
}