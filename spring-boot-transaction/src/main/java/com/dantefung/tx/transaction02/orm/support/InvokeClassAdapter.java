package com.dantefung.tx.transaction02.orm.support;

import com.dantefung.tx.transaction02.orm.annotation.MyColumn;
import com.dantefung.tx.transaction02.orm.annotation.MyId;
import com.dantefung.tx.transaction02.orm.utils.MyStringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class InvokeClassAdapter {

    /**
     *@Description  将数据库中的一条记录封装成一个实体类
     *@Author DANTE FUNG
     *@Date 2021-03-01 23:30:12
     *@Param [resultSet, clazz]
     *@return T
     */
    public static <T> T invokeObject(ResultSet resultSet, Class<T> clazz){
        Map<String, Field> map = new HashMap<>(  );
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent( MyColumn.class )) {
					MyColumn annotation = field.getAnnotation( MyColumn.class );
                    String annotationColomnName = annotation.columnName() == null?field.getName(): annotation.columnName();
                    //将注解名称和属性存放到map中
                    map.put( annotationColomnName,  field);
                }else if (field.isAnnotationPresent( MyId.class )){
					MyId annotation = field.getAnnotation( MyId.class );
                    map.put( field.getName(), field );
                }
            }
            T obj = clazz.getDeclaredConstructor().newInstance();
            ResultSetMetaData metaData = null;

            metaData = resultSet.getMetaData();

            for (int i = 0;i < metaData.getColumnCount(); i++){
                String columnName = metaData.getColumnName( i + 1 );
                if (map.containsKey( columnName )) {
                    Field field = map.get( columnName );
                    Class<?> type = field.getType();
                    //获取方法名称
                    String setMethod = MyStringUtils.getSetMethod( field.getName() );
                    Method method = clazz.getMethod( setMethod, type );
                    Object object = resultSet.getObject( i + 1 );
                    method.invoke( obj, type.cast( object ) );
                }
            }
            return obj;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
