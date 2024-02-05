#!/usr/bin/env python3
import functools
import os
import statistics
import threading
import time

BASE_FOLDER = './results'

def run_n_times(func, n, args, kwargs):
    for _ in range(n):
        func(*args, **kwargs)


def bench(n_threads=1, seq_iter=1, iter=1):
    
    bench_dict = {
        'n_threads': n_threads,
        'seq_iter': seq_iter,
        'iter': iter
    };
    
    @functools.wraps(bench)
    def bench_decorator(func):
        func_name = func.__name__
        bench_dict['func'] = func_name
        
        def wrapper(*args, **kwargs):
            # Now I run `func` in `n_threads` threads, `seq_iter` times each. 
            # I do that `iter` times, and for each time I compute the time.
            
            # I will store here the time needed for each iter
            times = []

            for i in range(iter):
                threads = [];
                
                for _ in range(n_threads):
                    t = threading.Thread(target=run_n_times, args=(func, seq_iter, args, kwargs));
                    threads.append(t)
                
                start_time = time.perf_counter()
                for t in threads:
                    t.start()
                
                for t in threads:
                    t.join()
                    
                end_time = time.perf_counter()
                time_delta = end_time - start_time
                times.append(time_delta)
                print("Computed iteration {} of {} in {} seconds".format(i, func_name, time_delta));
            # end for _ in range(iter)
            
            # calculating the mean and variance for all these iterations
            mean = statistics.mean(times)
            variance = 0
            if len(times) > 1: # to avoid errors with only 1 value
                variance = statistics.variance(times)
            
            bench_dict['mean'] = mean
            bench_dict['variance'] = variance

            return bench_dict

        return wrapper
        
    return bench_decorator



def test (iterations, fun, args):
    """
    executes fun on args with varying numbers of iterations and degrees of parallelism:
    - execute 16 times fun on args on a single thread (passing iter as further parameter)
    - run fun 8 times on two threads
    - then 4 times on 4 threads
    - finally, 2 times on 8 threads.
    
    Writes the results from the 4 invocantions into a set of files named `<fun>_<args>_<n_threads>_<seq_iter>`
    """
    
    @bench(n_threads=1, seq_iter=16, iter=iterations)
    def test1(*args, **kwargs):
        return fun(*args, **kwargs)
    
    @bench(n_threads=2, seq_iter=8, iter=iterations)
    def test2(*args, **kwargs):
        return fun(*args, **kwargs)
    
    @bench(n_threads=4, seq_iter=4, iter=iterations)
    def test4(*args, **kwargs):
        return fun(*args, **kwargs)
    
    @bench(n_threads=8, seq_iter=2, iter=iterations)
    def test8(*args, **kwargs):
        return fun(*args, **kwargs)
    
    bench_dict_1 = test1(*args);
    bench_dict_2 = test2(*args);
    bench_dict_4 = test4(*args);
    bench_dict_8 = test8(*args);
    
    # if directory doesn't exist, create it
    try:
        os.mkdir(BASE_FOLDER)
    except:
        pass
    
    # write to file
    with open(f'{BASE_FOLDER}/{fun.__name__}_{str(args)}_1_16.txt', 'w') as f:
        f.write(f'{bench_dict_1}\n')
    with open(f'{BASE_FOLDER}/{fun.__name__}_{str(args)}_2_8.txt', 'w') as f:
        f.write(f'{bench_dict_2}\n')
    with open(f'{BASE_FOLDER}/{fun.__name__}_{str(args)}_4_4.txt', 'w') as f:
        f.write(f'{bench_dict_4}\n')
    with open(f'{BASE_FOLDER}/{fun.__name__}_{str(args)}_8_2.txt', 'w') as f:
        f.write(f'{bench_dict_8}\n')
    
    
    
def just_wait(n): # NOOP for n/10 seconds
    time.sleep(n * 0.1)

def grezzo(n): # CPU intensive
    for i in range(2**n):
        pass   

def inefficient_fibo(n):
    if n < 2:
        return n
    else:
        return inefficient_fibo(n-1) + inefficient_fibo(n-2)


if __name__ == "__main__":
    print("Running tests...")
    
    my_args = (1,);
    test(fun=just_wait, args=my_args, iterations=10);
    
    my_args = (15,);
    test(fun=grezzo, args=my_args, iterations=10);
    
    my_args = (35,);
    test(fun=inefficient_fibo, args=my_args, iterations=10);
    print("All tests done!")