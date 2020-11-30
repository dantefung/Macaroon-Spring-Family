package com.example.springbootbatch.programmatic;

import org.springframework.batch.item.ItemProcessor;

/**
 * Processor
 * 读完数据后，我们就需要处理数据了。既然我们前面从文件里读取了待下发的命令，那么在这里下发命令给设备是最好的时机。
 * @Author 王洪悦.{javayue@yeah.net}
 * @Create 2017-08-06 下午9:12
 */
public class DemoProcessor implements ItemProcessor<DeviceCommand, DeviceCommand>{

    @Override
    public DeviceCommand process(DeviceCommand deviceCommand) throws Exception {
        // 模拟下发命令给设备
        System.out.println("send command to device, id=" + deviceCommand.getId());

        // 更新命令状态
        deviceCommand.setStatus("SENT");

        // 返回命令对象
        return deviceCommand;

    }
}