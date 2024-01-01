package edu.hw10.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private final Object cachingObject;
    private final Map<String, Object> cache;
    private final Path path;
    private final static String FILE_EXTENSION = ".txt";

    private CacheProxy(Object cachingObject) {
        this.cachingObject = cachingObject;
        this.cache = new HashMap<>();
        this.path = Path.of("src/main/java/edu/hw10/task2/dir_for_cache/");
    }

    public static <T> T create(Object cachingObject, Class<T> cachingInterface) {
        return (T) Proxy.newProxyInstance(
            cachingObject.getClass().getClassLoader(),
            new Class<?>[] {cachingInterface},
            new CacheProxy(cachingObject)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache annotation = method.getAnnotation(Cache.class);
            if (annotation.persist()) {
                load(method);
            }

            String key = getKey(method, args);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }

            Object methodResult = method.invoke(cachingObject, args);
            cache.put(key, methodResult);
            if (annotation.persist()) {
                save(method);
            }
            return methodResult;
        }

        return method.invoke(cachingObject, args);
    }

    private String getKey(Method method, Object[] args) {
        StringBuilder builder = new StringBuilder(method.getName());
        for (Object arg : args) {
            builder.append("-key: ").append(arg.toString());
        }
        return String.valueOf(builder);
    }

    private void save(Method method) {
        Path cacheFilePath = Path.of(path.toString()
            + "/" + method.getName() + FILE_EXTENSION);

        if (!Files.exists(cacheFilePath)) {
            try {
                Files.createFile(cacheFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
            new FileOutputStream(cacheFilePath.toString()))) {
            outputStream.writeObject(cache);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load(Method method) {
        Path cacheFilePath = Path.of(path.toString()
            + "/" + method.getName() + FILE_EXTENSION);

        if (!Files.exists(cacheFilePath)) {
            return;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path.toString()
            + "/" + method.getName() + FILE_EXTENSION))) {
            Object object = inputStream.readObject();
            if (object instanceof Map) {
                cache.putAll((Map<String, Object>) object);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
