package com.example.springbootbatch.programmatic;

import org.springframework.batch.item.file.LineMapper;

/**
 * 我们需要自己实现一个LineMapper实现类，用于将batch-data.csv文件的每行数据，转成程序方便处理的DeviceCommand对象。
 * LineMapper则表示如何将文件的每行数据转成对应的DTO对象。
 * @Author 王洪悦.{javayue@yeah.net}
 * @Create 2017-08-06 下午9:10
 */
public class DemoLineMapper implements LineMapper<DeviceCommand> {

    @Override
    public DeviceCommand mapLine(String line, int lineNumber) throws Exception {
        // 逗号分割每一行数据
        String[] args = line.split(",");

        // 创建DeviceCommand对象
        DeviceCommand deviceCommand = new DeviceCommand();

        // 设置id值到对象中
        deviceCommand.setId(args[0]);

        // 设置status值到对象中
        deviceCommand.setStatus(args[1]);

        // 返回对象
        return deviceCommand;
    }
}