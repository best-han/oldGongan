package com.windaka.suizhi.mpi.utils;

import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.model.Person;
import com.windaka.suizhi.mpi.model.PersonInOut;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class QueueUtils {
    private static QueueUtils instance;

    public static final int FILE_QUEUE_SIZE = 1000;// 阻塞队列大小

    private static BlockingQueue<PersonInOut> queuePerson = new ArrayBlockingQueue<PersonInOut>(FILE_QUEUE_SIZE);

    private static BlockingQueue<CarInOut> queueCar = new ArrayBlockingQueue<CarInOut>(FILE_QUEUE_SIZE);

    private static BlockingQueue<RecordAbnormal> queueAbnormal = new ArrayBlockingQueue<RecordAbnormal>(FILE_QUEUE_SIZE);

    private static BlockingQueue<Person> queuePersonInfoIsCrime = new ArrayBlockingQueue<Person>(FILE_QUEUE_SIZE);

    /**
     * 构造方法，private不让外界生成队列工具类
     */
    private QueueUtils(){

    }



    /**
     * 添加队列元素
     * @param imageInfo
     * @throws InterruptedException
     */
    public static void put(PersonInOut  imageInfo) throws InterruptedException {
        queuePerson.put(imageInfo);
    }

    /**
     * 获取队列
     * @return
     */
    public static BlockingQueue<PersonInOut> getQueuePerson(){
        return queuePerson;
    }

    /**
     * 添加队列元素
     * @param carInOut
     * @throws InterruptedException
     */
    public static void put(CarInOut  carInOut) throws InterruptedException {
        queueCar.put(carInOut);
    }

    /**
     * 获取队列
     * @return
     */
    public static BlockingQueue<CarInOut> getQueueCar(){
        return queueCar;
    }

    /**
     * 添加队列元素
     * @param recordAbnormal
     * @throws InterruptedException
     */
    public static void put(RecordAbnormal  recordAbnormal) throws InterruptedException {
        queueAbnormal.put(recordAbnormal);
    }

    /**
     * 获取队列
     * @return
     */
    public static BlockingQueue<RecordAbnormal> getQueueAbnormal(){
        return queueAbnormal;
    }

    /**
     * 添加队列元素
     * @param
     * @throws InterruptedException
     */
    public static void put(Person  person) throws InterruptedException {
        queuePersonInfoIsCrime.put(person);
    }

    /**
     * 获取队列
     * @return
     */
    public static BlockingQueue<Person> getQueuePersonInfoIsCrime(){
        return queuePersonInfoIsCrime;
    }


}
