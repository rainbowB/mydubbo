package priv.fjh.mydubbo.serializer;

/**
 * @author fjh
 * @date 2021/1/2 15:35
 * @Description:
 */
public interface CommonSerializer {

    //序列化
    byte[] serialize(Object obj);

    //反序列化
    Object deserialize(byte[] bytes, Class<?> clazz);

    //获得该序列化器的编号
    int getCode();

    //根据序列化器的编号获取序列化器
    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }

}
