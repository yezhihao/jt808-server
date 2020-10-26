package org.yzh.protocol.basics;

/**
 * 键值对
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class KeyValuePair<K, V> {

    private K id;
    private V value;

    public KeyValuePair() {
    }

    public KeyValuePair(K id, V value) {
        this.id = id;
        this.value = value;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public static <K, V> KeyValuePair<K, V> of(K key, V value) {
        return new KeyValuePair<>(key, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        sb.append('{');
        sb.append("id=").append(id);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}