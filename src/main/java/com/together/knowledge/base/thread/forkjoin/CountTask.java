package com.together.knowledge.base.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask {

    private final static int THRESHOLD= 2;//阈值

    private int start;

    private int end;

    public CountTask(int start,int end) {
        this.start= start;
        this.end= end;
    }

    @Override
    protected Object compute() {
        int sum = 0;
        boolean canCompute = (end-start) <=THRESHOLD;
        if(canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        }else{
            int middle = (start+end) / 2;
            CountTask leftTask =new CountTask(start, middle);
            CountTask rightTask =new CountTask(middle + 1,end);
            leftTask.fork();
            rightTask.fork();
            int leftResult= (int) leftTask.join();
            int rightResult=(int) rightTask.join();
            sum = leftResult  + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1,4);
        //执行一个任务
        Future result = forkJoinPool.submit(countTask);
        System.out.println(result.get());

    }


}
