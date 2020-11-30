package com.example.springbootbatch.programmatic;

/**
 * 由于我们的数据源是命令数据，所以我们需要创建一个DeviceCommand.java文件
 *
 * @Author 王洪悦.{javayue@yeah.net}
 * @Create 2017-08-06 下午9:09
 */
public class DeviceCommand {

    private String id;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeviceCommand{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}