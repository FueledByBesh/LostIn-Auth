package com.lostin.auth.util.abstracts;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class CanThrow<T,E extends Throwable>{

    private E error;
    private T data;

    private CanThrow(T data, E error){
        this.data = data;
        this.error = error;
    }

    public static <T,E extends Throwable> CanThrow<T,E> withoutError(@NonNull T data){
        return new CanThrow<>(data,null);
    }

    public static <T,E extends Throwable> CanThrow<T,E> error(@NonNull E error){
        return new CanThrow<>(null,error);
    }

    public <X extends Throwable> T catchIfError(Supplier<? extends X> exceptionSupplier) throws X{
        if(Objects.nonNull(data)){
            return data;
        }
        throw exceptionSupplier.get();
    }

    public <X extends Throwable> T catchIfErrorAndLog(Supplier<? extends X> exceptionSupplier, String logInfo, boolean addExceptionIntoLog) throws X{
        if(Objects.nonNull(data)){
            return data;
        }

        if(addExceptionIntoLog)
            log.error(logInfo,exceptionSupplier.get());
        else
            log.error(logInfo);
        throw exceptionSupplier.get();
    }

    public T catchIfError(Function<E,T> function){
        if(Objects.nonNull(data)){
            return data;
        }
        return function.apply(error);
    }

}
